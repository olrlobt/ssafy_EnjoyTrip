package com.ssafy.ux.finalpj.board.controller;

import com.ssafy.ux.finalpj.board.model.BoardDto;
import com.ssafy.ux.finalpj.board.model.service.BoardService;
import com.ssafy.ux.finalpj.member.model.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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
    public ResponseEntity<?> list(HttpSession session, @PathVariable("type") String type,
                                  @RequestParam(required = false, defaultValue = "1") int pageNum) {
        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//        if (memberDto == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must login first.");
//        }
        try {
//            int totalPages = boardService.getTotalPage(type);
            List<BoardDto> list = boardService.listArticle(type, pageNum, 10);
            log.info("list size = {}" , list.size());
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
    public ResponseEntity<?> write(HttpSession session, @PathVariable("type") String type, @RequestBody BoardDto boardDto) {
        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//        if (memberDto == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must login first.");
//        }
//        boardDto.setUserId(memberDto.getUserId());
//        boardDto.setType(type);
        //임시로 userId, type 변경해주기
        boardDto.setUserId("user4");
        boardDto.setType("notice");
        System.out.println(boardDto);

        try {
            boardService.writeArticle(boardDto);
            return ResponseEntity.ok().body("Article created successfully.");
        } catch (Exception e) {
            log.error("Error writing article", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error writing article");
        }
    }

    @GetMapping("/modify")
    public ResponseEntity<?> modify(int articleNo) {
        try {
            BoardDto article = boardService.getArticleDetail(articleNo);

            if (article == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(article);
        } catch (Exception e) {
            log.error("Error during article modification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article modification.");
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modify(BoardDto boardDto) {
        try {
            boardService.modifyArticle(boardDto);

            return ResponseEntity.ok().body("Article modified successfully.");
        } catch (Exception e) {
            log.error("Error during article modification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article modification.");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(int articleNo) {
        try {
            boardService.deleteArticle(articleNo);
            return ResponseEntity.ok().body("Article deleted successfully.");
        } catch (Exception e) {
            log.error("Error during article deletion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article deletion.");
        }
    }


}
