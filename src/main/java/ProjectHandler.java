
import org.apache.commons.lang3.ArrayUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectHandler {

    private List<String> keywords;
    private List<Integer> tickets;
    private RESTClient client;

    private List<ClosedTicketInfo> ticketFrequency;


    public ProjectHandler(){
        client = new RESTClient();
        ticketFrequency = new ArrayList<>();
    }

    public void operate(){
        importKeywords();
        fillKeywords();
        output();
    }

    private void importKeywords(){
        try{
            keywords = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("design_keywords.txt"));
            //BufferedReader reader = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("design_keywords.txt").getFile()));
            String line = "";
            while ((line = reader.readLine()) != null){
                keywords.add(line.toLowerCase());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Read keywords in.");
        System.out.println(keywords);
    }

    private void fillKeywords(){

        for (Integer ticketNum : tickets){
            //build url
            String url = URIBuilder.getTicketURL(ticketNum);
            try {
                process(ticketNum, url);
            }catch (Exception e){
                output();
                e.printStackTrace();
                System.out.println("At the limit, killing the program and outputting.");
                System.exit(0);
            }
        }
    }

    /***
     *
     * @param ticketNum
     * @param url
     * @throws Exception THIS IS IMPORTANT BECAUSE THE EXCEPTION WILL BE THROWN IF WE HIT THE #REQUESTS/HOUR LIMIT
     */
    private void process(int ticketNum, String url) throws Exception{
        JSONObject jsonObject = getJSONFromSend(url);
        //I need to make sure this is closed first.
        String state = (String)jsonObject.get("state");
        if (state == null || !state.equals("closed")){
            return;
        }
        String body = (String)jsonObject.get("body");

        DateTimeFormatter format = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:SSZ");

        DateTime openDate = format.parseDateTime((String)jsonObject.get("created_at"));
        DateTime closeDate = format.parseDateTime((String)jsonObject.get("closed_at"));

        try {
            //sleep so github doesn't think im ddosing them, though realistically, this isn't necessary
            Thread.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        findKeywords(ticketNum, body, openDate, closeDate);
    }

    private void findKeywords(int ticketNum, String body, DateTime openDate, DateTime closeDate){
        //break up body
        body = body.toLowerCase();
        String[] individualText = body.split(" ");
        //loop through keywords on separate document, search body for the keyword. if match, call keyword.put(ticketNum, keyword, count++)
        int keywordCount = 0;
        for (String keyword : keywords){
            if (ArrayUtils.contains(individualText, keyword.toLowerCase())){
                System.out.println("Got a hit, matched ticket #: " + ticketNum + " with body: " + body + " to keyword: " + keyword);
                keywordCount++;
            }
        }
        //fill data structure
        ClosedTicketInfo closedTicketInfo = new ClosedTicketInfo();
        closedTicketInfo.setTicketNum(ticketNum);
        closedTicketInfo.setNumMatches(keywordCount);
        closedTicketInfo.setOpenDate(openDate);
        closedTicketInfo.setCloseDate(closeDate);
        ticketFrequency.add(closedTicketInfo);
    }


    public void findTickets(){
        findTickets(Main.github_ticket_num_raw);
    }


    public void findTickets(int ticketNum){
        String baseURL = URIBuilder.getBaseURL();
        if (ticketNum == -1){
            //default value, this means we don't know the last ticket and want it programmatically.
            JSONObject jsonObject = getJSONFromSend(baseURL);
            Long lastTicketLong = (Long)jsonObject.get("number");
            ticketNum = lastTicketLong.intValue();
        }
        fillTickets(ticketNum);
    }

    private void fillTickets(int maxSize){
        tickets = new ArrayList<>();
        //
        for (int i = maxSize-59; i <= maxSize; i++){
            tickets.add(i);
        }
        //for when we don't have to worry about 60 most recent tickets.
//        for (int i = 1; i <= maxSize; i++){
//            tickets.add(i);
//        }
    }

    private void output(){
        try {
            //output only non-zero keyword count fields
            FileWriter nonZero = new FileWriter(new File("nonZero_output.csv"));
            String delim = ", ";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Project_ID, Ticket_num, num_matches, Date_Opened, Date_Closed\n");
            for (ClosedTicketInfo closedTicketInfo : ticketFrequency){
                if (closedTicketInfo.getNumMatches() > 0){
                    stringBuilder.append(Main.github_repo_name + delim);
                    stringBuilder.append(closedTicketInfo.getTicketNum() + delim);
                    stringBuilder.append(closedTicketInfo.getNumMatches() + delim);
                    stringBuilder.append(closedTicketInfo.getOpenDate() + delim);
                    stringBuilder.append(closedTicketInfo.getCloseDate() + "\n");
                }
            }
            nonZero.append(stringBuilder.toString());
            nonZero.close();

            FileWriter zero = new FileWriter(new File("zero_output.csv"));
            stringBuilder = new StringBuilder();
            stringBuilder.append("Project_ID, Ticket_num, num_matches, Date_Opened, Date_Closed\n");
            for (ClosedTicketInfo closedTicketInfo : ticketFrequency){
                stringBuilder.append(Main.github_repo_name + delim);
                stringBuilder.append(closedTicketInfo.getTicketNum() + delim);
                stringBuilder.append(closedTicketInfo.getNumMatches() + delim);
                stringBuilder.append(closedTicketInfo.getOpenDate() + delim);
                stringBuilder.append(closedTicketInfo.getCloseDate() + "\n");
            }
            zero.append(stringBuilder.toString());
            zero.close();

            //output all fields
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private JSONObject getJSONFromSend(String url){
        return client.ticketRequestRunner(url).getResponse();
    }
}
