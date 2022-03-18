package yunhoSoccer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.Team;
import yunhoSoccer.dto.TeamDto;
import yunhoSoccer.repo.PlayerRepository;
import yunhoSoccer.repo.TeamRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    /**
     * 팀 생성
     */
    @Transactional
    public Long makeTeam(TeamDto teamDto) {
        Team team = teamDto.toEntity();
        teamRepository.save(team);

        return team.getId();
    }

    @Transactional
    public void winMatch(Long teamId) {
        Team team = teamRepository.findById(teamId).get();
        team.addWinScore();
    }

    @Transactional
    public void loseMatch(Long teamId) {
        Team team = teamRepository.findById(teamId).get();
        team.removeWinScore();
    }

    public Team findTeam(Long teamId) {
        return teamRepository.findById(teamId)
                                .orElseThrow(() -> new IllegalStateException("can't find team."));
    }

    public List<Team> findTeams() {
        return teamRepository.findAll();
    }
}
