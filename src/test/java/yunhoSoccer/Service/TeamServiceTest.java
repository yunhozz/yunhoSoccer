package yunhoSoccer.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.Team;
import yunhoSoccer.dto.TeamDto;
import yunhoSoccer.repo.PlayerRepository;
import yunhoSoccer.repo.TeamRepository;
import yunhoSoccer.service.PlayerService;
import yunhoSoccer.service.TeamService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class TeamServiceTest {

    @Autowired private TeamRepository teamRepository;
    @Autowired private TeamService teamService;
    @Autowired private PlayerRepository playerRepository;
    @Autowired private PlayerService playerService;

    @Test
    void 팀_생성() throws Exception {
        //given
        TeamDto teamDto1 = createTeam1();
        TeamDto teamDto2 = createTeam2();

        //when
        Long teamId1 = teamService.makeTeam(teamDto1);
        Long teamId2 = teamService.makeTeam(teamDto2);

        //then
        Team team1 = teamRepository.getById(teamId1);
        Team team2 = teamRepository.getById(teamId2);

        assertThat(team1.getName()).isEqualTo("Ateam");
        assertThat(team2.getName()).isEqualTo("Bteam");
        assertThat(team1.getCoach()).isEqualTo("yunho1");
        assertThat(team2.getCoach()).isEqualTo("yunho2");
    }

    private TeamDto createTeam1() {
        TeamDto teamDto = new TeamDto();

        teamDto.setName("Ateam");
        teamDto.setHomeGround("home1");
        teamDto.setCoach("yunho1");
        teamDto.setNumberOfPlayer(11);
        teamDto.setCapital(10000);
        teamDto.setWinScore(0);

        return teamDto;
    }

    private TeamDto createTeam2() {
        TeamDto teamDto = new TeamDto();

        teamDto.setName("Bteam");
        teamDto.setHomeGround("home2");
        teamDto.setCoach("yunho2");
        teamDto.setNumberOfPlayer(22);
        teamDto.setCapital(20000);
        teamDto.setWinScore(0);

        return teamDto;
    }
}