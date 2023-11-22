package com.ssafy.ux.finalpj.board.model.service;

import com.ssafy.ux.finalpj.board.model.BoardDto;
import com.ssafy.ux.finalpj.board.model.BoardListDto;

import java.sql.SQLException;
import java.util.List;

public interface BoardService {

    void writeArticle(BoardDto boardDto) throws Exception;

//    List<BoardDto> listArticle(String type, int currentPage, int itemsPerPage) throws Exception;
    public BoardListDto listArticle(String type, int currentPage, int itemsPerPage) throws Exception;
    BoardDto getArticle(int articleNo) throws Exception;

    void updateHit(int articleNo) throws Exception;

    void modifyArticle(BoardDto boardDto) throws Exception;

    void deleteArticle(int articleNo) throws Exception;

    //    int getTotalPage(String type) throws SQLException;

    int getTotalPage(String type, int itemsPerPage) throws SQLException;

    BoardDto getArticleDetail(int articleNo) throws SQLException;
}
