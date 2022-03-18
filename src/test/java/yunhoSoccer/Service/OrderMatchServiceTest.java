package yunhoSoccer.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.Member;
import yunhoSoccer.domain.OrderMatch;
import yunhoSoccer.domain.Grade;
import yunhoSoccer.dto.MatchDto;
import yunhoSoccer.controller.OrderMatchForm;
import yunhoSoccer.domain.MemberDto;
import yunhoSoccer.dto.TeamDto;
import yunhoSoccer.repo.*;
import yunhoSoccer.service.MatchService;
import yunhoSoccer.service.MemberService;
import yunhoSoccer.service.OrderMatchService;
import yunhoSoccer.service.TeamService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderMatchServiceTest {

    @Autowired private OrderMatchRepository orderMatchRepository;
    @Autowired private OrderMatchService orderMatchService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private MemberService memberService;
    @Autowired private MatchRepository matchRepository;
    @Autowired private MatchService matchService;
    @Autowired private TeamRepository teamRepository;
    @Autowired private TeamService teamService;

    @Test
    @Rollback(value = false)
    void 주문생성() throws Exception {
        //given
        MemberDto memberDto = createMember();
        MatchDto matchDto = createMatch();
        TeamDto teamDto1 = createTeam1();
        TeamDto teamDto2 = createTeam2();
        OrderMatchForm form1 = new OrderMatchForm();
        OrderMatchForm form2 = new OrderMatchForm();
        OrderMatchForm form3 = new OrderMatchForm();

        //when
        Long memberId = memberService.join(memberDto);
        Long teamId1 = teamService.makeTeam(teamDto1);
        Long teamId2 = teamService.makeTeam(teamDto2);
        Long matchId = matchService.makeMatch(matchDto, teamId1, teamId2);

        form1.setMatch(matchRepository.findById(matchId).get());
        form1.setGrade(Grade.PREMIUM); //100000원
        form1.setCount(1); //premium 좌석 1개 구매

        form2.setMatch(matchRepository.findById(matchId).get());
        form2.setGrade(Grade.VIP); //70000원
        form2.setCount(2); //vip 좌석 2개 구매

        form3.setMatch(matchRepository.findById(matchId).get());
        form3.setGrade(Grade.STANDARD); //50000원
        form3.setCount(4); //standard 좌석 4개 구매

        Long orderMatchId1 = orderMatchService.makeOrder(memberId, matchId, form1);
        Long orderMatchId2 = orderMatchService.makeOrder(memberId, matchId, form2);
        Long orderMatchId3 = orderMatchService.makeOrder(memberId, matchId, form3);

        //then
        OrderMatch orderMatch1 = orderMatchRepository.findById(orderMatchId1).get();
        OrderMatch orderMatch2 = orderMatchRepository.findById(orderMatchId2).get();
        OrderMatch orderMatch3 = orderMatchRepository.findById(orderMatchId3).get();
        Member member = memberRepository.findById(memberId).get();

        assertThat(orderMatch1.getGrade()).isEqualTo(Grade.PREMIUM);
        assertThat(orderMatch1.getCount()).isEqualTo(1);
        assertThat(orderMatch2.getGrade()).isEqualTo(Grade.VIP);
        assertThat(orderMatch2.getCount()).isEqualTo(2);
        assertThat(orderMatch3.getGrade()).isEqualTo(Grade.STANDARD);
        assertThat(orderMatch3.getCount()).isEqualTo(4);

        assertThat(orderMatch1.getTotalPrice()).isEqualTo(100000);
        assertThat(orderMatch2.getTotalPrice()).isEqualTo(140000);
        assertThat(orderMatch3.getTotalPrice()).isEqualTo(200000);
        assertThat(member.getAccount().getMoney()).isEqualTo(560000);
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