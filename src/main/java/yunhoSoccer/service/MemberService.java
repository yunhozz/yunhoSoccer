package yunhoSoccer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.Member;
import yunhoSoccer.domain.MemberDto;
import yunhoSoccer.repo.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(MemberDto memberDto) {
        Member member = memberDto.toEntityWithPasswordEncoder();
        List<Member> duplicateMembers = findMembers().stream()
                                            .filter(m -> m.getUserId().equals(member.getUserId()))
                                            .toList();

        if (duplicateMembers.size() != 0) {
            throw new IllegalStateException("중복된 회원 ID가 존재합니다.");
        }

        memberRepository.save(member);

        return member.getId();
    }

    /**
     * 회원정보 수정
     */
    @Transactional
    public void update(Long memberId, String verifyPw, MemberDto memberDto) {
        Member member = memberRepository.findById(memberId).get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //수정 전 비밀번호 입력하여 확인 -> 일치하면 수정 가능, 일치하지 않으면 exception
        if (encoder.matches(verifyPw, member.getUserPw())) {
            member.updateMember(memberDto.getName(), memberDto.getAge(), memberDto.getCity(), memberDto.getStreet());

        } else {
            throw new IllegalStateException("입력하신 비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * 회원 한명 조회
     */
    public Optional<Member> findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /**
     * 회원 리스트 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByUserId(userId)
                                .orElseThrow(() -> new UsernameNotFoundException(userId));
    }
}
