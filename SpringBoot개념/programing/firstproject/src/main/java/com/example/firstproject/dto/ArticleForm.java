package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
//3. AllArgsConstructor 패키지 자동 임포트
import lombok.ToString;
//5. ToSting 패키지 자동 임포트

@AllArgsConstructor     //2. 새 어노테이션 추가
@ToString               //5. 새 어노테이션 추가
public class ArticleForm {
    private Long id;        // 1. id 필드 추가
    private String title;
    private String content;

    //1. 생성자 전체 삭제
//    public ArticleForm(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }
    //4. 전체 메소드 삭제
//    @Override
//    public String toString() {
//        return "ArticleForm{" +
//                "title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }

    public Article toEntity() {
        return new Article(id, title, content);     // 2. null -< id 로 수정
    }
}
