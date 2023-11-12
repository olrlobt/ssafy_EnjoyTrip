package com.ssafy.ux.finalpj.board.model;

import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import lombok.Data;

@Data
public class BoardDto {

    private int articleNo;
    private String userId;
    private String subject;
    private String content;
    private int hit;
    private String registerTime;
    private String type;

}
