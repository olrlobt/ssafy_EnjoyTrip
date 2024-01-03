package com.ssafy.ux.sharecomments.controller;

import com.ssafy.ux.sharecomments.model.SharedTravelRouteComments;
import com.ssafy.ux.sharecomments.service.ShareCommentService;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/sharedTravelRouteComments")
public class ShareCommentController {

    private final ShareCommentService shareCommentService;

    public ShareCommentController(ShareCommentService shareCommentService) {
        this.shareCommentService = shareCommentService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody SharedTravelRouteComments sharedTravelRouteComments) {
        try {
            SharedTravelRouteComments savedComment = shareCommentService.addComment(sharedTravelRouteComments);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (Exception e) {
            log.error("Error adding comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding comment.");
        }
    }

    @GetMapping("/get/{sharedTravelRouteNo}")
    public ResponseEntity<?> getCommentsForArticle(@PathVariable("sharedTravelRouteNo") String sharedTravelRouteNo) {

        System.out.println("sharedTravelRouteNo = " + sharedTravelRouteNo);
        try {
            List<SharedTravelRouteComments> comments = shareCommentService.getCommentsForArticle(Integer.parseInt(sharedTravelRouteNo));
            return ResponseEntity.ok().body(comments);
        } catch (Exception e) {
            log.error("Error getting comments for article", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting comments for article.");
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyComment(@RequestBody SharedTravelRouteComments sharedTravelRouteComments) {
        try {
            log.info(sharedTravelRouteComments.toString());
            shareCommentService.modifyComment(sharedTravelRouteComments);
            return ResponseEntity.ok().body("Comment modified successfully.");
        } catch (Exception e) {
            log.error("Error during comment modification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article modification.");
        }
    }

    @DeleteMapping("/delete/{commentNo}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentNo") int commentNo) {
        try {
            System.out.println(commentNo);
            shareCommentService.deleteComment(commentNo);
            return ResponseEntity.ok().body("Comment deleted successfully.");
        } catch (Exception e) {
            log.error("Error during comment deletion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during article deletion.");
        }
    }

}
