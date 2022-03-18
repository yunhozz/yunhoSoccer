package yunhoSoccer.dto;

import lombok.Data;
import yunhoSoccer.domain.Team;

@Data
public class TeamDto {

    private String name;
    private String homeGround;
    private String coach;
    private int numberOfPlayer;
    private int capital;
    private int winScore;

    public Team toEntity() {
        return Team.builder()
                    .name(name)
                    .homeGround(homeGround)
                    .coach(coach)
                    .numberOfPlayer(numberOfPlayer)
                    .capital(capital)
                    .winScore(winScore)
                    .build();
    }
}
