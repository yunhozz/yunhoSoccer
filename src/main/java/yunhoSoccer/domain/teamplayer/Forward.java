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
@DiscriminatorValue("FW")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Forward extends TeamPlayer {

    private double shotAccuracy;
    private int goalChance;

    @Builder
    private Forward(Team team, BasicInfo basicInfo, Position position, int numberOfMatch, int goal, int assist, Long playerId,
                    double shotAccuracy, int goalChance) {

        super(team, basicInfo, position, numberOfMatch, goal, assist, playerId);
        this.shotAccuracy = shotAccuracy;
        this.goalChance = goalChance;
    }
}
