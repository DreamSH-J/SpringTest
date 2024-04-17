package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor     //Article 생성자를 대체하는 어노테이션 추가
@NoArgsConstructor      // 기본 생성자 추가 어노테이션
@ToString               //toString() 메소드를 대체하는 어노테이션 추가
@Getter                 //Getter를 대체하는 어노테이션 추가

public class Article {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id 자동 생성
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_seq")
    @SequenceGenerator(name="article_seq", sequenceName = "article_seq", initialValue = 1, allocationSize = 1)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

    public void patch(Article article) {
        if(article.title != null)
            this.title = article.title;
        if(article.content != null)
            this.content = article.content;
    }


//    Article(){      //기본 생성자
//
//    }

//    public Article(Long id, String title, String content) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//    }

//    @Override
//    public String toString() {
//        return "Article{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }
}
