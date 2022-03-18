package yunhoSoccer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.Order;
import yunhoSoccer.repo.OrderRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * 주문 선택 취소
     */
    @Transactional
    public void cancelEachOrder(Long orderId) {
        Order findOrder = orderRepository.findById(orderId).get();
        findOrder.cancelOrder();
    }

    /**
     * 주문 전체 취소
     */
    @Transactional
    public void cancelAllOrders(List<Long> orderIds) {
        for (Long orderId : orderIds) {
            Order order = orderRepository.findById(orderId).get();
            order.cancelOrder();
        }
    }

    /**
     * 회원 전체의 주문 목록
     */
    public List<Order> findOrders() {
        return orderRepository.findAll();
    }

    /**
     * 회원 하나의 주문 목록 (=장바구니)
     */
    public List<Order> findOrderBasket(Long memberId) {
        return orderRepository.findOrderByMemberId(memberId)
                .orElseThrow(() -> new IllegalStateException("주문 내역이 없습니다."));
    }
}
