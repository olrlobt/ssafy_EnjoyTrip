package com.ssafy.ux.finalpj.share.model;

import lombok.Data;

import java.util.List;

@Data
public class TravelRouteDto {

    private int travelRouteNo;
    private String userId;
    private String subject;
    private String content;
    private int hit;
    private String registerTime;
    private List<MarkerDto> markers;
}
