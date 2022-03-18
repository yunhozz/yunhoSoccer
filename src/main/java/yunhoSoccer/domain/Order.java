package yunhoSoccer.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderMatch> orderMatches = new ArrayList<>();

    private LocalDateTime orderDate; //주문 일시

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //ORDER, CANCEL

    private Order(Member member, LocalDateTime orderDate, OrderStatus orderStatus) {
        this.member = member;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    public static Order createOrder(Member member) {
        return new Order(member, LocalDateTime.now(), OrderStatus.ORDER);
    }

    public void cancelOrder() {
        for (OrderMatch orderMatch : orderMatches) {
            if (orderMatch.getMatch().getMatchStatus() == MatchStatus.END) {
                throw new IllegalStateException("You can't cancel this match because this is already end.");
            }

            setOrderStatus(OrderStatus.CANCEL);
            orderMatch.cancelOrderMatch();
            member.getAccount().deposit(orderMatch.getTotalPrice());
        }
    }

    private void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
