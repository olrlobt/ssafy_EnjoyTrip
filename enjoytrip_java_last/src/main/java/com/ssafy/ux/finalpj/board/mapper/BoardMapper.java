package com.ssafy.ux.finalpj.board.mapper;

import com.ssafy.ux.finalpj.board.model.BoardDto;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface BoardMapper {

    void writeArticle(BoardDto boardDto) throws SQLException;

    void updateRef(int articleNo) throws SQLException;

    void updateStep(BoardDto boardDto) throws  SQLException;

    List<BoardDto> listArticle(String type, int currentPage, int itemsPerPage) throws SQLException;

    BoardDto getArticle(int articleNo) throws SQLException;

    void updateHit(int articleNo) throws SQLException;

    void modifyArticle(BoardDto boardDto) throws SQLException;

    void deleteArticle(int articleNO) throws SQLException;

    int getTotalPage(String type) throws SQLException;

    BoardDto getArticleDetail(int articleNo) throws SQLException;
}
