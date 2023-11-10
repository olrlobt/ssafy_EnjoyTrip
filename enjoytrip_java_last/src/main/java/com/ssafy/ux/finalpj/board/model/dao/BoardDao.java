package com.ssafy.ux.finalpj.board.model.dao;

import com.ssafy.ux.finalpj.board.model.BoardDto;


import java.sql.SQLException;
import java.util.List;

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
