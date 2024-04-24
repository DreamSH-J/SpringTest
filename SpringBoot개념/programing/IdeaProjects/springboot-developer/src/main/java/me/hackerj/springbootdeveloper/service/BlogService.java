package me.hackerj.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hackerj.springbootdeveloper.domain.Article;
import me.hackerj.springbootdeveloper.dto.AddArticleRequest;
import me.hackerj.springbootdeveloper.dto.UpdateArticleRequest;
import me.hackerj.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request, String userName){
        return blogRepository.save(request.toEntity(userName));
    }
    public List<Article> findAll(){
        return blogRepository.findAll();
    }
    /*
        findById()메소드를 사용하여 ID 를 받아 엔티티를 조회하고 없으면 IllegalArgumentException 예외를 발생
    */
    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(Long id) {
       Article article = blogRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

       authorizeArticleAuthor(article);
       blogRepository.delete(article);
    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    // 게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        if(!article.getAuthor().equals(userName)){
            throw new IllegalArgumentException("not authorized");
        }
    }
}
