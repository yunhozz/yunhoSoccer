package yunhoSoccer.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchTeam {

    @Id
    @GeneratedValue
    @Column(name = "match_team_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public MatchTeam(Team team) {
        this.team = team;
    }

    protected void setMatch(Match match) {
        this.match = match;
    }
}
