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
@DiscriminatorValue("GK")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goalkeeper extends TeamPlayer {

    private int cleanSheet;
    private int save;

    @Builder
    private Goalkeeper(Team team, BasicInfo basicInfo, Position position, int numberOfMatch, int goal, int assist,
                       Long playerId, int cleanSheet, int save) {

        super(team, basicInfo, position, numberOfMatch, goal, assist, playerId);
        this.cleanSheet = cleanSheet;
        this.save = save;
    }
}
