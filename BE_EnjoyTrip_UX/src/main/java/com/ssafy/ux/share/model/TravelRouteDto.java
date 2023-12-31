package com.ssafy.ux.share.model;

import java.util.List;
import lombok.Data;

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
