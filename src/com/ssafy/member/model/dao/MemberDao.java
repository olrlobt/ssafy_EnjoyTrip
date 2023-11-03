package com.ssafy.member.model.dao;

import java.sql.SQLException;

import com.ssafy.member.model.MemberDto;

public interface MemberDao {

	int idCheck(String userId) throws SQLException;
	int joinMember(MemberDto memberDto) throws SQLException;
	MemberDto loginMember(String userId, String userPwd) throws SQLException;

	void modifyMember(MemberDto memberDto, String userId) throws SQLException;

	void modifyMemberPass(String userId, String password, String userSalt) throws SQLException;

	void withdraw(String userId) throws SQLException;

	// Salt용 DAO 추가
	String getSaltById(String userId) throws SQLException;
}
