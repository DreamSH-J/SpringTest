package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController     // 1. REST 컨트롤러 선언
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    // 1. 댓글 조회
    /*
        1) @GetMapping() 으로 댓글 조회 요청을 받음 / 괄호 안에는 요청 URL인 api/articles/{articleId}/comments / 조회하는 게시글의 id는 매번 바뀌므로 {aritcleId}
        2) 메소드 이름은 comments() 로 지음 / 매개변수는 몇 번 게시글을 조회하는지 알아야 하므로 @GetMapping의 articleId 를 받아옴
            메소드의 반환형은 ResponseEntity<List<CommentDto> 로 정의
            왜 반환형이 댓글 엔티티 묶음인 List<Comment> 가 아니고 ResonseEntity<List<CommentDto> 일까?

            DB에서 조회한 댓글 엔티티 목록은 List<comment> 이지만 엔티티를 DTO로 변환하면 List<CommentDto>가 되기 때문
        3) 메소드 실행 결과는 아직 아무것도 없으므로 일단 null을 반환
        4) 서비스에 댓글 조회를 위임하기 위해 CommentService의 comments(articleId) 메소드를 호출
            comments() 메소드의 전달값으로 articleId를 넘긴 이유는?
            해당 게시글의 id를 알아야 해당 게시글의 댓글을 가져 올 수 있기 때문.

            메소드의 실행 결과로 반환받은 값은 List<CommentDto> (댓글 Dto) 묶음 타입의 dtos 라는 변수에 저장.
    */
    @GetMapping("/api/articles/{articleId}/comments")   // 1. 댓글 조회 요청 접수
    public ResponseEntity<List<CommentDto>> comments (@PathVariable Long articleId){    // 2. comments() 메소드 생성
        // 서비스에 위임  (CommentService의 comments() 메소드는 일부러 같게 지음)
        List<CommentDto> dtos = commentService.comments(articleId);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);    // 3. null 반환
//      return ResponseEntity.status(HttpStatus.OK).body(dtos);     // RestPool 방식을 쓰기 위함
    }
    // 2. 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")          // 1. 댓글 생성 요청 접수
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId,  // REST방식으로 응답해주기 위한 ResponseEntity
                                             @RequestBody CommentDto dto){  // 2. create() 메소드 생성
                                                                            // @RequestBody == HTTP 요청 본문에 실린 내용(JSON,XML,YAML)을 자바 객체로 변환해줌
        // 서비스에 위임
        CommentDto createdDto = commentService.create(articleId, dto);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);

//        return null;                                                        // 3. null 반환


    }
    // 3. 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id,
                                             @RequestBody CommentDto dto){
        // 서비스에 위임
        CommentDto updatedDto = commentService.update(id, dto);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
    // 4. 댓글 삭제
    @DeleteMapping("/api/comments/{id}")        // 1. 댓글 삭제 요청 접수
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){    // 2. delete() 메소드 생성
        // 서비스에 위임
        CommentDto deletedDto = commentService.delete(id);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);                                                    // 3. null 반환
    }
    /*
        요청을 받아 응답할 컨트롤러 만들기

    */

}
