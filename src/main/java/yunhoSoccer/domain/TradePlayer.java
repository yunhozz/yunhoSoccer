package yunhoSoccer.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TradePlayer {

    @Id
    @GeneratedValue
    @Column(name = "trade_player_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id")
    private Trade trade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    public TradePlayer(Player player) {
        this.player = player;
    }

    public void recruitPlayer() {
        player.recruited();
    }

    public void releasePlayer(int price) {
        player.released(price);
    }

    protected void setTrade(Trade trade) {
        this.trade = trade;
    }
}
