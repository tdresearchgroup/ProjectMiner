import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONResponse {

    /***
     * JSONResponse might either be a JSONOBject or JSONArray, depending on if we are askign for a specific ticket (JSONObject) or
     * are doing the generic repo with no specified ticket (JSONArray)
     */

    @Getter
    private JSONObject response;
    private JSONParser parser = new JSONParser();

    public JSONResponse(InputStream inputs){
        try{
            this.response = (JSONObject) parser.parse(new InputStreamReader(inputs, "UTF-8"));

        }catch (Exception e){
            System.out.println("error parsing inputstream response to JSON");
            e.printStackTrace();
        }
    }

    public JSONResponse(String response){
        try{
            this.response = (JSONObject) parser.parse(response);
        }catch (ClassCastException e){
            try {
                this.response = (JSONObject)((JSONArray) parser.parse(response)).get(0);
            }catch (ParseException e1){
                System.out.println("Error with this nasty ass try catch.");
            }
        } catch (ParseException e){
            System.out.println("Parse exception in json response");
            e.printStackTrace();
        }
    }
}
