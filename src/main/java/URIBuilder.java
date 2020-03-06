
public class URIBuilder {

    public final static String base = "https://api.github.com";
    public final static String delim = "/";


    public URIBuilder(){

    }

    public static String getBaseURL(){
        return  base + delim + "repos/" + Main.github_repo_owner + delim + Main.github_repo_name + delim + "issues";
    }

    public static String getTicketURL(int ticketNum){
        return getBaseURL() + delim + ticketNum;
    }
}
