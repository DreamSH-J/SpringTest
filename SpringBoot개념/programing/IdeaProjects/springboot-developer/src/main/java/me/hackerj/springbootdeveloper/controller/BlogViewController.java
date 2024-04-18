package me.hackerj.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.hackerj.springbootdeveloper.domain.Article;
import me.hackerj.springbootdeveloper.dto.ArticleListViewResponse;
import me.hackerj.springbootdeveloper.dto.ArticleViewResponse;
import me.hackerj.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model){
        List<ArticleListViewResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles",articles);   // 1) 블로그 글 리스트 저장

        return "articleList";   // 2) articleList.html 이라는 뷰 조회
    }
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
        /*
            getArticle() 메소느는 인자 id에 URL로 넘어온 값을 받아 findById() 메소드로 넘겨
            글을 조회하고,
            화면에서 사용할 모델에 데이터를 저장한 다음, 보여줄 화면의 템플릿 이름을 반환함
        */
    }
    @GetMapping("/new-article")
    // 1) id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑(id는 없을 수도 있음)
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {   // 2) id 가 없으면 생성
            model.addAttribute("article", new ArticleViewResponse());
        } else {    // id가 없으면 수정
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}


/*
    articles 키에 블로그 글들을, 즉, 글 리스트를 저장함

    반환값인 articleList 는 resource/templates/articleList.html 을 찾도록 뷰 이름을 적은것

*/
