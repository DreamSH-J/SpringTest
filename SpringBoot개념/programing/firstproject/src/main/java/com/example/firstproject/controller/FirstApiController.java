package com.example.firstproject.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class FirstApiController {
    @GetMapping("/api/hello")   // 1. URL 요청 접수
    public String hello(){      // 2. hello world! 문자열 반환
        return "hello world!";
    }
}

//@RestController    ==  데이터 반환(return "" 값을 그대로 반환)
//@Controller    ==  뷰 페이지 반환(return "" 값에 뷰 페이지 반환)