package com.ssafy.ux.finalpj.comment.service;

import com.ssafy.ux.finalpj.comment.mapper.CommentMapper;
import com.ssafy.ux.finalpj.comment.model.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }
    @Override
    public CommentDto addComment(CommentDto commentDto) {
        System.out.println(commentDto.getArticleNo());
        commentMapper.addComment(commentDto);
        return commentDto;
    }

    @Override
    public List<CommentDto> getCommentsForArticle(int articleNo) {
        return commentMapper.getCommentsForArticle(articleNo);
    }

}
