package org.example.springbootdev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired //의존성 주입
    TestService testService;

    @GetMapping("/test")
    public List<Member> getAllMembers() { //리스트 타입을 받는 getAllmembers 메서드 생성.
        List<Member> members = testService.getAllMembers();
        return members;

    }
}




