package com.example.firstproject.entity;
import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "comment_list")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 자동적으로 1씩 증가
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @SequenceGenerator(name="comment_seq", sequenceName = "comment_seq", initialValue = 1, allocationSize = 1)
    private Long id;    // 대표키
    @ManyToOne  // comment 엔티티와 Article 엔티티를 다대일 관계로 설정
    @JoinColumn(name="article_id")    //(name="외래키_이름") 외래키 생성, Article 엔티티의 기본키(id)와 매핑
    private Article article;    // 해당 댓글의 부모 게시글
    @Column // 해당 필드를 테이블의 속성으로 매핑
    private String nickname;   // 댓글을 단 사람
    @Column // 해당 필드를 테이블의 속성으로 매핑
    private String body;    // 댓글 본문

    public static Comment createComment(CommentDto dto, Article article) {
        /*
        게시글이 일치하지 않는 경우애도 예외를 발생시킴
        dto에서 가져온 부모 게시글과 엔티티에서 가져온 부모 게시글의 id 가 다르면(dto.getArticleId() != article.getId()) 안됨
        이는 JSON 데이터와 URL 요청 정보가 다르다는 뜻이므로 올바르지 않음
        */
        // 예외 발생
        if(dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다");
        if(dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다");
        // 엔티티 생성 및 반환
        /*
            예외 상황이 발생하지 않았다면 엔티티를 만들어 반환하게 함.
            return 문에서 댓글 생성자를 호출
            전달값으로 필요한 요소인 id, nickname, body는 dto에서 가져오고
            부모 게시글은 article 자체를 입력
        */
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }

    public void patch(CommentDto dto) {
        // 예외 발생
        if (this.id != dto.getId()){
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다");
        }
        // 객체 갱신
        if(dto.getNickname() != null){  // 수정할 닉네임 데이터가 있다면(공백이 아니면)
            this.nickname = dto.getNickname();  // 내용 반영

        if (dto.getBody() != null){     //수정할 본문 데이터가 있다면
            this.body = dto.getBody();  // 내용 반영
        }
        }
    }
}
