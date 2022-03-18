package yunhoSoccer.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.Member;
import yunhoSoccer.domain.MemberDto;
import yunhoSoccer.repo.MemberRepository;
import yunhoSoccer.service.MemberService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private MemberService memberService;

    @Test
    void 회원가입() throws Exception {
        //given
        MemberDto memberDto1 = getMember1();
        MemberDto memberDto2 = getMember2();

        //when
        Long memberId1 = memberService.join(memberDto1);
        Long memberId2 = memberService.join(memberDto2);

        //then
        assertThat(memberRepository.findById(memberId1).get().getName()).isEqualTo("yunho1");
        assertThat(memberRepository.findById(memberId2).get().getName()).isEqualTo("yunho2");
    }

    @Test
    void 회원가입_중복_ID() throws Exception {
        //given
        MemberDto memberDto1 = getMember1();
        MemberDto memberDto2 = getMember1();

        //when
        memberService.join(memberDto1);

        //then
        try {
            memberService.join(memberDto2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void 회원정보_수정() throws Exception {
        //given
        MemberDto memberDto = getMember1();
        MemberDto updateDto = new MemberDto();

        updateDto.setName("Park Yunho");
        updateDto.setAge(12);
        updateDto.setCity("Incheon");
        updateDto.setStreet("Hong Dae");

        //when
        Long memberId = memberService.join(memberDto);
        memberService.update(memberId, "1111", updateDto);

        //then
        Member member = memberRepository.findById(memberId).get();

        assertThat(member.getName()).isEqualTo("Park Yunho");
        assertThat(member.getAge()).isEqualTo(12);
        assertThat(member.getId()).isEqualTo(memberId);
    }

    private MemberDto getMember1() {
        MemberDto memberDto = new MemberDto();

        memberDto.setUserId("qkrdbsgh1");
        memberDto.setUserPw("1111");
        memberDto.setName("yunho1");
        memberDto.setAge(11);
        memberDto.setCity("Seoul");
        memberDto.setStreet("Dongnipmoon");
        memberDto.setAccountNumber(1234567890);
        memberDto.setMoney(10000);
        memberDto.setAuth("ADMIN");

        return memberDto;
    }

    private MemberDto getMember2() {
        MemberDto memberDto = new MemberDto();

        memberDto.setUserId("qkrdbsgh2");
        memberDto.setUserPw("2222");
        memberDto.setName("yunho2");
        memberDto.setAge(22);
        memberDto.setCity("Busan");
        memberDto.setStreet("Han River");
        memberDto.setAccountNumber(547952845);
        memberDto.setMoney(20000);
        memberDto.setAuth("USER");

        return memberDto;
    }
}