package me.hackerj.springbootdeveloper.repository;

import me.hackerj.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    //1) email로 사용자 정보를 가져옴
}
/*
    이메일로 사용자 정보를 식별 할 수 있음
    즉, 사용자 이름으로 봐도 무관함
    사용자 정보를 가져오기 위해서는 스프링 시큐리티가 이메일을 전달받아야 함

    스프링 데이터 JPA 는 메소드 규칙에 맞춰 메소드를 선언하면 이름을 분석해 자동으로 쿼리를 생성해줌

    findByEmail() 메소드는 실제 데이터베이스에 회원 정보를 요청할 때

    FROM Users
    WHERE email = #{email}
*/
