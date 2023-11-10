package com.ssafy.ux.finalpj.member.model.service;


import com.ssafy.ux.finalpj.member.model.MemberDto;

public interface MemberService {

	int idCheck(String userId) throws Exception;
	
	void joinMember(MemberDto memberDto) throws Exception;

	MemberDto loginMember(MemberDto memberDto) throws Exception;
	
//	void modifyMember(MemberDto memberDto, String userId) throws Exception;
//
//	void modifyMemberPass(String userId, String passowrd) throws Exception;
//
//	void withdraw(String userId) throws Exception;


}
