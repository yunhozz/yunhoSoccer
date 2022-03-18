package yunhoSoccer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yunhoSoccer.domain.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long> {

}
