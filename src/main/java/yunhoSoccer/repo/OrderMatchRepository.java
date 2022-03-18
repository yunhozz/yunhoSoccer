package yunhoSoccer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yunhoSoccer.domain.OrderMatch;

public interface OrderMatchRepository extends JpaRepository<OrderMatch, Long> {

}
