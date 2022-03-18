package yunhoSoccer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yunhoSoccer.domain.MatchTeam;

public interface MatchTeamRepository extends JpaRepository<MatchTeam, Long> {

}
