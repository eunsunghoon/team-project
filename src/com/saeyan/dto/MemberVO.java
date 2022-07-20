package com.saeyan.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

//데이터베이스의 테이블에 근거해서 만든다.
//테이블의 데이터를 보내거나 받아올때 VO 를 통해 get,set 한
public class MemberVO {
	private String name;
	private String userid;
	private String pwd;
	private String email;
	private String phone;
	private int admin;
}
