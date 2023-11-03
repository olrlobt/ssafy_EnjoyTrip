package com.ssafy.ux.finalpj.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {

	private String userId;
	private String userName;
	private String userPwd;
	private String userSalt;
	// UserSalt 추가
	private String emailId;
	private String emailDomain;
	private String joinDate;

	
}
