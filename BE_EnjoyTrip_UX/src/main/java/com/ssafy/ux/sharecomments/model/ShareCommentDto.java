package com.ssafy.ux.sharecomments.model;

import lombok.Data;

@Data
public class ShareCommentDto {
    private String content;
    private int  sharedTravelRouteNo;
    private String userId;
    private int commentNo;
}
