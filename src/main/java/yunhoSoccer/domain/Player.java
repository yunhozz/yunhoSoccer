package yunhoSoccer.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player {

    @Id
    @GeneratedValue
    @Column(name = "player_id")
    private Long id;

    @Embedded
    private BasicInfo basicInfo;

    private int price;

    @Enumerated(EnumType.STRING)
    private PlayerStatus playerStatus;

    @Builder
    private Player(BasicInfo basicInfo, int price, PlayerStatus playerStatus) {
        this.basicInfo = basicInfo;
        this.price = price;
        this.playerStatus = playerStatus;
    }

    public void recruited() {
        if (this.playerStatus == PlayerStatus.TRADED) {
            throw new IllegalStateException("This player is already traded.");
        }

        setTradeStatus(PlayerStatus.TRADED);
    }

    public void released(int price) {
        if (this.playerStatus == PlayerStatus.WAITING) {
            throw new IllegalStateException("This player is before traded.");
        }

        setPrice(price);
        setTradeStatus(PlayerStatus.WAITING);
    }

    private void setPrice(int price) {
        this.price = price;
    }

    private void setTradeStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }
}
