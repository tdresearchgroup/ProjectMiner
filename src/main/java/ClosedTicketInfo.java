import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;


@Getter
@Setter
public class ClosedTicketInfo {

    private int ticketNum;
    private int numMatches;
    private DateTime openDate;
    private DateTime closeDate;

    public ClosedTicketInfo(){

    }

}
