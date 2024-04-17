package me.hackerj.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.hackerj.springbootdeveloper.domain.Article;
import me.hackerj.springbootdeveloper.dto.AddArticleRequest;
import me.hackerj.springbootdeveloper.dto.ArticleResponse;
import me.hackerj.springbootdeveloper.dto.UpdateArticleRequest;
import me.hackerj.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor // final 이 붙거나 @NotNull 이 붙은 필드의 생성자 추가
@RestController
public class BlogApiController {
    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = blogService.findAll()
                .stream()                           //stream으로 직렬화
                .map(ArticleResponse::new)
                .toList();      // 리스트에 담기 위해서 toList()

        return ResponseEntity.ok()
                .body(articles);
    }
    @GetMapping("/api/articles/{id}")       // URL에서 {id}에 해당하는 값이 id 로 들어옴
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id){
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id){
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}
