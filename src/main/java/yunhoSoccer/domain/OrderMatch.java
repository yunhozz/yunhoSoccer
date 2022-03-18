package yunhoSoccer.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMatch {

    @Id
    @GeneratedValue
    @Column(name = "order_match_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    private Grade grade; //PREMIUM, VIP, STANDARD

    private int count;

    private int price; //주문 후 가격

    private OrderMatch(Match match, Grade grade, int count, int price) {
        this.match = match;
        this.grade = grade;
        this.count = count;
        this.price = price;
    }

    /**
     * 연관관계 편의 메소드
     */
    private void setOrder(Order order) {
        this.order = order;
        order.getOrderMatches().add(this);
    }

    private void setTicket(Ticket ticket) {
        this.ticket = ticket;
        ticket.changeOrderMatch(this);
    }

    public static OrderMatch createOrderMatch(Order order, Match match, Ticket ticket, Grade grade, int count) {
        if (match.getMatchStatus().equals(MatchStatus.END)) {
            throw new IllegalStateException("Can't order this match because it is already end.");
        }

        int priceByGrade = match.getSeat().getPriceByGrade(grade);

        OrderMatch orderMatch = new OrderMatch(match, grade, count, priceByGrade);
        orderMatch.setOrder(order);
        orderMatch.setTicket(ticket);

        order.getMember().getAccount().withdrawal(priceByGrade * count); //member 계좌 인출
        match.removeSeatByGrade(grade, count); //등급, 갯수에 따른 좌석수 감소

        return orderMatch;
    }

    public void cancelOrderMatch() {
        match.addSeatByGrade(grade, count);
        ticket.removeTicket();
    }

    public int getTotalPrice() {
        return count * price;
    }
}
