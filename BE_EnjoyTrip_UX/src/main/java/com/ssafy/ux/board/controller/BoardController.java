package com.ssafy.ux.board.controller;

import com.ssafy.ux.board.model.BoardDto;
import com.ssafy.ux.board.model.BoardListDto;
import com.ssafy.ux.board.model.service.BoardService;

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

import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/article")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/{type}")
    public ResponseEntity<?> list(@RequestParam(required = false, defaultValue = "1") int pgno, @RequestParam Map<String, String> params) {
        try {
            BoardListDto list = boardService.listArticle(params);
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            log.error("Error getting articles list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting articles list");
        }
    }

    @PostMapping("/{type}/write")
    public ResponseEntity<?> write( @RequestBody BoardDto boardDto) {
        try {
            boardService.writeArticle(boardDto);
            return ResponseEntity.ok().body("Article created successfully.");
        } catch (Exception e) {
            log.error("Error writing article", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error writing article");
        }
    }

    @GetMapping("{type}/view/{articleNo}")
    public ResponseEntity<?> detail(@PathVariable("articleNo")String articleNo) {
        try {
            BoardDto article = boardService.getArticleDetail(Integer.parseInt(articleNo));
            boardService.updateHit(Integer.parseInt(articleNo));
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
    public ResponseEntity<?> getModify(@PathVariable("articleNo")String articleNo) {
        try {
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
    public ResponseEntity<?> modify(@RequestBody BoardDto boardDto) {
        try {
            boardService.modifyArticle(boardDto);
            return ResponseEntity.ok().body("Article modified successfully.");
        } catch (Exception e) {
            log.error("Error during article modification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article modification.");
        }
    }

    @DeleteMapping("{type}/delete/{articleNo}")
    public ResponseEntity<?> delete( @PathVariable String articleNo) {
        try {
            boardService.deleteArticle(Integer.parseInt(articleNo));
            return ResponseEntity.ok().body("Article deleted successfully.");
        } catch (Exception e) {
            log.error("Error during article deletion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article deletion.");
        }
    }
}
