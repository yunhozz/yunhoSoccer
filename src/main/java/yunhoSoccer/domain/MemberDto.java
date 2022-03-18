package yunhoSoccer.domain;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberDto {

    private Long id;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String userPw;

    private String name;
    private int age;
    private String city;
    private String street;
    private int accountNumber;
    private int money;
    private String auth;

    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .userPw(userPw)
                .name(name)
                .age(age)
                .city(city)
                .street(street)
                .account(new Account(accountNumber, money))
                .auth(auth)
                .build();
    }

    public Member toEntityWithPasswordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPw = encoder.encode(userPw);

        return Member.builder()
                .userId(userId)
                .userPw(encodedPw)
                .name(name)
                .age(age)
                .city(city)
                .street(street)
                .account(new Account(accountNumber, money))
                .auth(auth)
                .build();
    }
}
