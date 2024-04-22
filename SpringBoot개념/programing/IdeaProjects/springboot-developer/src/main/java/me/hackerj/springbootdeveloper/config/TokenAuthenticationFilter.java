package me.hackerj.springbootdeveloper.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hackerj.springbootdeveloper.config.jwt.TokenProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";
    /*
        요청 헤더에서 키가 'Authorization' 인 필드의 값을 가져온 다음 토큰의 접두사 Bearer를 제외한 값을 얻음
        만약 값이 null이거나 Bearer로 시작하지 않으면 null을 반환
    */

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // 요청 헤더의 Authorization 키의 값 조회
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        // 가져온 값에서 접두사 제거
        String token = getAccessToken(authorizationHeader);
        // 가져온 토큰이 유효한지 확인하고, 유효한 때는 인증 정보 설정(인증 정보를 관리하는 시큐리티 컨텍스트에 인증 정보를 설정)
        if (tokenProvider.validToken(token)){
            Authentication authentication = tokenProvider.getAuthenticaion(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        // 인증 정보가 설정된 이후에 컨텍스트 홀더에서 getAuthentication() 메서드를 사용해 인증 정보를 가져오면 유저 객체가 반환됨
        }
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

}
