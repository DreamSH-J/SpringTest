package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired      //스프링 부트가 미리 생성해 놓은 레포지토리 객제 주입(DI)     ** 의존성 주입 **
private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;      // 서비스 객체 주입

    @GetMapping("/articles/new")
        public String newArticleForm(){
            return "articles/new";
        }
    @PostMapping("/articles/create")
        public String createArticle(ArticleForm form){

            log.info(form.toString());              // 로깅 코드 추가
//            System.out.println(form.toString());     기존 println()문 주석처리
            // 1. DTO를 엔티티로 변환
            Article article = form.toEntity();
            log.info(article.toString());
//            System.out.println(article.toString());
            // 2. 레포지토리로 엔티티를 DB에 저장
            Article saved = articleRepository.save(article);
            log.info(saved.toString());
//            System.out.println(saved.toString());
            return "redirect:/articles/" + saved.getId();
        }
        @GetMapping("/articles/{id}")       // 데이터 조회 요청 접수
        public String show(@PathVariable Long id, Model model){      // 매개변수로 id 받아오기        모델을 사용하기 위해  model 객체 받아오기
            log.info("id =" + id);
            // 1. id를 조회해 데이터 가져오기
//            Article articleEntity = articleRepository.findById(id);   빨간 물결 표시  articleRepository가 findBy(id)로 찾은 값을 반환 할 때 반환형이 Article이 아니기때문에 발생하는 문제
//            Optional<Article> aricleEntity = articleRepository.findById(id);   반환형 == Optional<Article> 타입    자바 8 이상 사용 가능
              Article articleEntity = articleRepository.findById(id).orElse(null);  // id 값이 없으면 null을 반환하라는 뜻 ( 값이 있으면 articleEntity 변수에 값을 넣고 없으면 null을 저장 )
              List<CommentDto> commentsDtos = commentService.comments(id);  // CommentService의 comments(id) 메소드를 호출해 조회한 댓글 목록을 List<CommentsDto> 타입의 commentsDtos 뱐수에 저장
            // 2. 모델에 데이터 등록하기  (articleEntity에 담긴 데이터를 모델에 등록하는 이유 MVC 패턴에 따라 조회한 데이터를 뷰 페이지에서 사용하기 위함)
              //name 이라는 이름으로 value 객체 추가
              //model.addAttribute(String name, Object value);
              model.addAttribute("article", articleEntity);
              model.addAttribute("commentsDtos", commentsDtos); // 댓글 목록 모델에 등록
            // 3. 뷰 페이지 반환하기
              return "articles/show";
        }
        @GetMapping("/articles")
        public String index(Model model){       // 모델 객체 받아오기
            // 1. 모든 데이터 가져오기
            ArrayList<Article> articleEntityList = articleRepository.findAll();    // findAll() 메소드가 반환하는 데이터 타입은 Iterable인데 작성한 타입은 List
            // ArrayList의 상위 타입인 List로도 업캐스팅 가능
            // 해결방법 3가지 (메모장 참고)
            model.addAttribute("articleList", articleEntityList);   // articleEntityList 등록
            // 2. 모델에 데이터 등록하기
            // 3. 사용자에게 보여 줄 뷰 페이지 설정하기
            return "articles/index";
        }
        @GetMapping("/articles/{id}/edit")
        public String edit(@PathVariable Long id, Model model){  // 2. id를 매개변수로 받아오기   1. model 객체 받아오기
            // 수정할 데이터 가져오기
            Article artilceEntity = articleRepository.findById(id).orElse(null); // 1. DB에서 수정할 데이터 가져오기
            // 모델에 데이터 등록하기
            model.addAttribute("article", artilceEntity);   // 2. articleEntity를 article로 등록
            // 뷰 페이지 설정하기
            return "articles/edit";
        }
        @PostMapping("/articles/update") // 2. URL 요청 접수
        public String update(ArticleForm form){     // 1. 메소드 생성    매개 변수로 DTO 받아 오기
        // 수정 폼에서 전송한 데이터는 DTO에서 받음
            log.info(form.toString());
            // 1. DTO를 엔티티로 변환하기
            Article articleEntity = form.toEntity();    //1. Dto(form)를 엔티티(articleEntity)로 변환하기
            log.info(articleEntity.toString());         //2. 엔티티로 잘 변환됐는지 로그 찍기
            // 2. 엔티티를 DB에 저장하기
            // 2-1. DB에서 기존 데이터 가져오기
            Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
//           2. 데이터 저장                        1. DB에서 데이터 찾기              3. 데이터가 없으면 null 반환
            // 2-2. 기존 데이터 값을 갱신하기
            log.info(target.toString());
            
            if (target != null){
                articleRepository.save(articleEntity);  //엔티티를 DB에 저장(갱신)
            // target이 null이 아니면 (target != null), 즉 기존 데이터가 있다면 articleRepository에 저장된 내용을 DB로 갱신
            }
            // 3. 수정 결과 페이지를 리다이렉트하기
            return "redirect:/articles/" + articleEntity.getId();
        }
        @GetMapping("/articles/{id}/delete")
        public String delete(@PathVariable Long id, RedirectAttributes rttr){    // 2. id를 매개변수로 가져오기
//        RedirectAttributes(넘겨_주려는_키_문자열, 넘겨_주려는_값_객체);
            log.info("삭제 요청이 들어왔습니다!!");
            // 1. 삭제할 대상 가져오기
            Article target = articleRepository.findById(id).orElse(null);   // 1. 데이터 찾기
            rttr.addFlashAttribute("msg", "삭제 완료!");
            log.info(target.toString());
            // 2. 대상 엔티티 삭제하기
            if(target != null){                     // 1. 삭제할 대상이 있는지 확인
                articleRepository.delete(target);   // 2. delete() 메소드로 대상 삭제
            }
            // 3. 결과 뷰에 리다이렉트
            return "redirect:/articles";
        }
}

// println()을 사용하지 않으려면 로깅을 해야함.
// @Slf4j   ==    Simple Logging Facade for Java  ==   로깅 기능 사용 가능
// println()문으로 확인했을 때는 뒷부분만 출력하지만
// 리팩터링 한 후에는 ArticleController에서 어떤 데이터가 언제 저장됐는지 시간 정보도 남아 있음
