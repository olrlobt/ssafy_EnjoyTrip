package com.ssafy.ux.chat.model.service;

import com.ssafy.ux.comment.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Comments, Integer> {
    @Query("SELECT c.content FROM Comments c WHERE c.articleNo = :articleNo")
    List<String> findCommentsContentByArticleNo(@Param("articleNo") int articleNo);
}
