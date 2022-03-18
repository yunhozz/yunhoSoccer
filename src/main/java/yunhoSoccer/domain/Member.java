package yunhoSoccer.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, length = 20)
    private String userId;

    @Column(length = 100)
    private String userPw;

    private String name;

    private int age;

    private String city;

    private String street;

    @Embedded
    private Account account;

    private String auth;

    @Builder
    private Member(String userId, String userPw, String name, int age, String city, String street, Account account, String auth) {
        this.userId = userId;
        this.userPw = userPw;
        this.name = name;
        this.age = age;
        this.city = city;
        this.street = street;
        this.account = account;
        this.auth = auth;
    }

    public void updateMember(String name, int age, String city, String street) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.street = street;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();

        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }

        return roles;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
