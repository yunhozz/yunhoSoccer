package yunhoSoccer.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.*;
import yunhoSoccer.dto.PlayerDto;
import yunhoSoccer.dto.TeamDto;
import yunhoSoccer.repo.PlayerRepository;
import yunhoSoccer.repo.TeamPlayerRepository;
import yunhoSoccer.repo.TeamRepository;
import yunhoSoccer.repo.TradeRepository;
import yunhoSoccer.service.PlayerService;
import yunhoSoccer.service.TeamService;
import yunhoSoccer.service.TradeService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class TradeServiceTest {

    @Autowired private TradeRepository tradeRepository;
    @Autowired private TradeService tradeService;
    @Autowired private PlayerRepository playerRepository;
    @Autowired private PlayerService playerService;
    @Autowired private TeamRepository teamRepository;
    @Autowired private TeamService teamService;
    @Autowired private TeamPlayerRepository teamPlayerRepository;

    @Test
    @Rollback(value = false)
    void 선수_영입_방출() throws Exception {
        //given
        Team team = teamService.findTeam(1L);
        Player player1 = playerService.findPlayer(2L).get();
        Player player2 = playerService.findPlayer(3L).get();
        Player player3 = playerService.findPlayer(4L).get();

        //when
        tradeService.makeTrade4recruit(team.getId(), player1.getId(), Position.FORWARD); //영입 (5L)
        tradeService.makeTrade4recruit(team.getId(), player2.getId(), Position.DEFENDER); //영입 (8L)
        tradeService.makeTrade4recruit(team.getId(), player3.getId(), Position.GOALKEEPER); //영입 (11L)

        tradeService.makeTrade4release(team.getId(), 8L, 50000); //방출 (8L)

        //then
        assertThat(team.getNumberOfPlayer()).isEqualTo(2);
        assertThat(team.getCapital()).isEqualTo(75000);

        assertThat(player1.getPlayerStatus()).isEqualTo(PlayerStatus.TRADED);
        assertThat(player2.getPlayerStatus()).isEqualTo(PlayerStatus.WAITING);
        assertThat(player3.getPlayerStatus()).isEqualTo(PlayerStatus.TRADED);

        assertThat(player2.getPrice()).isEqualTo(50000);
    }

    @BeforeEach
    void beforeEach() {
        TeamDto teamDto = createTeam();
        teamService.makeTeam(teamDto);

        PlayerDto playerDto1 = createPlayer("playerA", 18, 180.5, 87.2, MainFoot.RIGHT, 20000, PlayerStatus.WAITING);
        PlayerDto playerDto2 = createPlayer("playerB", 20, 174.5, 76.8, MainFoot.BOTH, 30000, PlayerStatus.WAITING);
        PlayerDto playerDto3 = createPlayer("playerC", 22, 187.3, 89.7, MainFoot.LEFT, 25000, PlayerStatus.WAITING);
        playerService.makePlayer(playerDto1);
        playerService.makePlayer(playerDto2);
        playerService.makePlayer(playerDto3);
    }

    private TeamDto createTeam() {
        TeamDto teamDto = new TeamDto();
        teamDto.setName("YunhoTeam");
        teamDto.setHomeGround("home");
        teamDto.setCoach("yunho");
        teamDto.setNumberOfPlayer(0);
        teamDto.setCapital(100000);
        teamDto.setWinScore(0);

        return teamDto;
    }

    private PlayerDto createPlayer(String name, int age, double h, double w, MainFoot foot, int price, PlayerStatus status) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(name);
        playerDto.setAge(age);
        playerDto.setHeight(h);
        playerDto.setWeight(w);
        playerDto.setMainFoot(foot);
        playerDto.setPrice(price);
        playerDto.setPlayerStatus(status);

        return playerDto;
    }
}