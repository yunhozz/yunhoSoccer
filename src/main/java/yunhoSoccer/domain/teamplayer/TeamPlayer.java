package yunhoSoccer.domain.teamplayer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yunhoSoccer.domain.BasicInfo;
import yunhoSoccer.domain.Team;
import yunhoSoccer.domain.Position;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class TeamPlayer {

    @Id
    @GeneratedValue
    @Column(name = "team_player_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Embedded
    private BasicInfo basicInfo;

    private Position position; //FORWARD, MIDFIELDER, DEFENDER, GOALKEEPER

    private int numberOfMatch;

    private int goal;

    private int assist;

    private Long playerId; //player id 확인용

    public TeamPlayer(Team team, BasicInfo basicInfo, Position position, int numberOfMatch, int goal, int assist, Long playerId) {
        this.team = team;
        this.basicInfo = basicInfo;
        this.position = position;
        this.numberOfMatch = numberOfMatch;
        this.goal = goal;
        this.assist = assist;
        this.playerId = playerId;
    }
}
