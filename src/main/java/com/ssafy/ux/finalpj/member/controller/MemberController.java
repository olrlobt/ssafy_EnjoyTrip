//package com.ssafy.ux.finalpj.member.controller;
//
//import com.ssafy.ux.finalpj.member.model.MemberDto;
//import com.ssafy.ux.finalpj.member.model.service.MemberService;
//
//import lombok.extern.slf4j.Slf4j;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.io.IOException;
//
//@Slf4j
//@Controller
//@RequestMapping("/user")
//public class MemberController extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	
//	private MemberService memberService;
//	
//	public MemberController(MemberService memberService) {
//		super();
//		this.memberService = memberService;
//	}
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.getSession().removeAttribute("msg");
//		String action = request.getParameter("action");
//
//		String path = "";
//		if ("mvjoin".equals(action)) {
//			path = "/user/join.jsp";
//			redirect(request, response, path);
//		} else if ("join".equals(action)) {
//			path = join(request, response);
//			redirect(request, response, path);
//		} else if ("mvlogin".equals(action)) {
//			path = "/user/login.jsp";
//			redirect(request, response, path);
//		} else if ("login".equals(action)) {
//			path = login(request, response);
//			redirect(request, response, path);
//		} else if ("logout".equals(action)) {
//			path = logout(request, response);
//			redirect(request, response, path);
//		} else if ("modify".equals(action)) {
//			path = modify(request, response);
//			redirect(request, response, path);
//		} else if ("modifyPass".equals(action)) {
//			path = modifyPass(request, response);
//			redirect(request, response, path);
//		} else if("withdraw".equals(action)) {
//			path = withdraw(request, response);
//			if(path.equals("/user/login.jsp")) redirect(request, response, path);
//		}else if("idcheck".equals(action)) {
//			String checkid = request.getParameter("checkid");
//			System.out.println("checkid >>>>> " + checkid);
//			int cnt = 1;
//			try {
//				cnt = memberService.idCheck(checkid);
//			} catch (Exception e) {
//				e.printStackTrace();
//				cnt = 1;
//			}
////			CSV
//			response.setContentType("text/plain;charset=utf-8");
//			response.getWriter().print(checkid + "," + cnt);
//		}else {
//			redirect(request, response, path);
//		}
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		doGet(request, response);
//	}
//	
//	private void forward(HttpServletRequest request, HttpServletResponse response, String path)
//			throws ServletException, IOException {
//		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
//		dispatcher.forward(request, response);
//	}
//
//	private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
//		response.sendRedirect(request.getContextPath() + path);
//	}
//	
//	private String join(HttpServletRequest request, HttpServletResponse response) {
//		MemberDto memberDto = new MemberDto();
//		memberDto.setUserName(request.getParameter("username"));
//		memberDto.setUserId(request.getParameter("userid"));
//		memberDto.setUserPwd(request.getParameter("userpwd"));
//		if(!request.getParameter("emailid").isEmpty()) {
//			memberDto.setEmailId(request.getParameter("emailid"));
//			memberDto.setEmailDomain(request.getParameter("emaildomain"));
//		}
//		try {
//			memberService.joinMember(memberDto);
//			request.getSession().setAttribute("info", "회원가입을 축하합니다.");
//			return "/user/login.jsp";
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.getSession().setAttribute("msg", "회원가입 중 에러가 발생 다시 진행해주세요.");
//			return "";
//		}
//	}
//	
//	private String login(HttpServletRequest request, HttpServletResponse response) {
//		String userId = request.getParameter("userid");
//		String userPwd = request.getParameter("userpwd");
//		try {
//			MemberDto memberDto = memberService.loginMember(userId, userPwd);
//			if(memberDto != null) { // 로그인 성공
////				session 설정
//				HttpSession session = request.getSession();
//				session.setAttribute("userinfo", memberDto);
////				cookie 설정
//				String idsave = request.getParameter("saveid");
//				if("ok".equals(idsave)) { //아이디 저장을 체크 했다면.
//					Cookie cookie = new Cookie("ssafy_id", userId);
//					cookie.setPath(request.getContextPath());
//					cookie.setMaxAge(60 * 60 * 24 * 365 * 40); //40년간 저장.
//					response.addCookie(cookie);
//				} else { //아이디 저장을 해제 했다면.
//					Cookie cookies[] = request.getCookies();
//					if(cookies != null) {
//						for(Cookie cookie : cookies) {
//							if("ssafy_id".equals(cookie.getName())) {
//								cookie.setMaxAge(0);
//								response.addCookie(cookie);
//								break;
//							}
//						}
//					}
//				}
//				return "/index.jsp";
//			} else {
//				request.getSession().setAttribute("msg", "아이디 또는 비밀번호 확인 후 다시 로그인하세요.");
//				return "/user/login.jsp";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "";
//		}
//	}
//	
//	private String logout(HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		session.invalidate();
//		return "/index.jsp";
//	}
//
//	private String modify(HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//		if(memberDto != null) {
//			String userId = memberDto.getUserId();
//			memberDto.setUserName(request.getParameter("username"));
//			memberDto.setUserId(request.getParameter("userid"));
//			if(!request.getParameter("emailid").isEmpty()) {
//				memberDto.setEmailId(request.getParameter("emailid"));
//				memberDto.setEmailDomain(request.getParameter("emaildomain"));
//			}
//			try {
//				memberService.modifyMember(memberDto, userId);
//				session.setAttribute("userinfo", memberDto);
//				session.setAttribute("info", "회원정보 수정 성공");
//				return "";
//			} catch (Exception e) {
//				e.printStackTrace();
//				session.setAttribute("msg", "회원정보 수정 중 문제가 발생했습니다 재시도 해주세요.");
//				return "";
//			}
//
//		} else
//			return "/user/login.jsp";
//	}
//	private String modifyPass(HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//		if(memberDto != null) {
//			String password = request.getParameter("userpwd");
//			try {
//				memberService.modifyMemberPass(memberDto.getUserId(), password);
//				String msg = "다시 로그인해주세요.";
//				return homeWithMsg(request, response, msg);
//			} catch (Exception e) {
//				e.printStackTrace();
//				session.setAttribute("msg", "비밀번호 수정 중 문제가 발생했습니다 재시도 해주세요.");
//				return "";
//			}
//
//		} else
//			return "/user/login.jsp";
//	}
//
//	private String withdraw(HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//		if(memberDto != null) {
//			try {
//				memberService.withdraw(memberDto.getUserId());
//				return logout(request, response);
//			} catch (Exception e) {
//				e.printStackTrace();
//				session.setAttribute("msg", "회원 탈퇴 중 문제가 발생했습니다 재시도 해주세요.");
//				return "";
//			}
//
//		} else
//			return "/user/login.jsp";
//	}
//
//	private String homeWithMsg(HttpServletRequest request, HttpServletResponse response, String msg) {
//		HttpSession session = request.getSession();
//		session.removeAttribute("userinfo");
//		session.setAttribute("info", msg);
//		return "";
//	}
//
//}
