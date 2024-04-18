package me.hackerj.springbootdeveloper.domain;

//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity //엔티티로 지정
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Article {
//    @Id     // id 필드를 기본키로 지정
//    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 기본키를 자동으로 1씩 증가
//    @Column(name = "id", updatable = false)         // id 필드를 나타냄 , 업데이트 불가
//    private Long id;
//
//    @Column(name = "title", nullable = false)       // title 필드를 나타냄, null 불가
//    private String title;
//
//    @Column(name = "content", nullable = false)     // content 필드를 나타냄, null 불가
//    private String content;
//
//    @Builder    // Lombok 라이브러리에서 제공한 @Builder 어노테이션(setter 대신 사용 가능)
//                /*
//                    Article 클래스의 빌더 패턴을 구현한 것
//                    빌더 패턴을 자동으로 생성해줌
//                    빌더 패턴을 사용하면 객체를 생성할 때 setter 메소드를 사용하는 대신에 빌더 객체를 통해
//                    직관적이고 가독성 있는 방식으로 객체를 생성할 수 있음
//                    ** 필드 중 일부만 값을 설정 가능
//                    ** 순서에 상관없이 설정 할 수 있음
//                */
//    public Article(String title, String content) {  // 생성자: Article 객체를 생성할 때 제목(title)과 내용(content)를 인자로 받아 초기화하는 역할
//        this.title = title;
//        this.content = content;
//    }
//    public void update(String title, String content){
//        this.title = title;
//        this.content = content;
//    }
//}
    /* 기본 생성자

    protected Article(String title, String content){
        this.title = title;
        this.content = content;
    }

    */

    /*  게터

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    */
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}


