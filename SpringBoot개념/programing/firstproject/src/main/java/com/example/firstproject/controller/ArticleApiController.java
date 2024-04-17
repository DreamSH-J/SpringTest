package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleService articleService;  // 서비스 객체 주입

    //GET
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }

    //    @Autowired
//    private ArticleRepository articleRepository; // 3. 게시글 레포지토리 주입
//
//    // GET
//    @GetMapping("api/articles")             // 1. URL 요청 접수
//    public List<Article> index() {           // 2. index() 메소드 정의
//        return articleRepository.findAll();
//    }
//
    @GetMapping("/api/articles/{id}")            // 1. 코드를 붙여 넣고 URL 수정
    public Article show(@PathVariable Long id) {  // 2. show() 메소드로 수정    3. URL의 id를 매개변수로 받아오기
        return articleService.show(id);
    }

    //
    // POST
    @PostMapping("/api/articles")               // 1. URL 요청 접수
    // 반환형 수집
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {         // 2. create() 메소드 정의
        Article created = articleService.create(dto);       // 1. 서비스로 게시글 생성
        //created로 객체 이름 변경
        return (created != null) ?      // 생성하면 정상, 실패하면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //
    // PATCH
    @PatchMapping("/api/articles/{id}")                  // 1. URL 요청 접수
    public ResponseEntity<Article> update(@PathVariable Long id,        // 2. update() 메소드 정의           3) 3. 반환형 수정
                                          @RequestBody ArticleForm dto) {
        Article updated = articleService.update(id, dto); // 서비스를 통해 게시글 수정
        return (updated != null) ?                        // 수정되면 정상, 안되면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    // DELETE
    @DeleteMapping("/api/articles/{id}")                        // 1. URL 요청 접수
    public ResponseEntity<Article> delete(@PathVariable Long id) {   // 2. 메소드 정의
        Article deleted = articleService.delete(id);     //서비스를 통해 게시글 삭제
        return (deleted != null) ?                       //삭제 결과에 따라 응답 처리
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleted) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/api/transaction-test")                       // 1. 여러 게시글 생성 요청 접수
    public ResponseEntity<List<Article>> transactionTest
            (@RequestBody List<ArticleForm> dtos) {              // 2. trasactionTest() 메소드 정의
        List<Article> createdList = articleService.createArticles(dtos);   // 1. 서비스 호출
        return (createdList != null) ?                          // 2. 생성 결과에 따라 응답 처리
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

