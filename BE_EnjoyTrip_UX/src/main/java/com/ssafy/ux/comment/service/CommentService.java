package com.ssafy.ux.comment.service;

import com.ssafy.ux.comment.model.Comments;

import java.util.List;

public interface CommentService {
    public Comments addComment(Comments comments)  throws Exception;
    public List<Comments> getCommentsForArticle(int articleNo)  throws Exception;
    void modifyComment(Comments comments) throws Exception;
    void deleteComment(int commentNo) throws Exception;
}
