package com.ssafy.ux.comment.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNo;
    private String content;

    @Column(name = "articleNo")
    private int articleNo;
    private String userId;
}
