//package com.ssafy.ux.finalpj.member.model.service;
//
//import com.ssafy.ux.finalpj.member.model.MemberDto;
//import com.ssafy.ux.finalpj.member.model.dao.MemberDao;
//import com.ssafy.ux.finalpj.member.model.dao.MemberDaoImpl;
//import com.ssafy.ux.finalpj.util.SHA256Util;
//
//public class MemberServiceImpl implements MemberService {
//	
//	private static MemberService memberService = new MemberServiceImpl();
//	private MemberDao memberDao;
//	
//	private MemberServiceImpl() {
//		memberDao = MemberDaoImpl.getMemberDao();
//	}
//	
//	public static MemberService getMemberService() {
//		return memberService;
//	}
//
//	@Override
//	public int idCheck(String userId) throws Exception {
//		return memberDao.idCheck(userId);
//	}
//
//	@Override
//	public void joinMember(MemberDto memberDto) throws Exception {
//
//		String salt = SHA256Util.generateSalt();
//		memberDto.setUserSalt(salt);
//
//		String password = memberDto.getUserPwd();
//		password = SHA256Util.getEncrypt(password, salt);
//
//		memberDto.setUserPwd(password);
//		return memberDao.joinMember(memberDto);
//	}
//
//	@Override
//	public MemberDto loginMember(String userId, String userPwd) throws Exception {
//
//		String salt = memberDao.getSaltById(userId);
//
//		userPwd = SHA256Util.getEncrypt(userPwd, salt);
//		return memberDao.loginMember(userId, userPwd);
//	}
//
//	@Override
//	public void modifyMember(MemberDto memberDto, String userId) throws Exception {
//		memberDao.modifyMember(memberDto, userId);
//	}
//
//	@Override
//	public void modifyMemberPass(String userId, String password) throws Exception {
//
//		String salt = SHA256Util.generateSalt();
//
//		password = SHA256Util.getEncrypt(password, salt);
//
//		memberDao.modifyMemberPass(userId, password, salt);
//	}
//
//	@Override
//	public void withdraw(String userId) throws Exception {
//		memberDao.withdraw(userId);
//	}
//}
