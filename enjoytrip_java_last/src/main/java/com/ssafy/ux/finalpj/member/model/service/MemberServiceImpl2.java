package com.ssafy.ux.finalpj.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ux.finalpj.member.mapper.MemberMapper;
import com.ssafy.ux.finalpj.member.model.MemberDto;
import com.ssafy.ux.finalpj.util.SHA256Util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl2 implements MemberService {
	
	private MemberMapper memberMapper;
	
	@Autowired
	private MemberServiceImpl2(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	@Override
	public int idCheck(String userId) throws Exception {
		return memberMapper.idCheck(userId);
	}

	@Override
	public void joinMember(MemberDto memberDto) throws Exception {

		String salt = SHA256Util.generateSalt();
		memberDto.setUserSalt(salt);

		String password = memberDto.getUserPwd();
		password = SHA256Util.getEncrypt(password, salt);
		log.debug("salt:{} password:{}", salt, password);
		
		memberDto.setUserPwd(password);
		System.out.println(memberDto);
		memberMapper.joinMember(memberDto);
	}

	@Override
	public MemberDto loginMember(MemberDto memberDto) throws Exception {

		String salt = memberMapper.getSaltById(memberDto.getUserId());
		log.debug("salt:{}", salt);
		String pwd = SHA256Util.getEncrypt(memberDto.getUserPwd(), salt);
		log.debug("pwd:{}", pwd);
		memberDto.setUserPwd(pwd);
		return memberMapper.loginMember(memberDto);
	}

//	@Override
//	public void modifyMember(MemberDto memberDto, String userId) throws Exception {
//		memberMapper.modifyMember(memberDto, userId);
//	}
//
//	@Override
//	public void modifyMemberPass(String userId, String password) throws Exception {
//
//		String salt = SHA256Util.generateSalt();
//		password = SHA256Util.getEncrypt(password, salt);
//		memberMapper.modifyMemberPass(userId, password, salt);
//	}
//
//	@Override
//	public void withdraw(String userId) throws Exception {
//		memberMapper.withdraw(userId);
//	}

}
