package yunhoSoccer.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yunhoSoccer.domain.exception.NotEnoughCapitalException;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    private String homeGround;

    private String coach;

    private int numberOfPlayer;

    private int capital;

    private int winScore;

    @Builder
    private Team(String name, String homeGround, String coach, int numberOfPlayer, int capital, int winScore) {
        this.name = name;
        this.homeGround = homeGround;
        this.coach = coach;
        this.numberOfPlayer = numberOfPlayer;
        this.capital = capital;
        this.winScore = winScore;
    }

    public void addNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer += numberOfPlayer;
    }

    public void removeNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer -= numberOfPlayer;
    }

    public void addCapital(int capital) {
        this.capital += capital;
    }

    public void removeCapital(int capital) {
        int remainCapital = this.capital - capital;

        if (remainCapital >= 0) {
            this.capital = remainCapital;

        } else {
            throw new NotEnoughCapitalException("You don't have sufficient capital.");
        }
    }

    public void addWinScore() {
        winScore++;
    }

    public void removeWinScore() {
        winScore--;
    }
}
