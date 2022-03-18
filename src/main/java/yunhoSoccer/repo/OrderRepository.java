package yunhoSoccer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yunhoSoccer.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order ,Long> {

    @Query("select o from Order o join fetch o.member m where m.id = :id")
    Optional<List<Order>> findOrderByMemberId(@Param("id") Long memberId);

    @Query("select m from Member m where (select o from Order o where o.member = m) > 0")
    List<Order> findMemberAtLeastOneOrder();
}
