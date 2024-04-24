package me.hackerj.springbootdeveloper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login(){
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
}
/*
    /login 경로로 접근하면 login() 메소드가 login.html 을,
    /signup 경로에 접근하면 signup() 메소드가 signup.html 을 반환
*/
