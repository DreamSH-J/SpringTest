package me.hackerj.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {
//    UserDetails를 상속받아 인증 객체로 사용
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Builder
    public User(String email, String password, String auth, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
    @Override   // 권한 반환
    /*
        getAuthorities()  반환타입  Collection<? extends GrantedAuthority>
        사용자가 가지고 있는 권한의 목록을 반환
        지금은 사용자 이외의 권한이 없기 때문에 user 권한만 담아 반환
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }
    // 사용자의 id를 반환(고유한 값)
    @Override
    /*
       getUsername()    반환타입    String
       사용자를 식별할 수 있는 사용자 이름을 반환
       이때 사용되는 사용자 이름은 반드시 고유해야함.
       여기서는 유니크 속성이 적용된 이메일을 반환함
    */
    public String getUsername() {
        return email;
    }
    // 사용자의 password 반환
    @Override
    /*
        getPassword()   반환타입    String
        사용자의 비밀번호를 반환
        이때 저장되어 있는 비밀번호는 암호화해서 저장해야함
    */
    public String getPassword() {
        return password;
    }
    // 계정 만료 여부 반환
    @Override
    /*
        isAccountNonExpired()   반환타입    Boolean
        계정이 만료되었는지 확인하는 메소드
        만약 만료되지 않은 때는 true 를 반환
    */
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true;    // true => 만료되지 않았음
    }
    // 계정 잠금 여부 반환
    @Override
    /*
        isAccountNonLocked()    반환타입    Boolean
        계정이 잠금되었는지 확인하는 메소드
        만약 잠금되지 않은 때는 true 를 반환
    */
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true;    //true => 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    /*
        isCredentialsNonExpired()    반환타입    Boolean
        비밀번호가 만료되었는지 확인하는 메소드
        만약 만료되지 않은 때는 true 를 반환
    */
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true;   //true => 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    /*
        isEnabled() 반환타입    Boolean
        계정이 사용 가능한지 확인하는 메소드
        만약 사용가능하다면 true 를 반환
    */
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true;   // true => 사용 가능
    }

    // 사용자 이름
    @Column(name = "nickname", unique = true)
    private String nickname;


    // 사용자 이름 변경
    public User update(String nickname) {
        this.nickname = nickname;
        return this;
    }
}
/*
    User 클래스가 상속한 UserDetails 클래스는 스프링 시큐리티에서
    사용자의 인증 정보를 담아두는 인터페이스

    스프링 시큐리티에서 해당 객체를 통해 인증 정보를 가져오려면 필수 오버라이드 메소드들을
    여러 개 사용해야 함
*/