package yunhoSoccer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.*;
import yunhoSoccer.controller.OrderMatchForm;
import yunhoSoccer.repo.MatchRepository;
import yunhoSoccer.repo.MemberRepository;
import yunhoSoccer.repo.OrderMatchRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderMatchService {

    private final OrderMatchRepository orderMatchRepository;
    private final MemberRepository memberRepository;
    private final MatchRepository matchRepository;

    /**
     * 주문 생성
     */
    @Transactional
    public Long makeOrder(Long memberId, Long matchId, OrderMatchForm form) {
        Member member = memberRepository.findById(memberId).get();
        Match match = matchRepository.findById(matchId).get();

        Order order = Order.createOrder(member); //create order
        Ticket ticket = Ticket.createTicket(member.getUserId(), match.getMatchInfo()); //create ticket

        OrderMatch orderMatch = OrderMatch.createOrderMatch(order, form.getMatch(), ticket, form.getGrade(), form.getCount());
        orderMatchRepository.save(orderMatch); //auto persist : order, ticket

        return orderMatch.getId();
    }
}
