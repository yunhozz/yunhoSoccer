package yunhoSoccer.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.Member;
import yunhoSoccer.domain.Order;
import yunhoSoccer.domain.Grade;
import yunhoSoccer.dto.MatchDto;
import yunhoSoccer.controller.OrderMatchForm;
import yunhoSoccer.domain.MemberDto;
import yunhoSoccer.dto.TeamDto;
import yunhoSoccer.repo.MatchRepository;
import yunhoSoccer.repo.MemberRepository;
import yunhoSoccer.repo.OrderMatchRepository;
import yunhoSoccer.repo.OrderRepository;
import yunhoSoccer.repo.TeamRepository;
import yunhoSoccer.service.MatchService;
import yunhoSoccer.service.MemberService;
import yunhoSoccer.service.OrderMatchService;
import yunhoSoccer.service.OrderService;
import yunhoSoccer.service.TeamService;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderService orderService;
    @Autowired private OrderMatchRepository orderMatchRepository;
    @Autowired private OrderMatchService orderMatchService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private MemberService memberService;
    @Autowired private TeamRepository teamRepository;
    @Autowired private TeamService teamService;
    @Autowired private MatchRepository matchRepository;
    @Autowired private MatchService matchService;

    @Test
    @Rollback(value = false)
    void 주문_선택_취소() throws Exception {
        //given
        Member findMember = memberService.findMembers().stream().findFirst().get();
        List<Order> orderBasket = orderService.findOrderBasket(findMember.getId());

        //when
        orderService.cancelEachOrder(orderBasket.get(1).getId());

        //then
    }

    @Test
    @Rollback(value = false)
    void 주문_전체_취소() throws Exception {
        //given
        Member findMember = memberService.findMembers().stream().findFirst().get();
        List<Order> orderBasket = orderService.findOrderBasket(findMember.getId());
        List<Long> orderIds = orderBasket.stream().map(Order::getId).collect(Collectors.toList());

        //when
        orderService.cancelAllOrders(orderIds);

        //then
    }

    @BeforeEach
    void beforeEach() throws Exception {
        MemberDto memberDto = createMember();
        MatchDto matchDto = createMatch();
        TeamDto teamDto1 = createTeam1();
        TeamDto teamDto2 = createTeam2();
        OrderMatchForm form1 = new OrderMatchForm();
        OrderMatchForm form2 = new OrderMatchForm();

        Long memberId = memberService.join(memberDto);
        Long teamId1 = teamService.makeTeam(teamDto1);
        Long teamId2 = teamService.makeTeam(teamDto2);
        Long matchId = matchService.makeMatch(matchDto, teamId1, teamId2);

        form1.setMatch(matchRepository.findById(matchId).get());
        form1.setGrade(Grade.PREMIUM);
        form1.setCount(1);

        form2.setMatch(matchRepository.findById(matchId).get());
        form2.setGrade(Grade.VIP);
        form2.setCount(2);

        orderMatchService.makeOrder(memberId, matchId, form1);
        orderMatchService.makeOrder(memberId, matchId, form2);
    }

    private MemberDto createMember() {
        MemberDto memberDto = new MemberDto();
        memberDto.setUserId("qkrdbsgh");
        memberDto.setUserPw("1111");
        memberDto.setName("yunho");
        memberDto.setAge(27);
        memberDto.setCity("Seoul");
        memberDto.setStreet("Dongnipmoon");
        memberDto.setAccountNumber(1234567890);
        memberDto.setMoney(1000000);
        memberDto.setAuth("ADMIN");

        return memberDto;
    }

    private MatchDto createMatch() {
        MatchDto matchDto = new MatchDto();
        matchDto.setMatchPlace("Seoul");
        matchDto.setYear(2022);
        matchDto.setMonth(3);
        matchDto.setDay(12);
        matchDto.setHour(14);
        matchDto.setMinute(30);
        matchDto.setPremiumSeat(100);
        matchDto.setVipSeat(200);
        matchDto.setStandardSeat(300);

        return matchDto;
    }

    private TeamDto createTeam1() {
        TeamDto teamDto = new TeamDto();
        teamDto.setName("Ateam");
        teamDto.setHomeGround("home1");
        teamDto.setCoach("yunho1");
        teamDto.setNumberOfPlayer(11);
        teamDto.setCapital(10000);
        teamDto.setWinScore(0);

        return teamDto;
    }

    private TeamDto createTeam2() {
        TeamDto teamDto = new TeamDto();
        teamDto.setName("Bteam");
        teamDto.setHomeGround("home2");
        teamDto.setCoach("yunho2");
        teamDto.setNumberOfPlayer(22);
        teamDto.setCapital(20000);
        teamDto.setWinScore(0);

        return teamDto;
    }
}