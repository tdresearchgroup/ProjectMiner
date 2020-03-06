import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.sun.deploy.util.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectHandler {

    private List<String> keywords;
    private List<Integer> tickets;
    private RESTClient client;

    /**
     * tracks Ticket_number, keyword, and keyword_count for a single project.
     */
    private Table<Integer, String, Integer> keywordFrequency;

    public ProjectHandler(){
        client = new RESTClient();
        keywordFrequency = TreeBasedTable.create();
    }

    public void operate(){
        importKeywords();
        fillKeywords();
    }

    private void importKeywords(){
        try{
            keywords = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Derek Reimanis\\Documents\\research\\accumulator\\target\\classes\\design_keywords.txt"));
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
            process(ticketNum, url);;
        }
    }

    private void process(int ticketNum, String url){
        JSONObject jsonObject = getJSONFromSend(url);
        String body = (String)jsonObject.get("body");
        try {
            //sleep so github doesn't think im ddosing them, though realistically, this isn't necessary
            Thread.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        findKeywords(ticketNum, body);
    }

    private void findKeywords(int ticketNum, String body){
        //break up body
        String[] individualText = body.split(" ");
        //loop through keywords on separate document, search body for the keyword. if match, call keyword.put(ticketNum, keyword, count++)
        for (String keyword : keywords){
            if (ArrayUtils.contains(individualText, keyword)){
                System.out.println("Got a hit, matched ticket #: " + ticketNum + " with body: " + body + " to keyword: " + keyword);
                int count = keywordFrequency.get(ticketNum, keyword);
                if (count == 0){
                    //hasn't been found yet... or its null idk
                }
                keywordFrequency.put(ticketNum, keyword, count++);
            }
        }
    }


    public void findTickets(){
        findTickets(-1);
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
        for (int i = 1; i <= maxSize; i++){
            tickets.add(i);
        }
    }

    private JSONObject getJSONFromSend(String url){
        return client.ticketRequestRunner(url).getResponse();
    }
}
