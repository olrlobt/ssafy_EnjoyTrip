package com.ssafy.ux.finalpj.board.controller;

import com.ssafy.ux.finalpj.board.model.BoardDto;
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
    public ResponseEntity<?> list(HttpSession session, @PathVariable("type") String type,
                                  @RequestParam(required = false, defaultValue = "1") int pageNum) {
        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//        if (memberDto == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must login first.");
//        }
        try {
//            int totalPages = boardService.getTotalPage(type);
            List<BoardDto> list = boardService.listArticle(type, pageNum, 10);
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

    @GetMapping("/modify/{articleNo}")
    public ResponseEntity<?> getModify( @PathVariable("articleNo")String articleNo) {
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

    @PutMapping("/modify")
    public ResponseEntity<?> modify( @RequestBody BoardDto boardDto) {
        try {
            boardService.modifyArticle(boardDto);
            return ResponseEntity.ok().body("Article modified successfully.");
        } catch (Exception e) {
            log.error("Error during article modification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article modification.");
        }
    }

    @DeleteMapping("/delete/{articleNo}")
    public ResponseEntity<?> delete(@PathVariable String articleNo) {
        try {
            boardService.deleteArticle(Integer.parseInt(articleNo));
            return ResponseEntity.ok().body("Article deleted successfully.");
        } catch (Exception e) {
            log.error("Error during article deletion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article deletion.");
        }
    }


}
