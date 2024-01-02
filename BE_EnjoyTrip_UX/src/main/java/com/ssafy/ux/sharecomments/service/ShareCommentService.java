package com.ssafy.ux.sharecomments.service;

import com.ssafy.ux.sharecomments.model.ShareCommentDto;
import java.util.List;

public interface ShareCommentService {
    public  ShareCommentDto addComment(ShareCommentDto shareCommentDto)  throws Exception;
    public List<ShareCommentDto> getCommentsForArticle(int sharedTravelRouteNo)  throws Exception;
    void modifyComment(ShareCommentDto shareCommentDto) throws Exception;
    void deleteComment(int sharedTravelRouteNo) throws Exception;
}
