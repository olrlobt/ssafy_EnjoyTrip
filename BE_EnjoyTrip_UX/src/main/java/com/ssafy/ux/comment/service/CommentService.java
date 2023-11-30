package com.ssafy.ux.comment.service;

import com.ssafy.ux.comment.model.CommentDto;

import java.util.List;

public interface CommentService {
    public  CommentDto addComment(CommentDto commentDto)  throws Exception;
    public List<CommentDto> getCommentsForArticle(int articleNo)  throws Exception;
    void modifyComment(CommentDto commentDto) throws Exception;
    void deleteComment(int commentNo) throws Exception;
}
