package com.example.firstproject.repository;
//CrudRepository 패키지 자동 임포트
import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Override
    ArrayList<Article> findAll();    // Iterable -> ArrayList 수정
}

// 자식 ArticleReporsitory   extends =>  부모 CrudRepository
// 오버라이딩 -> 상위 클래스가 가지고 있는 메소드를 하위 클래스가 재정의해서 사용하는 것