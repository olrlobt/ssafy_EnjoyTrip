package com.ssafy.ux.finalpj.board.model.dao;

import com.ssafy.ux.finalpj.board.model.BoardDto;
import com.ssafy.ux.finalpj.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDaoImpl implements BoardDao {
	
	private static BoardDao boardDao;
	private DBUtil dbUtil;
	
	private BoardDaoImpl() {
		dbUtil = DBUtil.getInstance();
	}

	public static BoardDao getBoardDao() {
		if(boardDao == null)
			boardDao = new BoardDaoImpl();
		return boardDao;
	}

	@Override
	public void writeArticle(BoardDto boardDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("insert into board (user_id, subject, content, type) \n");
			sql.append("values (?, ?, ?, ?)");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, boardDto.getUserId());
			pstmt.setString(2, boardDto.getSubject());
			pstmt.setString(3, boardDto.getContent());
			pstmt.setString(4, boardDto.getType());
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public List<BoardDto> listArticle(String type, int currentPage, int itemsPerPage) throws SQLException {
		List<BoardDto> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT article_no, user_id, subject, content, hit, register_time \n");
			sql.append("FROM board \n");
			sql.append("WHERE type = ? \n"); // PreparedStatement를 사용하여 SQL Injection을 방지합니다.
			sql.append("ORDER BY article_no DESC \n");
			int offset = (currentPage - 1) * itemsPerPage;
			sql.append("LIMIT ? OFFSET ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, type);
			pstmt.setInt(2, itemsPerPage);
			pstmt.setInt(3, offset);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDto boardDto = new BoardDto();
				boardDto.setArticleNo(rs.getInt("article_no"));
				boardDto.setUserId(rs.getString("user_id"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setContent(rs.getString("content"));
				boardDto.setHit(rs.getInt("hit"));
				boardDto.setRegisterTime(rs.getString("register_time"));
				list.add(boardDto);
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return list;
	}


	@Override
	public BoardDto getArticle(int articleNo) throws SQLException {
		BoardDto boardDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select article_no, user_id, subject, content, hit, register_time \n");
			sql.append("from board \n");
			sql.append("where article_no = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, articleNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				boardDto = new BoardDto();
				boardDto.setArticleNo(rs.getInt("article_no"));
				boardDto.setUserId(rs.getString("user_id"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setContent(rs.getString("content"));
				boardDto.setHit(rs.getInt("hit"));
				boardDto.setRegisterTime(rs.getString("register_time"));
			}
			
			
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return boardDto;
	}

	@Override
	public void updateHit(int articleNo) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("update board \n");
			sql.append("set hit = hit + 1 \n");
			sql.append("where article_no = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, articleNo);
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}	
	}

	@Override
	public void modifyArticle(BoardDto boardDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbUtil.getConnection();

			String sql = "UPDATE Board SET subject = ?, content = ? WHERE article_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardDto.getSubject());
			pstmt.setString(2, boardDto.getContent());
			pstmt.setInt(3, boardDto.getArticleNo());
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("게시글 수정 실패: 해당 번호의 게시글이 존재하지 않습니다.");
			}
		} finally {
			// 자원 해제
			dbUtil.close(pstmt, conn);
		}
	}


	@Override
	public void deleteArticle(int articleNo) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			String sql = "DELETE FROM Board WHERE article_no = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, articleNo);
			int deletedRows = pstmt.executeUpdate();

			if(deletedRows == 0) {
				throw new SQLException("삭제 실패: 해당 번호의 글이 존재하지 않습니다.");
			}
		} finally {
			// 자원 해제
			if(pstmt != null) try { pstmt.close(); } catch(SQLException ignore) {}
			if(conn != null) try { conn.close(); } catch(SQLException ignore) {}
		}
	}


	@Override
	public int getTotalPage(String type, int itemsPerPage) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalPage = 0;

		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(*) FROM board where type = ?"); // 총 게시글 수를 조회합니다.
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1,type);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				int totalCount = rs.getInt(1); // 총 게시글 수를 가져옵니다.
				totalPage = (int) Math.ceil((double) totalCount / itemsPerPage); // 전체 페이지 수를 계산합니다.
			}
		} finally {
			dbUtil.close(rs, pstmt, conn); // ResultSet도 닫아주어야 합니다.
		}

		return totalPage;
	}

	@Override
	public BoardDto getArticleDetail(int articleNo) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDto boardDto = null;

		try {
			conn = dbUtil.getConnection();

			String sql = "SELECT * FROM Board WHERE article_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, articleNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				boardDto = new BoardDto();
				boardDto.setArticleNo(rs.getInt("article_no"));
				boardDto.setUserId(rs.getString("user_id"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setContent(rs.getString("content"));
				boardDto.setHit(rs.getInt("hit"));
				boardDto.setRegisterTime(rs.getString("register_time"));
				boardDto.setType(rs.getString("type"));
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}

		return boardDto;
	}



}
