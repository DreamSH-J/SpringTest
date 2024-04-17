package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
/*
    1) 이 클래스를 서비스로 선언
    2) 서비스와 함께 협업할 리포지토리 , 즉 댓글 리포지토리와 게시글 리포지토리 객체를 주입.

        댓글 리포지토리 뿐만 아니라 게시글 리포지토리까지 필요한 이유는?

        게시글 리포지토리가 있어야 댓글을 생성할 때 대상 게시들의 존재 여부를 파악할 수 있기 때문.



*/

@Service    // 1. 서비스로 선언
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;    // 2. 댓글 리포지토리 생성
    @Autowired
    private ArticleRepository articleRepository;    // 2. 게시글 리포지토리 생성

    // 서비스가 DB에서 데이터를 가져올 때 리포지토리에 시킴.
    // 댓글을 조회하므로 CommentRepository의 findByArticleId(articleId) 메소드를 호출

    public List<CommentDto> comments(Long articleId) {
        // 1. 댓글 조회
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        // 2. 엔티티 -> DTO 변환
/*
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for(int i = 0; i < comments.size(); i++){   // 1. 조회한 댓글 엔티티 수만큼 반복하기
        Comment c = comments.get(i);                // 2. 조회한 댓글 엔티티 하나씩 가져오기
        CommentDto dto = CommentDto.createCommentDto(c); // 3. 엔티티를 DTO 로 변환
        dtos.add(dto);                              // 4. 변환한 DTO 를 dtos 리스트에 삽입
        }

        return dtos;

*/
        // 3. 결과 반환
        return commentRepository.findByArticleId(articleId)             // 댓글 엔티티 목록 조회
                .stream()                                               // 댓글 엔티티 목록을 스트림으로 변환
                .map(comment -> CommentDto.createCommentDto(comment))   // 스트림의 각 요소(a)를 꺼내 b를 수행한 결과로 매핑
                .collect(Collectors.toList());                          // 스트림 데이터를 리스트 자료형으로 변환
                /*
                가져온 댓글 엔티티 목록을 스트림으로 바꿈.
                    스트림은 컬렉션이나 리스트에 저장된 요소들을 하나씩 참조하며 반복을 처리할 때 사용
                    1) 스트림화한 댓글 엔티티 목록을 DTO로 변환.
                        map() 메소드를 이용하면 스트림에서 요소를 꺼내 조건에 맞게 변환 가능

                        형식) .map(a -> b)    스트림의 각 요소(a)를 꺼내 b를 수행한 결과로 매핑

                        스트림에서 댓글 엔티티를 하나씩 꺼내 DTO로 매핑
                        .map(comment -> CommentDto.createCommentDto(comment))

                        각 엔티티 (comment)를 스트림으로부터 입력받아 createCommentDto() 메소드를 호출해 얻은 DTO를 다시 스트림에 매핑
                */
    }
@Transactional  // create() 메소드는 DB 내용을 바꾸기 때문에 실패할 경우를 대비해야 함.(문제가 발생했을 때 롤백)
    public CommentDto create(Long articleId, CommentDto dto) {
        // 1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)               // 1. 부모 게시글 가져오기
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! " +
                        "대상 게시글이 없습니다."));                              // 2. 없으면 에러 메시지를 출력
    /*
        orElseThrow() 메소드는 Optional 객체(존재할 수도 있지만 안 할 수도 있는 객체, 즉 null이 될 수도 있는 객체)에
        값이 존재하면 그 값을 반환하고, 값이 존재하지 않으면 전달값으로 보낸 예외를 발생시키는 메소드
        전달값으로 IllegalArgumentException 클래스를 사용하면 메소드가 잘못됐거나 부적합한 전달값을 보냈음을 나타냄
    */
        // 2. 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);
        // 3. 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);
        // 4. DTO로 변환해 반환
        return CommentDto.createCommentDto(created);
    }
    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 1. 댓글 조회 및 예외 발생

        Comment target = commentRepository.findById(id)         // 1. 수정할 댓글 가져오기
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패!" +
                        "대상 댓글이 없습니다."));                 // 2. 없으면 에러 메시지 출력
        // 2. 댓글 수정
        target.patch(dto);
        // 3. DB로 갱신
        Comment updated = commentRepository.save(target);
        // 4. 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);

    }

    public CommentDto delete(Long id) {
        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)         // 1. 삭제할 댓글 가져오기
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! " +
                        "대상이 없습니다."));                      // 2. 없으면 에러 메시지 출력
        // 2. 댓글 삭제
        commentRepository.delete(target);
        // 3. 삭제 댓글을 DTO로 변환 및 반환
        return CommentDto.createCommentDto(target);
    }
}
/*
    스트림의 특징
        서비스 코드의 for() 문을 스트림으로 개선
        스트림은 자바의 컬렉션(Collection),
        즉 리스트와 해시맵 등의 데이터 묶음을 요소별로 순차적으로 조작하는데 좋음

    스트림의 주요 특징
        1) 원본 데이터를 읽기만 하고 변경하지 않음
        2) 정렬된 결과를 컬렉션이나 배열에 담아 반환 가능
        3) 내부 반복문으로, 반복문이 코드상에 노출되지 않음
*/
