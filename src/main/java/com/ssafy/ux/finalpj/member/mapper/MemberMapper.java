package com.ssafy.ux.finalpj.member.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.ux.finalpj.member.model.MemberDto;

@Mapper
public interface MemberMapper {

	String getSaltById(String userId) throws SQLException;
	
	int idCheck(String userId) throws SQLException;

	void joinMember(MemberDto memberDto) throws SQLException;

	void modifyMember(MemberDto memberDto, String userId) throws Exception;
	
	void modifyMemberPass(String userId, String password, String userSalt) throws Exception;
	
	void withdraw(String userId) throws Exception;

	MemberDto loginMember(MemberDto memberDto);
}
