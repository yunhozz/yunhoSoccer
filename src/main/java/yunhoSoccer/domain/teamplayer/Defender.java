package yunhoSoccer.domain.teamplayer;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yunhoSoccer.domain.BasicInfo;
import yunhoSoccer.domain.Team;
import yunhoSoccer.domain.Position;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("DF")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Defender extends TeamPlayer {

    private int wonOfTackle;
    private int blockShot;

    @Builder
    private Defender(Team team, BasicInfo basicInfo, Position position, int numberOfMatch, int goal, int assist, Long playerId,
                     int wonOfTackle, int blockShot) {

        super(team, basicInfo, position, numberOfMatch, goal, assist, playerId);
        this.wonOfTackle = wonOfTackle;
        this.blockShot = blockShot;
    }
}
