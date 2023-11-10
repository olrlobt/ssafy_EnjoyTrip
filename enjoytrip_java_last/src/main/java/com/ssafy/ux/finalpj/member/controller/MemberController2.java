package com.ssafy.ux.finalpj.member.controller;

import com.ssafy.ux.finalpj.member.model.MemberDto;
import com.ssafy.ux.finalpj.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/user")
public class MemberController2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MemberService memberService;

	public MemberController2(MemberService memberService) {
		super();
		this.memberService = memberService;
	}

	/* 회원가입 */
	@GetMapping("/join")
	public String join() {
		return "user/join";
	}

	@PostMapping("/join")
	public String join(MemberDto memberDto, Model model) {
		try {
//			log.info("request:{}", request);
			memberService.joinMember(memberDto);

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "회원 가입 중 에러가 발생했는다.");
		}

		return "redirect:/user/login";
	}

	/* 로그인 */
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}

	@PostMapping("/login")
	public String login(MemberDto getmemberDto, @RequestParam(name = "saveid", required = false) String saveid,
			Model model, HttpSession session, HttpServletResponse response) {

		try {
			MemberDto memberDto = memberService.loginMember(getmemberDto);
			if (memberDto != null) {
				session.setAttribute("userinfo", memberDto);

				Cookie cookie = new Cookie("ssafy_id", getmemberDto.getUserId());
				cookie.setPath("/board");
				if ("ok".equals(saveid)) {
					cookie.setMaxAge(60 * 60 * 24 * 365 * 40);
				} else {
					cookie.setMaxAge(0);
				}
				response.addCookie(cookie);
				return "redirect:/";
			} else {
				model.addAttribute("msg", "아이디 또는 비밀번호 확인 후 다시 로그인하세요!");
				return "user/login";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "로그인 중 문제 발생!!!");
		}

		return "redirect:/";
	}
	
	@GetMapping
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
//	@GetMapping
//	public String modify(HttpSession session) {
//	
//		return "user/modify";
//	}
//	
//	@PostMapping
//	public String modify(MemberDto memberDto) {
//		
//		return "user/modify";
//	}
	
	

}
