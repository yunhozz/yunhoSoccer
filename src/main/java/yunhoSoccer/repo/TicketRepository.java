package yunhoSoccer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yunhoSoccer.domain.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
