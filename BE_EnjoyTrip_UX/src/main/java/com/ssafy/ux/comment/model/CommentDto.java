package com.ssafy.ux.comment.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CommentDto {
    private String content;
    private int articleNo;
    private String userId;
    private int commentNo;
}
