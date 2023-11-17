package com.ssafy.ux.finalpj.member.controller;

import com.ssafy.ux.finalpj.member.model.MemberDto;
import com.ssafy.ux.finalpj.member.model.service.MemberService;
import com.ssafy.ux.finalpj.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private MemberService memberService;
    private JWTUtil jwtUtil;

    public MemberController2(MemberService memberService, JWTUtil jwtUtil) {
        super();
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
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


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto getmemberDto,
                                   @RequestParam(name = "saveid", required = false) String saveid,
                                   HttpSession session,
                                   HttpServletResponse response) {
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
//		System.out.println(getmemberDto);
		
        try {
            MemberDto loginUser = memberService.loginMember(getmemberDto);
            System.out.println(loginUser);
            if (loginUser != null) { //여기서부터 고칠 것
            	//jwt
            	String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
				log.debug("access token : {}", accessToken);
				log.debug("refresh token : {}", refreshToken);
				
				System.out.println("access TOKEN: " + accessToken);

//				발급받은 refresh token을 DB에 저장.
				memberService.saveRefreshToken(loginUser.getUserId(), refreshToken);
				
//				JSON으로 token 전달.
				resultMap.put("access-token", accessToken);
				//Cookie 로 변경
				resultMap.put("refresh-token", refreshToken);
				
				status = HttpStatus.CREATED;
				
				//쿠키 설정
//                Cookie cookie = new Cookie("ssafy_id", getmemberDto.getUserId());
//                cookie.setPath("/");
//                if ("ok".equals(saveid)) {
//                    cookie.setMaxAge(60 * 60 * 24 * 365 * 40);
//                } else {
//                    cookie.setMaxAge(0);
//                }
//                response.addCookie(cookie);

//                return ResponseEntity.ok().body(loginUser);
            } else {
            	resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
            	status = HttpStatus.UNAUTHORIZED;
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body("아이디 또는 비밀번호 확인 후 다시 로그인하세요!");
            	
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("로그인 중 문제 발생!!!");
        }
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

    }

	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") String userId,
			HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		System.out.println("getInfo 들어오나");
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			log.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userId);
				resultMap.put("userInfo", memberDto);
				status = HttpStatus.OK;
				System.out.println(memberDto);
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	

	/**
	 * 로그아웃
	 * @param userId : 회원 아이디
	 * @return
	 */
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable ("userId") String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			log.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		log.debug("token : {}, memberDto : {}", token, memberDto);
		if (jwtUtil.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(memberDto.getUserId()))) {
				String accessToken = jwtUtil.createAccessToken(memberDto.getUserId());
				log.debug("token : {}", accessToken);
				log.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			log.debug("리프레쉬토큰도 사용불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

//    @GetMapping("/modify")
//    public String modify(HttpSession session) {
//
//        return "user/modify";
//    }

    @PutMapping("/modify")
    public ResponseEntity<?> modify(@RequestBody MemberDto memberDto, HttpSession session) throws Exception {
    	System.out.println("modify 접근: " + memberDto);
        memberService.modifyMember(memberDto, memberDto.getUserId());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
