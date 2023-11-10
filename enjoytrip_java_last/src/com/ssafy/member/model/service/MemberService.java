package com.ssafy.member.model.service;

import com.ssafy.member.model.MemberDto;

public interface MemberService {

	int idCheck(String userId) throws Exception;
	int joinMember(MemberDto memberDto) throws Exception;
	MemberDto loginMember(String userId, String userPwd) throws Exception;

	void modifyMember(MemberDto memberDto, String userId) throws Exception;

	void modifyMemberPass(String userId, String passowrd) throws Exception;

	void withdraw(String userId) throws Exception;

}
