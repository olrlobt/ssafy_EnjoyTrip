package com.ssafy.board.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.board.model.BoardDto;

public interface BoardDao {

	void writeArticle(BoardDto boardDto) throws SQLException;
	List<BoardDto> listArticle(String type, int currentPage, int itemsPerPage) throws SQLException;
	BoardDto getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	
	void modifyArticle(BoardDto boardDto) throws SQLException;
	void deleteArticle(int articleNO) throws SQLException;

	int getTotalPage(String type, int itemsPerPage) throws SQLException;

	BoardDto getArticleDetail(int articleNo) throws SQLException ;
}
