package yunhoSoccer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.Member;
import yunhoSoccer.repo.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String userId, String userPw) {
        return memberRepository.findByUserId(userId)
                    .filter(m -> m.getUserPw().equals(userPw))
                    .orElse(null);
    }
}
