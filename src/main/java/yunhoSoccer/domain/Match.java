package yunhoSoccer.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Match {

    @Id
    @GeneratedValue
    @Column(name = "match_id")
    private Long id;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<MatchTeam> matchTeams = new ArrayList<>();

    private int homeScore;

    private int awayScore;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus; //BEFORE_START, POSTPONED, END

    private String matchPlace;

    private LocalDateTime matchDate;

    @Embedded
    private Seat seat;

    /**
     * 연관관계 편의 메소드
     */
    private void setMatchTeam(MatchTeam matchTeam) {
        matchTeams.add(matchTeam);
        matchTeam.setMatch(this);
    }

    @Builder
    private Match(int homeScore, int awayScore, MatchStatus matchStatus, String matchPlace, LocalDateTime matchDate,
                  Seat seat, MatchTeam... matchTeams) {

        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.matchStatus = matchStatus;
        this.matchPlace = matchPlace;
        this.matchDate = matchDate;
        this.seat = seat;

        for (MatchTeam matchTeam : matchTeams) {
            setMatchTeam(matchTeam);
        }
    }

    public void addSeatByGrade(Grade grade, int count) {
        seat.addSeat(grade, count);
    }

    public void removeSeatByGrade(Grade grade, int count) {
        seat.removeSeat(grade, count);
    }

    public void postponeMatch(int year, int month, int day, int hour, int min) {
        if (matchStatus == MatchStatus.END) {
            throw new IllegalStateException("You can't postpone this match which is already ended.");
        }

        setMatchStatus(MatchStatus.POSTPONED);
        setMatchDate(LocalDateTime.of(year, month, day, hour, min));
    }

    public void endMatch(int homeScore, int awayScore) {
        if (matchStatus == MatchStatus.END) {
            throw new IllegalStateException("You can't end this match because this is already ended.");
        }

        setMatchStatus(MatchStatus.END);
        setScore(homeScore, awayScore);
    }

    public String getMatchInfo() {
        StringBuilder matchInfo = new StringBuilder();

        for (MatchTeam matchTeam : matchTeams) {
            String firstName = matchTeam.getTeam().getName().split("")[0];
            matchInfo.append(firstName);
        }

        return matchInfo.toString();
    }

    private void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    private void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }

    private void setScore(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }
}
