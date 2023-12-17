package com.ssafy.ux.comment.service;

import com.ssafy.ux.comment.mapper.CommentMapper;
import com.ssafy.ux.comment.model.Comments;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }
    @Override
    public Comments addComment(Comments comments) {
        commentMapper.addComment(comments);
        System.out.println("commentDto = " + comments.toString());
        return comments;
    }

    @Override
    public List<Comments> getCommentsForArticle(int articleNo) {
        commentMapper.getCommentsForArticle(articleNo).forEach(System.out::println);
        return commentMapper.getCommentsForArticle(articleNo);
    }

    @Override
    public void modifyComment(Comments comments) throws Exception {
        commentMapper.modifyComment(comments);
    }

    @Override
    public void deleteComment(int commentNo) throws Exception {
        commentMapper.deleteComment(commentNo);
    }

}
