package yunhoSoccer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.Member;
import yunhoSoccer.domain.MemberDto;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void initDb() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            MemberDto memberDto = new MemberDto();
            memberDto.setUserId("qkrdbsgh1");
            memberDto.setUserPw("1234");
            memberDto.setAge(27);

            Member member = memberDto.toEntity();
            em.persist(member);
        }

        public void dbInit2() {

        }
    }
}
