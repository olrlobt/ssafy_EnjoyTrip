package com.ssafy.ux.finalpj.share.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Data;

@Data
public class TravelRouteDto {

    private int travelRouteNo;
    private String userId;
    private String subject;
    private String content;
    private int hit;
    private LocalDateTime registerTime;
    private List<MarkerDto> markers;

    public String getRegisterTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return registerTime.format(formatter);
    }
}
