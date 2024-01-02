package com.ssafy.ux.sharecomments.service;

import com.ssafy.ux.sharecomments.mapper.ShareCommentMapper;
import com.ssafy.ux.sharecomments.model.ShareCommentDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ShareCommentServiceImpl implements ShareCommentService {

    private final ShareCommentMapper shareCommentMapper;

    public ShareCommentServiceImpl(ShareCommentMapper commentMapper) {
        this.shareCommentMapper = commentMapper;
    }
    @Override
    public ShareCommentDto addComment(ShareCommentDto shareCommentDto) {
        shareCommentMapper.addComment(shareCommentDto);
        System.out.println("commentDto = " + shareCommentDto.toString());
        return shareCommentDto;
    }

    @Override
    public List<ShareCommentDto> getCommentsForArticle(int sharedTravelRouteNo) {
        shareCommentMapper.getCommentsForArticle(sharedTravelRouteNo).forEach(System.out::println);
        return shareCommentMapper.getCommentsForArticle(sharedTravelRouteNo);
    }

    @Override
    public void modifyComment(ShareCommentDto shareCommentDto) throws Exception {
        shareCommentMapper.modifyComment(shareCommentDto);
    }

    @Override
    public void deleteComment(int commentNo) throws Exception {
        shareCommentMapper.deleteComment(commentNo);
    }

}
