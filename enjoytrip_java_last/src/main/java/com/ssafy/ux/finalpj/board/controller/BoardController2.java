package com.ssafy.ux.finalpj.board.controller;

import com.ssafy.ux.finalpj.board.model.BoardDto;
import com.ssafy.ux.finalpj.board.model.BoardListDto;
import com.ssafy.ux.finalpj.board.model.service.BoardService;
import com.ssafy.ux.finalpj.member.model.MemberDto;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/article")
public class BoardController2 {

    private BoardService boardService;

    public BoardController2(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/{type}")
    public ResponseEntity<?> list(@PathVariable("type") String type,
                                  @RequestParam(required = false, defaultValue = "1") int pgno) {
        try {
            BoardListDto list = boardService.listArticle(type, pgno, 5);
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            log.error("Error getting articles list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting articles list");
        }
    }

    @GetMapping("/writeForm")
    public ResponseEntity<?> write(HttpSession session, @PathVariable("type") String type) {
        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
        if (memberDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must login first.");
        }
        return ResponseEntity.ok().body("/article/write");
    }

    @PostMapping("/{type}/write")
    public ResponseEntity<?> write(@PathVariable("type") String type, @RequestBody BoardDto boardDto) {
        boardDto.setType(type);
        System.out.println(boardDto);

        try {
            boardService.writeArticle(boardDto);
            return ResponseEntity.ok().body("Article created successfully.");
        } catch (Exception e) {
            log.error("Error writing article", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error writing article");
        }
    }

    @GetMapping("{type}/view/{articleNo}")
    public ResponseEntity<?> detail(@PathVariable("type") String type, @PathVariable("articleNo")String articleNo) {
        try {
            System.out.println("articleNo: "+articleNo);
            BoardDto article = boardService.getArticleDetail(Integer.parseInt(articleNo));

            if (article == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(article);
        } catch (Exception e) {
            log.error("Error during article modification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article modification.");
        }
    }

    @GetMapping("{type}/modify/{articleNo}")
    public ResponseEntity<?> getModify( @PathVariable("type") String type, @PathVariable("articleNo")String articleNo) {
        try {
//    		System.out.println("articleNo: "+articleNo);
            BoardDto article = boardService.getArticleDetail(Integer.parseInt(articleNo));

            if (article == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(article);
        } catch (Exception e) {
            log.error("Error during article modification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article modification.");
        }
    }

    @PutMapping("{type}/modify")
    public ResponseEntity<?> modify( @PathVariable("type") String type, @RequestBody BoardDto boardDto) {
        try {
            boardService.modifyArticle(boardDto);
            return ResponseEntity.ok().body("Article modified successfully.");
        } catch (Exception e) {
            log.error("Error during article modification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article modification.");
        }
    }

    @DeleteMapping("{type}/delete/{articleNo}")
    public ResponseEntity<?> delete(@PathVariable("type") String type, @PathVariable String articleNo) {
        try {
            boardService.deleteArticle(Integer.parseInt(articleNo));
            return ResponseEntity.ok().body("Article deleted successfully.");
        } catch (Exception e) {
            log.error("Error during article deletion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article deletion.");
        }
    }


}
