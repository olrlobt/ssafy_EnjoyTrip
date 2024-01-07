package com.ssafy.ux.sharecomments.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class SharedTravelRouteComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNo;
    private String content;

    @Column(name = "sharedTravelRouteNo")
    private int sharedTravelRouteNo;
    private String userId;
}
