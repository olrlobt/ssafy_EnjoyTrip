package com.ssafy.ux.comment.service;

import com.ssafy.ux.comment.mapper.CommentMapper;
import com.ssafy.ux.comment.model.CommentDto;
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
        commentMapper.addComment(commentDto);
        System.out.println("commentDto = " + commentDto.toString());
        return commentDto;
    }

    @Override
    public List<CommentDto> getCommentsForArticle(int articleNo) {
        commentMapper.getCommentsForArticle(articleNo).forEach(System.out::println);
        return commentMapper.getCommentsForArticle(articleNo);
    }

    @Override
    public void modifyComment(CommentDto commentDto) throws Exception {
        commentMapper.modifyComment(commentDto);
    }

    @Override
    public void deleteComment(int commentNo) throws Exception {
        commentMapper.deleteComment(commentNo);
    }

}