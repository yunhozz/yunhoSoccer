package yunhoSoccer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yunhoSoccer.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
