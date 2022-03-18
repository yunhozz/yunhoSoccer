package yunhoSoccer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yunhoSoccer.domain.teamplayer.TeamPlayer;

public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Long> {

}
