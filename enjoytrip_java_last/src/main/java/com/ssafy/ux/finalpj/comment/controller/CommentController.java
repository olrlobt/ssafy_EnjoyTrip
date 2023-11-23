package com.ssafy.ux.finalpj.comment.controller;

import com.ssafy.ux.finalpj.comment.model.CommentDto;
import com.ssafy.ux.finalpj.comment.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
//        System.out.println("들어오나 설마 여기야>"+commentDto.getArticleNo());
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
}
