package com.ssafy.ux.finalpj.board.model;

import lombok.Data;

import java.util.List;

@Data
public class BoardListDto {
    private List<BoardDto> articles;
    private int currentPage;
    private int totalPageCount;
}
