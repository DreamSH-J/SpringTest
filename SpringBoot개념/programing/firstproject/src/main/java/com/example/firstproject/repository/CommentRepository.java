package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//    JpaRepository == ListCrudRepository와 ListPagingAndSortingRepository 인테페이스를 상속 받아 만들어진 것
                        //Crud 작업           //페이징 처리와 정렬 작업
//    Jpa에서 CRUD 작업만 하려면 CrudRepository만 사용해도 충분.
//    Crud 작업에 더해서 페이지 처리와 정렬 작업까지 해야 한다면 JpaRepository 사용
//    native query method   ==  작성한 SQL 쿼리를 리포지토리 메소드로 사용 할 수 있게 해줌.
//    @Query(value= "쿼리", nativeQuery= true)
//    @Query 어노테이션은 SQL과 유사한 JPQL(Java Persistance Query Language)이라는 객체 지향 쿼리 언어를 통해 복잡한 쿼리 처리를 지원
//    nativeQuery 속성을 true로 하면 기존 SQL 문을 그대로 사용할 수 있음.
//    주의할점 SQL문의 WHERE 절에 조건을 쓸 때 매개변수 앞에 꼭!! 콜론(:)을 붙여야함.
public interface CommentRepository extends JpaRepository<Comment, Long> {   // <대상 엔티티, 대표키 값의 타입>
    // 1. 특정 게시글의 모든 댓글 조회
    // 덧셈 연산자를 통해 가독성을 높여줄 수 있음
    @Query(value= "SELECT * " +
                  "FROM comment " +
                  "WHERE article_id = :articleId",
                   nativeQuery = true)    //value 속성에 실행하려는 쿼리 작성
    List<Comment> findByArticleId(Long articleId);
    // 2. 특정 닉네임의 모든 댓글 조회  (메소드의 실행 결과로 댓글의 묶음을 반환하므로 반환형은 List<Comment>로 작성
    // findByNickName() 메소드에서 수행할 쿼리를 이번에는 XML로 작성
    // native query XML == 네이티브 쿼리 XML == 기본 경로 == META-INF > orm.xml == 이 경로로 파일을 만들면 XML이 자동으로 인식
    // resources > META-INF > orm.xml
    List<Comment> findByNickname(String nickname);
}
