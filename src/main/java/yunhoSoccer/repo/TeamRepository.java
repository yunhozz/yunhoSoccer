package yunhoSoccer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yunhoSoccer.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
