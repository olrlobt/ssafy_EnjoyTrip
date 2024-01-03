package com.ssafy.ux.sharecomments.service;

import com.ssafy.ux.sharecomments.mapper.ShareCommentMapper;
import com.ssafy.ux.sharecomments.model.SharedTravelRouteComments;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ShareCommentServiceImpl implements ShareCommentService {

    private final ShareCommentMapper shareCommentMapper;

    public ShareCommentServiceImpl(ShareCommentMapper commentMapper) {
        this.shareCommentMapper = commentMapper;
    }
    @Override
    public SharedTravelRouteComments addComment(SharedTravelRouteComments sharedTravelRouteComments) {
        shareCommentMapper.addComment(sharedTravelRouteComments);
        System.out.println("commentDto = " + sharedTravelRouteComments.toString());
        return sharedTravelRouteComments;
    }

    @Override
    public List<SharedTravelRouteComments> getCommentsForArticle(int sharedTravelRouteNo) {
        shareCommentMapper.getCommentsForArticle(sharedTravelRouteNo).forEach(System.out::println);
        return shareCommentMapper.getCommentsForArticle(sharedTravelRouteNo);
    }

    @Override
    public void modifyComment(SharedTravelRouteComments sharedTravelRouteComments) throws Exception {
        shareCommentMapper.modifyComment(sharedTravelRouteComments);
    }

    @Override
    public void deleteComment(int commentNo) throws Exception {
        shareCommentMapper.deleteComment(commentNo);
    }

}
