package yunhoSoccer.dto;

import lombok.Data;
import yunhoSoccer.domain.MatchTeam;
import yunhoSoccer.domain.Seat;
import yunhoSoccer.domain.Match;
import yunhoSoccer.domain.MatchStatus;

import java.time.LocalDateTime;

@Data
public class MatchDto {

    private int homeScore;
    private int awayScore;
    private String matchPlace;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    private int premiumSeat;
    private int vipSeat;
    private int standardSeat;

    public Match toEntity(MatchTeam homeTeam, MatchTeam awayTeam) {
        MatchTeam[] matchTeams = new MatchTeam[2];
        matchTeams[0] = homeTeam;
        matchTeams[1] = awayTeam;

        return Match.builder()
                        .matchTeams(matchTeams)
                        .homeScore(0)
                        .awayScore(0)
                        .matchStatus(MatchStatus.BEFORE_START)
                        .matchPlace(matchPlace)
                        .matchDate(LocalDateTime.of(year, month, day, hour, minute))
                        .seat(new Seat(premiumSeat, vipSeat, standardSeat))
                        .build();
    }
}
