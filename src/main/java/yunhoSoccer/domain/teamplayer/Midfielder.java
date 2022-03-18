package yunhoSoccer.domain.teamplayer;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yunhoSoccer.domain.BasicInfo;
import yunhoSoccer.domain.Team;
import yunhoSoccer.domain.Position;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("MF")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Midfielder extends TeamPlayer {

    private double passRate;
    private int wonOfTackle;

    @Builder
    private Midfielder(Team team, BasicInfo basicInfo, Position position, int numberOfMatch, int goal, int assist, Long playerId,
                       double passRate, int wonOfTackle) {

        super(team, basicInfo, position, numberOfMatch, goal, assist, playerId);
        this.passRate = passRate;
        this.wonOfTackle = wonOfTackle;
    }
}
