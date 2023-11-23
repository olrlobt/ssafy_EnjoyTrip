package com.ssafy.ux.finalpj.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ux.finalpj.member.mapper.MemberMapper;
import com.ssafy.ux.finalpj.member.model.MemberDto;
import com.ssafy.ux.finalpj.util.SHA256Util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
	
	private MemberMapper memberMapper;
	
	@Autowired
	private MemberServiceImpl(MemberMapper memberMapper) {
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
		memberDto.setUserPwd(SHA256Util.getEncrypt(memberDto.getUserPwd(), salt));

		return memberMapper.loginMember(memberDto);
	}

	@Override
	public void modifyMember(MemberDto memberDto, String userId) throws Exception {
		memberMapper.modifyMember(memberDto, userId);
	}

	@Override
	public void modifyMemberPass(String userId, String password) throws Exception {

		String salt = SHA256Util.generateSalt();
		password = SHA256Util.getEncrypt(password, salt);
		memberMapper.modifyMemberPass(userId, password, salt);
	}

	@Override
	public void withdraw(String userId) throws Exception {
		memberMapper.withdraw(userId);
	}
	
	@Override
	public MemberDto userInfo(String userId) throws Exception {
		return memberMapper.userInfo(userId);
	}
	
	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		memberMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return memberMapper.getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);
	}
	

}
