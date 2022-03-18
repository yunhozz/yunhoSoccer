package yunhoSoccer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yunhoSoccer.domain.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

}
