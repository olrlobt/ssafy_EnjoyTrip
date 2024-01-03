package com.ssafy.ux.sharecomments.service;

import com.ssafy.ux.sharecomments.model.SharedTravelRouteComments;
import java.util.List;

public interface ShareCommentService {
    public SharedTravelRouteComments addComment(SharedTravelRouteComments sharedTravelRouteComments)  throws Exception;
    public List<SharedTravelRouteComments> getCommentsForArticle(int sharedTravelRouteNo)  throws Exception;
    void modifyComment(SharedTravelRouteComments sharedTravelRouteComments) throws Exception;
    void deleteComment(int sharedTravelRouteNo) throws Exception;
}
