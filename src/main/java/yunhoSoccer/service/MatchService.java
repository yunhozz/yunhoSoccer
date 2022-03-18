package yunhoSoccer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.Match;
import yunhoSoccer.domain.MatchTeam;
import yunhoSoccer.domain.Team;
import yunhoSoccer.dto.MatchDto;
import yunhoSoccer.repo.MatchRepository;
import yunhoSoccer.repo.TeamRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    /**
     * 경기 생성
     */
    @Transactional
    public Long makeMatch(MatchDto matchDto, Long teamId1, Long teamId2) {
        Team homeTeam = teamRepository.findById(teamId1).get();
        Team awayTeam = teamRepository.findById(teamId2).get();

        //create matchTeam
        MatchTeam matchTeam1 = new MatchTeam(homeTeam);
        MatchTeam matchTeam2 = new MatchTeam(awayTeam);

        Match match = matchDto.toEntity(matchTeam1, matchTeam2);
        matchRepository.save(match); //auto persist : matchTeam

        return match.getId();
    }

    /**
     * 경기 연기
     */
    @Transactional
    public void postpone(Long matchId, MatchDto matchDto) {
        Match match = matchRepository.findById(matchId).get();

        //연기하려는 시간이 원래 시간보다 더 빠르면 exception
        if (match.getMatchDate().isAfter(LocalDateTime.of(matchDto.getYear(), matchDto.getMonth(), matchDto.getDay(),
                matchDto.getHour(), matchDto.getMinute()))) {

            throw new IllegalStateException("Can't postpone match date.");
        }

        match.postponeMatch(matchDto.getYear(), matchDto.getMonth(), matchDto.getDay(), matchDto.getHour(), matchDto.getMinute());
    }

    /**
     * 경기 종료
     */
    @Transactional
    public void end(Long matchId, MatchDto matchDto) {
        Match findMatch = matchRepository.findById(matchId).get();
        findMatch.endMatch(matchDto.getHomeScore(), matchDto.getAwayScore());
    }

    public Match findMatch(Long matchId) {
        return matchRepository.findById(matchId)
                                .orElseThrow(() -> new IllegalStateException("Match is not exists."));
    }

    public List<Match> findMatches() {
        return matchRepository.findAll();
    }
}
