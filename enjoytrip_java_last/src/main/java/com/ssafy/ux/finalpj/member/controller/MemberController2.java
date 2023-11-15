package com.ssafy.ux.finalpj.member.controller;

import com.ssafy.ux.finalpj.member.model.MemberDto;
import com.ssafy.ux.finalpj.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private MemberService memberService;

    public MemberController2(MemberService memberService) {
        super();
        this.memberService = memberService;
    }

    /* 회원가입 */
    @GetMapping("/join")
    public void join() {
        log.info("join");
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberDto memberDto) {
        try {
        	System.out.println("join_memberDto: " + memberDto);
            memberService.joinMember(memberDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입에 성공하였습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 가입 중 에러가 발생했습니다: " + e.getMessage());
        }
    }

    /* 로그인 */
    @GetMapping("/login")
    public void login() {

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(MemberDto getmemberDto,
                                   @RequestParam(name = "saveid", required = false) String saveid,
                                   HttpSession session,
                                   HttpServletResponse response) {
        try {
            MemberDto memberDto = memberService.loginMember(getmemberDto);
            if (memberDto != null) {
                session.setAttribute("userinfo", memberDto);

                Cookie cookie = new Cookie("ssafy_id", getmemberDto.getUserId());
                cookie.setPath("/");
                if ("ok".equals(saveid)) {
                    cookie.setMaxAge(60 * 60 * 24 * 365 * 40);
                } else {
                    cookie.setMaxAge(0);
                }
                response.addCookie(cookie);

                return ResponseEntity.ok().body(memberDto);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("아이디 또는 비밀번호 확인 후 다시 로그인하세요!");
            }
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("로그인 중 문제 발생!!!");
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/modify")
    public String modify(HttpSession session) {

        return "user/modify";
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modify(MemberDto memberDto, HttpSession session) throws Exception {
        memberService.modifyMember(memberDto, ((MemberDto) session.getAttribute("userinfo")).getUserId());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
