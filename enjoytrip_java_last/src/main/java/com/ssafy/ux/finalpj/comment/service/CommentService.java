package com.ssafy.ux.finalpj.comment.service;

import com.ssafy.ux.finalpj.comment.model.CommentDto;

import java.util.List;

public interface CommentService {
    public  CommentDto addComment(CommentDto commentDto)  throws Exception;
    public List<CommentDto> getCommentsForArticle(int articleNo)  throws Exception;
}
