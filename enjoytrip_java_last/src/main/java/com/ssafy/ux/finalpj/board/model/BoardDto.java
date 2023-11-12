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
        if (registerTime != null && !registerTime.isEmpty()) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yy/MM/dd HH:mm");

            try {
                Date date = inputFormat.parse(registerTime);
                this.registerTime = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                // Handle the parsing exception, log it, or throw a specific exception
            }
        } else {
            // Handle the case where registerTime is empty
            // You can set a default value or take appropriate action
            this.registerTime = null; // or set a default time
        }
    }

}
