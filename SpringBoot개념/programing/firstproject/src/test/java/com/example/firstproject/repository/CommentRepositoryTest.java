package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest            // 1. 해당 클래스를 JPA와 연동해 테스팅
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;    // 2. commentRepository 객체 주입
    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId()
        /* Case 1: 4번 게시글의 모든 댓글 조회 */
    {
        // 1. 입력 데이터 준비
        Long articleId = 4L;    // 4번 게시글의 모든 댓글을 조회하므로 articleId에 4L을 넣음
        // 2. 실제 데이터
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        // 3. 예상 데이터
        Article article = new Article(4L, "당신의 인생 영화는?", "댓글 고");   // 2. 부모 게시글 객체 생성
        Comment a = new Comment(1L, article,"Park","굿 윌 헌팅");
        Comment b = new Comment(2L, article,"Kim","아이 엠 샘");           // 1. 댓글 객체 생성
        Comment c = new Comment(3L, article,"Choi","쇼생크 탈출");
        List<Comment> expected = Arrays.asList(a, b, c);                                   // 3. 댓글 객체 합치기
            /*
               1. comment 테이블에서 확인한 4번 게시글의 댓글을 Comment a, b, c 객체에 저장
               2. a,b,c 객체의 두 번째 필드는 부모 게시글인 article임. 따라서 article 객체도 생성.
               여기서는 4번 게시글의 댓글을 조회하고 있으므로 객체 값으로 4번 게시글의 id,title,content를 저장
               3. 마지막으로 a,b,c 객체를 하나의 리스트로 합치고 이를 expected 리스트에 저장
             */

        // 4. 비교 및 검증       // 예상 데이터의 문자열(expected.toString())과 실제 데이터의 문자열(comments.toString())이 같은지 비교
                               // 메소드의 마지막 전달값으로 검증이 실패했을 때 출력할 메시지를 넣음
        assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력");
    }
    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname()            /*
            1. @DisplayName으로 테스트 이름을 설정
            2. 첫 번째 테스트 케이스로 Park의 모든 댓글을 조회 테스트 케이스를 중괄호로 묶고 테스트 단계를 주석으로 씀.
            3. 실제 데이터를 가져옴 . commentRepository.findByNickname(nickname) 메소드를 호출해 얻은 결과를 comments 리스트에 저장
            4. 입력 데이터에는 Park이 쓴 모든 댓글을 조회하므로 nickname에 "Park"을 넣음
            5. 예상 데이터를 작성하기 위해 http://localhost:8080/h2-console 페이지로 가서 DB에 접속
                comment 테이블을 선택한 후 [Run] 클릭 > 테이블의 내용을 확인 / Park이 작성한 댓글은 1, 4, 7 번
                1- Park 이 작성한 댓글 1, 4, 7번 데이터를 Comment a, b, c 객체에 저장
                    1번 댓글의 부모(article)는 4번 , 4번 댓글의 부모(article)는 5번, 7번 댓글의 부모(article)은 6번으로 부모 게시글이 모두 다름
                    이 경우 하나의 article 객체를 만들어 참조 할 수 없음 / a, b, c 객체 생성 시 article 필드에 각각 객체를 생성함
                2- a, b, c 객체를 하나의 리스트로 합치고 이를 expected 리스트에 저장함
             6. assertEquals() 메소드로 예상 데이터의 문자열(expected.toString())과 실제 데이터의 문자열 (comment.toString())이 같은지 비교하고
                                                                                                 검증에 실패 할 경우 보여 줄 메시지 입력
             7. 테스트가 잘 동작하는지 findByNickname()  메소드의 시작 행에 있는 실행 버튼을 클릭하고 Run 'CommentRepository'를 선택
            */
    /* Case 1: "Park"의 모든 댓글 조회 */
    {
        // 1. 입력 데이터 준비
        String nickname = "Park";
        // 2. 실제 데이터
        List<Comment> comments = commentRepository.findByNickname(nickname);
        // 3. 예상 데이터
        Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 고"), nickname, "굿 윌 헌팅");
        Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 고고"), nickname, "치킨");
        Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 고고고"), nickname, "조깅");
        List<Comment> expected = Arrays.asList(a, b, c);
        // 4. 비교 및 검증
        assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력");

    }
}