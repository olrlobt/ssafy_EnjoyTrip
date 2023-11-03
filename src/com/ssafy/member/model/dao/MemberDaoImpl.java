package com.ssafy.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ssafy.member.model.MemberDto;
import com.ssafy.util.DBUtil;

public class MemberDaoImpl implements MemberDao {
	
	private static MemberDao memberDao = new MemberDaoImpl();
	private DBUtil dbUtil;
	
	private MemberDaoImpl() {
		dbUtil = DBUtil.getInstance();
	}
	
	public static MemberDao getMemberDao() {
		return memberDao;
	}

	@Override
	public int idCheck(String userId) throws SQLException {
		int cnt = 1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder loginMember = new StringBuilder();
			loginMember.append("select count(user_id) \n");
			loginMember.append("from member \n");
			loginMember.append("where user_id = ? \n");
			pstmt = conn.prepareStatement(loginMember.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			rs.next();
			cnt = rs.getInt(1);
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return cnt;
	}

	@Override
	public int joinMember(MemberDto memberDto) throws SQLException {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("insert into member (user_name, user_id, user_pass, user_salt, email_id, email_domain) \n");
			sql.append("values (?, ?, ?, ?, ?, ?)");
			// salt 추가로 인해 column 하나 추가
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, memberDto.getUserName());
			pstmt.setString(2, memberDto.getUserId());
			pstmt.setString(3, memberDto.getUserPwd());
			pstmt.setString(4, memberDto.getUserSalt());
			pstmt.setString(5, memberDto.getEmailId());
			pstmt.setString(6, memberDto.getEmailDomain());
			cnt = pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
		return cnt;
	}

	@Override
	public MemberDto loginMember(String userId, String userPwd) throws SQLException {
		MemberDto memberDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder loginMember = new StringBuilder();
			loginMember.append("select * \n");
			loginMember.append("from member \n");
			loginMember.append("where user_id = ? and user_pass = ? \n");
			pstmt = conn.prepareStatement(loginMember.toString());
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				memberDto = new MemberDto();
				memberDto.setUserId(rs.getString("user_id"));
				memberDto.setUserName(rs.getString("user_name"));
				memberDto.setEmailId(rs.getString("email_id"));
				memberDto.setEmailDomain(rs.getString("email_domain"));
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return memberDto;
	}

	@Override
	public void modifyMember(MemberDto memberDto, String userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("update member \n");
			sql.append("set user_id = ?, user_name = ?,  email_id = ?, email_domain = ?	\n");
			sql.append("where user_id = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, memberDto.getUserId());
			pstmt.setString(2, memberDto.getUserName());
			pstmt.setString(3, memberDto.getEmailId());
			pstmt.setString(4, memberDto.getEmailDomain());
			pstmt.setString(5, userId);
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public void modifyMemberPass(String userId, String password, String userSalt) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("update member \n");
			sql.append("set user_pass = ?, user_salt = ? \n");
			// 비밀번호 변경 시 salt도 변경하도록 반영
			sql.append("where user_id = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, password);
			pstmt.setString(2, userSalt);
			pstmt.setString(3, userId);
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public void withdraw(String userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("delete from member \n");
			sql.append("where user_id = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userId);
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	/**
	 * salt return하는 함가
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public String getSaltById(String userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String salt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select user_salt from member \n");
			sql.append("where user_id = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				salt = rs.getString("user_salt");
			}
		} finally {
			dbUtil.close(pstmt, conn);
		}
		return salt;
	}
}
