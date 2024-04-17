package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller     // 1. 컨트롤러 선언  (자동 임포트)
public class FirstController2 {
    @GetMapping("/hey")
    public String NiceToMeetYou(Model model){   // 3. 메소드 파라미터로 모델 가져오기
        model.addAttribute("username2", "HackerJ"); // 4. 변수의 값에 따라서 다르게 나옴.
        return "greetings2";      //mustache 파일 이름만 적어주기    2. GetMapping("/~~")
    }
    @GetMapping("/see")
    public String SeeYou(Model model){
        model.addAttribute("nickname2", "HackerJ");
        return "goodbye2";
    }
}

/*
1. localhost:8080/hey 접속한것을 Controller가 받음
(요청과 동시에 수행이 됨 @GetMapping("/요청값") 메소드 return "mustache 이름")

2. 메소드가 반환하는 리턴값을 통해서 view페이지를 찾아서 보여줌
(greeting2  ==  mustache의 이름)

3. {{변수}} 를 사용하기 위해서는 Model을 거쳐야함.
(Model model을 통해서 model.addAttribute 사용)
*/
