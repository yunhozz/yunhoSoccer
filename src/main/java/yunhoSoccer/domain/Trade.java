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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trade {

    @Id
    @GeneratedValue
    @Column(name = "trade_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL)
    private List<TradePlayer> tradePlayers = new ArrayList<>();

    private int resultPrice;

    private LocalDateTime tradeTime;

    @Enumerated(EnumType.STRING)
    private TradePurpose tradePurpose; //RECRUIT, RELEASE

    public Trade(Team team, LocalDateTime tradeTime, TradePurpose tradePurpose) {
        this.team = team;
        this.tradeTime = tradeTime;
        this.tradePurpose = tradePurpose;
    }

    /**
     * 연관관계 편의 메소드
     */
    private void setTradePlayer(TradePlayer tradePlayer) {
        this.tradePlayers.add(tradePlayer);
        tradePlayer.setTrade(this);
    }

    //선수 영입 생성자 (Player -> TeamPlayer)
    public static Trade createTradeOfRecruit(Team team, TradePlayer... tradePlayers) {
        Trade trade = new Trade(team, LocalDateTime.now(), TradePurpose.RECRUIT);
        int sumOfPrice = 0;

        for (TradePlayer tradePlayer : tradePlayers) {
            trade.setTradePlayer(tradePlayer);
            tradePlayer.recruitPlayer();
            sumOfPrice += tradePlayer.getPlayer().getPrice();
        }

        team.addNumberOfPlayer(tradePlayers.length);
        team.removeCapital(sumOfPrice);
        trade.setResultPrice(sumOfPrice);

        return trade;
    }

    //선수 방출 생성자 (TeamPlayer -> Player)
    public static Trade createTradeOfRelease(Team team, int price, TradePlayer... tradePlayers) {
        Trade trade = new Trade(team, LocalDateTime.now(), TradePurpose.RELEASE);
        int sumOfPrice = 0;

        for (TradePlayer tradePlayer : tradePlayers) {
            trade.setTradePlayer(tradePlayer);
            tradePlayer.releasePlayer(price);
            sumOfPrice += price;
        }

        team.removeNumberOfPlayer(tradePlayers.length);
        team.addCapital(sumOfPrice);
        trade.setResultPrice(sumOfPrice);

        return trade;
    }

    private void setResultPrice(int resultPrice) {
        this.resultPrice = resultPrice;
    }
}
