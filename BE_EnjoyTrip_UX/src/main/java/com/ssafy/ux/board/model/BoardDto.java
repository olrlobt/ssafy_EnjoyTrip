package com.ssafy.ux.board.model;

import lombok.Data;

@Data
public class BoardDto {

    private int articleNo;
    private String userId;
    private String userName;
    private String subject;
    private String content;
    private int hit;
    private String registerTime;
    private String type;
    private int ref;
    private int step;
    private int depth;

}
