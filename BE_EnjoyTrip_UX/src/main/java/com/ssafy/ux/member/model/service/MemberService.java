package com.ssafy.ux.member.model.service;


import com.ssafy.ux.member.model.MemberDto;

public interface MemberService {

	int idCheck(String userId) throws Exception;
	
	void joinMember(MemberDto memberDto) throws Exception;

	MemberDto loginMember(MemberDto memberDto) throws Exception;
	
	void modifyMember(MemberDto memberDto, String userId) throws Exception;

	void modifyMemberPass(String userId, String passowrd) throws Exception;

	void withdraw(String userId) throws Exception;

	void saveRefreshToken(String userId, String refreshToken) throws Exception;

	Object getRefreshToken(String userId) throws Exception;

	void deleRefreshToken(String userId) throws Exception;

	MemberDto userInfo(String userId) throws Exception;


}
