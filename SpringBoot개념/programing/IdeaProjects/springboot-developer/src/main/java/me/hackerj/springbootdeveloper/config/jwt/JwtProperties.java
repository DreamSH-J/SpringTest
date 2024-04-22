package me.hackerj.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("jwt") // 자바 클래스에 프로퍼티 값을 가져와서 사용하는 어노테이션
public class JwtProperties {
 //application.yml 에서 심었던 값
    private String issuer;
    private String secretKey;
}
