import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static String github_repo_owner;
    public static String github_repo_name;
    public static int github_ticket_start_num;
    public static int github_ticket_end_num;
    public static String github_access_token;

    public Main(){
        ProjectHandler test = new ProjectHandler();
        test.findTickets();
        test.operate();
    }

    public static void main(String[] args){
        try {
            Properties prop = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = Main.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(stream);
            github_repo_owner = prop.getProperty("github_repo_owner");
            github_repo_name = prop.getProperty("github_repo_name");
            github_ticket_start_num = Integer.parseInt(prop.getProperty("github_ticket_start_num"));
            github_ticket_end_num = Integer.parseInt(prop.getProperty("github_ticket_end_num"));
            github_access_token = prop.getProperty("github_access_token");
        }catch(IOException e){
            System.out.println("Error importing properties");
            e.printStackTrace();
        }
        new Main();
    }
}
