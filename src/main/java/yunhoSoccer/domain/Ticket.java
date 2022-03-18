package yunhoSoccer.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket {

    @Id
    @GeneratedValue
    @Column(name = "ticket_id")
    private Long id;

    @OneToOne(mappedBy = "ticket")
    private OrderMatch orderMatch;

    private String userIdOfOrder;

    private String randomId; //티켓 고유키

    @Enumerated(EnumType.STRING)
    private IssueStatus issueStatus; //ISSUE, EXPIRED

    private Ticket(String userIdOfOrder, String randomId, IssueStatus issueStatus) {
        this.userIdOfOrder = userIdOfOrder;
        this.randomId = randomId;
        this.issueStatus = issueStatus;
    }

    public static Ticket createTicket(String userIdOfOrder, String matchInfo) {
        String ticketRandomId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
                                    + "_" + (int)(Math.random() * 100000);
//        RandomStringUtils.randomAlphanumeric(6);

        return new Ticket(userIdOfOrder, matchInfo + "_" + ticketRandomId, IssueStatus.ISSUE);
    }

    public void removeTicket() {
        setRandomId(" ");
        setIssueStatus(IssueStatus.EXPIRED);
    }

    protected void changeOrderMatch(OrderMatch orderMatch) {
        this.orderMatch = orderMatch;
    }

    private void setRandomId(String randomId) {
        this.randomId = randomId;
    }

    private void setIssueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
    }
}
