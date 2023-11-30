package com.ssafy.ux.comment.controller;

import com.ssafy.ux.comment.model.CommentDto;
import com.ssafy.ux.comment.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto) {
        try {
            CommentDto savedComment = commentService.addComment(commentDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (Exception e) {
            log.error("Error adding comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding comment.");
        }
    }

    @GetMapping("/get/{articleNo}")
    public ResponseEntity<?> getCommentsForArticle(@PathVariable("articleNo") String articleNo) {
        try {
            List<CommentDto> comments = commentService.getCommentsForArticle(Integer.parseInt(articleNo));
            return ResponseEntity.ok().body(comments);
        } catch (Exception e) {
            log.error("Error getting comments for article", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting comments for article.");
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyComment(@RequestBody CommentDto commentDto) {
        try {
            log.info(commentDto.toString());
            commentService.modifyComment(commentDto);
            return ResponseEntity.ok().body("Comment modified successfully.");
        } catch (Exception e) {
            log.error("Error during comment modification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article modification.");
        }
    }

    @DeleteMapping("/delete/{commentNo}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentNo") String commentNo) {
        try {
            System.out.println(commentNo);
            commentService.deleteComment(Integer.parseInt(commentNo));
            return ResponseEntity.ok().body("Comment deleted successfully.");
        } catch (Exception e) {
            log.error("Error during comment deletion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article deletion.");
        }
    }

}
