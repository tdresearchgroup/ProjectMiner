
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RESTClient {

    private Client client;

    public RESTClient(){

    }
    /***
     * responsible for loading client, getting http posts, returning string (as JSON) to calling function.
     * JSON string is direct JSON from github.
     * @param urlString
     * @return
     */
    public JSONResponse ticketRequestRunner(String urlString){
        String curlHeader = "curl -H \"Authorization: token 3291f1efbafeb9fc4e6362b1c6d799f2fd109960\" ";
        urlString = curlHeader + urlString;
        JSONResponse jsonResponse = null;
        try{
            Process process = Runtime.getRuntime().exec(urlString);
            InputStream receive = process.getInputStream();

            jsonResponse = new JSONResponse(receive);

            process.destroy();


            //client = ClientBuilder.newClient();
            //WebTarget target = client.target(urlString);
            //Response r = target.request().get();
            //String response = r.readEntity(String.class);
            //jsonResponse = new JSONResponse(response);
        }catch(Exception e){
            System.out.println("Error in the ticket request runner");
            e.printStackTrace();
        }
        return jsonResponse;
    }
}
