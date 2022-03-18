package yunhoSoccer.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.Match;
import yunhoSoccer.domain.Team;
import yunhoSoccer.domain.MatchStatus;
import yunhoSoccer.dto.MatchDto;
import yunhoSoccer.dto.TeamDto;
import yunhoSoccer.repo.MatchRepository;
import yunhoSoccer.repo.TeamRepository;
import yunhoSoccer.service.MatchService;
import yunhoSoccer.service.TeamService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MatchServiceTest {

    @Autowired private MatchRepository matchRepository;
    @Autowired private MatchService matchService;
    @Autowired private TeamRepository teamRepository;
    @Autowired private TeamService teamService;

    @Test
    void 경기_생성() throws Exception {
        //given
        TeamDto teamDto1 = getTeamDto1();
        TeamDto teamDto2 = getTeamDto2();
        MatchDto matchDto = getMatchDto();

        //when
        Long teamId1 = teamService.makeTeam(teamDto1);
        Long teamId2 = teamService.makeTeam(teamDto2);
        Long matchId = matchService.makeMatch(matchDto, teamId1, teamId2);

        //then
        Match match = matchRepository.findById(matchId).get();

        assertThat(match.getMatchTeams().size()).isEqualTo(2);
        assertThat(match.getMatchInfo()).isEqualTo("AB");
        assertThat(match.getMatchStatus()).isEqualTo(MatchStatus.BEFORE_START);
    }

    @Test
    void 경기_연기() throws Exception {
        //given
        TeamDto teamDto1 = getTeamDto1();
        TeamDto teamDto2 = getTeamDto2();
        MatchDto matchDtoBeforePostpone = getMatchDto();

        MatchDto matchDtoAfterPostpone = new MatchDto();
        matchDtoAfterPostpone.setYear(2022);
        matchDtoAfterPostpone.setMonth(4);
        matchDtoAfterPostpone.setDay(23);
        matchDtoAfterPostpone.setHour(14);
        matchDtoAfterPostpone.setMinute(20);

        //when
        Long teamId1 = teamService.makeTeam(teamDto1);
        Long teamId2 = teamService.makeTeam(teamDto2);
        Long matchId = matchService.makeMatch(matchDtoBeforePostpone, teamId1, teamId2);

        matchService.postpone(matchId, matchDtoAfterPostpone);

        //then
        Match match = matchRepository.findById(matchId).get();
        assertThat(match.getMatchStatus()).isEqualTo(MatchStatus.POSTPONED);
    }

    @Test
    void 경기_종료() throws Exception {
        //given
        TeamDto teamDto1 = getTeamDto1();
        TeamDto teamDto2 = getTeamDto2();
        MatchDto matchDto = getMatchDto();

        MatchDto matchDtoOfEnd = new MatchDto();
        matchDtoOfEnd.setHomeScore(2);
        matchDtoOfEnd.setAwayScore(1);

        //when
        Long teamId1 = teamService.makeTeam(teamDto1);
        Long teamId2 = teamService.makeTeam(teamDto2);
        Long matchId = matchService.makeMatch(matchDto, teamId1, teamId2);

        matchService.end(matchId, matchDtoOfEnd);

        //then
        Match match = matchRepository.findById(matchId).get();
        Team homeTeam = teamRepository.findById(teamId1).get();
        Team awayTeam = teamRepository.findById(teamId2).get();

        teamService.winMatch(homeTeam.getId()); //homeTeam win
        teamService.loseMatch(awayTeam.getId()); //awayTeam lose

        assertThat(match.getMatchStatus()).isEqualTo(MatchStatus.END);
        assertThat(match.getHomeScore()).isEqualTo(2);
        assertThat(match.getAwayScore()).isEqualTo(1);

        assertThat(homeTeam.getWinScore()).isEqualTo(1);
        assertThat(awayTeam.getWinScore()).isEqualTo(-1);
    }

    private TeamDto getTeamDto1() {
        TeamDto teamDto = new TeamDto();

        teamDto.setName("Ateam");
        teamDto.setHomeGround("home1");
        teamDto.setCoach("yunho1");
        teamDto.setNumberOfPlayer(11);
        teamDto.setCapital(10000);
        teamDto.setWinScore(0);

        return teamDto;
    }

    private TeamDto getTeamDto2() {
        TeamDto teamDto = new TeamDto();

        teamDto.setName("Bteam");
        teamDto.setHomeGround("home2");
        teamDto.setCoach("yunho2");
        teamDto.setNumberOfPlayer(22);
        teamDto.setCapital(20000);
        teamDto.setWinScore(0);

        return teamDto;
    }

    private MatchDto getMatchDto() {
        MatchDto matchDto = new MatchDto();

        matchDto.setMatchPlace("Seoul");
        matchDto.setYear(2022);
        matchDto.setMonth(3);
        matchDto.setDay(12);
        matchDto.setHour(14);
        matchDto.setMinute(30);
        matchDto.setPremiumSeat(100);
        matchDto.setVipSeat(200);
        matchDto.setStandardSeat(300);

        return matchDto;
    }
}