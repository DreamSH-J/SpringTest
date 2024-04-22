package me.hackerj.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import me.hackerj.springbootdeveloper.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
/* ************************************시큐리티 환경설정********************************************* */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userService;

    // 1) 스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }
    // 2) 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(auth -> auth // 3) 인증 , 인가 설정
                        .requestMatchers(
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/signup"),
                                new AntPathRequestMatcher("/user")
                        ).permitAll()   //접근 허용
                        .anyRequest().authenticated())  // 허용되지 않은 모든 요청에 인증
                .formLogin(formLogin -> formLogin   // 4) 폼 기반 로그인 설정
                        .loginPage("/login")
                        .defaultSuccessUrl("/articles")
                )
                .logout(logout -> logout        // 5) 로그아웃 설정
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                )
                .csrf(AbstractHttpConfigurer::disable)  // 6) csrf 비활성화
                .build();
    }
    //7) 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
  BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService)
    throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);    //8) 사용자 정보 서비스 설정
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }
    // 9) 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();     // 생성자를 만들면 알아서 암호화해줌
    }
}
/*
    1) 스프링 시큐리티의 모든 기능을 사용하지 않게 설정하는 코드
        즉, 인증, 인가 서비스를 모든 곳에 적용하지는 않음
           일반적으로 정적 리소스(이미지, HTML파일) 에 설정
           정적 리소스만 스프링 시큐리티 사용을 비활성화 하는데
           static 하위 경로에 있는 리소스와 h2의 데이터를 확인하는 데 사용하는 h2-console 하위
           url을 대상으로 ignoring() 메소드를 사용

    2) 특정 HTTP 요청에 대해 웹 기반 보안을 구성함
        이 메소드에서 인증/인가 및 로그인, 로그아웃 관련 설정을 할 수 있음

    3) 특정 경로에 대한 엑세스 설정을 함
    RequestMatchers() : 특정 요청과 일치하는 url에 대한 액세스를 설정
    permitAll() : 누구나 접근이 가능하게 설정
                    즉, "/login", "/signup", "/user"로 요청이 오면
                        인증/인가 없이도 접근할 수 있음
    anyRequest() : 위에서 설정한 url 이외의 요청에 대해서 설정
    authenticated() : 별도의 인가는 필요하지 않지만 인증이 성공된 상태여야 접근 할 수 있음

    4) 폼 기반 로그인 설정
    loginPage() : 로그인 페이지 경로를 설정
    defaultSuccessUrl() : 로그인이 완료되었을 때 이동할 경로를 설정

    5) 로그아웃 설정
    logoutSuccessUrl() : 로그아웃이 완료되었을때 이동할 경롤르 설정
    invalidateHttpSession() : 로그아웃 이후에 세션을 전체 삭제할지 여부를 설정

    6) CSRF 설정을 비활성화
        CSRF 공격을 방지하기 위해서는 활성화하는 게 좋지만 실습을 편리하게 하기 위해 비활성화

    7) 인증 관리자 관련 설정
        사용자 정보를 가져올 서비스를 재정의하거나, 인증 방법, 예를 들어
            LDAP, JDBC 기반 인증 등을 설정할 때 사용

    8) 사용자 서비스를 설정
    UserDetailsService() : 사용자 정보를 가져올 서비스를 설정
                            이때 설정하는 서비스 클래스는 반드시
                              UserDetailsService를 상속받은 클래스여야 함
    passwordEncoder() : 비밀번호를 암호화하기 위한 인코더를 설정

    9) 패스워드 인코더를 빈으로 등록함
*/
