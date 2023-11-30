package com.ssafy.ux.board.mapper;

import com.ssafy.ux.board.model.BoardDto;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface BoardMapper {

    void writeArticle(BoardDto boardDto) throws SQLException;

    void updateRef(int articleNo) throws SQLException;

    void updateStep(BoardDto boardDto) throws  SQLException;

    List<BoardDto> listArticle(Map<String, Object> parameters);

    BoardDto getArticle(int articleNo) throws SQLException;

    void updateHit(int articleNo) throws SQLException;

    void modifyArticle(BoardDto boardDto) throws SQLException;

    void deleteArticle(int articleNO) throws SQLException;

    int getTotalPage(Map<String, Object> parameters);

    BoardDto getArticleDetail(int articleNo) throws SQLException;
}
