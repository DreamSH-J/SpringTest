package org.example.springbootdev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    @Autowired //MemberRepository를 사용하기 위한 의존성(빈) 주입
    MemberRepository memberRepository;

    public List<Member> getAllMembers(){
        return memberRepository.findAll();  //멤버 목록 얻기.
    }



}
