package me.hackerj.springbootdeveloper.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

public class CookieUtil {
    // 요청 값(이름, 값, 만료 기간)을 기반으로 새 쿠키를 만들고 HttpServletResponse에 추가
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
         /*
         경로(cookie.setPath("/"))를 "/"로 설정하는 것은
         해당 쿠키가 웹 애플리케이션의 모든 경로에서 사용될 수 있도록 하는 것
         */
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    // 쿠키의 이름을 입력받아 쿠키 삭제
    public static void deleteCookie(HttpServletRequest request,
                                    HttpServletResponse response ,
                                    String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return;
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    /*
    객체를 직렬화하고 Base64로 인코딩하여 문자열로 반환
    직렬화란 객체를 데이터 스트림으로 변환하는 프로세스를 의미
    객체를 직렬화 (
    SerializationUtils.serialize(obj)는
    Apache Commons 라이브러리의 SerializationUtils 클래스의 정적 메서드
    이 메서드는 주어진 객체(obj)를 직렬화하여 byte 배열로 반환
    )
     이를 통해 객체를 파일에 저장하거나 네트워크를 통해 전송할 수 있음
    그 결과를 Base64로 URL 안전하게 인코딩
    이를 통해 객체를 문자열로 변환하여 저장하거나 전송할 수 있음
    */
    public static String serialize(Object obj) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }
    /*
    쿠키에서 직렬화된 데이터를 역직렬화하여 객체로 변환하는 역할
    먼저, 쿠키에서 Base64로 인코딩된 문자열을 디코딩
    Apache Commons 의 SerializationUtils 를 사용하여
    해당 바이트 배열을 역직렬화하고 지정된 클래스 형식(cls)으로 캐스트
    */
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }
}
