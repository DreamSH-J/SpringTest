package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller     // 1.컨트롤러 선언
public class FirstController {
    @GetMapping("/hi")      //대문자 인식 안됨    ( 2.클라이언트의 URL 요청을 받아 특정 컨트롤러의 메소드를 처리 )
    public String niceToMeetYou(Model model){      //메소드 작성   3. 메소드 수행 4. Model model == model 객체 받아오기
        model.addAttribute("username","HackerJ"); // 5. 모델 변수 등록
        return "greetings";     // 6. greetings.mustache 파일 반환
    }
    @GetMapping("/hi2")
    public String niceToMeetYou2(Model model){
        model.addAttribute("username","이순신");
        return "greetings";
    }
    @GetMapping("/bye")
    public String SeeYouNext(Model model){
        model.addAttribute("nickname", "HackerJ");
        return "goodbye";
    }
}
