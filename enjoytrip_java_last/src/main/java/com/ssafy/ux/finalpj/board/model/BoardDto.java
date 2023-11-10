package com.ssafy.ux.finalpj.board.model;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    public void setRegisterTime(String registerTime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yy/MM/dd HH:mm");

        try {
            Date date = inputFormat.parse(registerTime);
            this.registerTime = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
