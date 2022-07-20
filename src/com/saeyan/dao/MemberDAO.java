package com.saeyan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.saeyan.dto.MemberVO;

public class MemberDAO {

	private MemberDAO() {
		
	}
	private static MemberDAO instance = new MemberDAO();
	
	
	public static MemberDAO getInstance(){
		return instance;
	}
	
	public Connection getConnection() throws Exception{
		Connection conn = null;
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
		conn = ds.getConnection();
		return conn;
		
	}
	public int userCheck(String userid,String pwd) { //입력받는게 id와 pw기 때문에
		int result = -1;
		String sql = "select pwd from member where userid = ?"; 
		// 멤버 테이블에서 userid= ? 인 pwd 를 불러와라
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection(); //getConnection 연결 
			pstmt = conn.prepareStatement(sql);//update를 위한 pstmt를 준비 
			pstmt.setString(1, userid); // 첫번째 칼럼에 userid 를 셋팅해 놓는다
			rs = pstmt.executeQuery(); // rs라는 마우스 포인터로 pstmt에 셋팅된  전체를 조회한다.
			
			if(rs.next()) {
				if(rs.getString("pwd")!=null && rs.getString("pwd").equals(pwd)) {
					//pwd가 null이 아니고 rs에서 칼럼이 userid곳 중에서 "pwd"인거랑 pwd인거랑 동일 하다면
					result = 1;
					//아이디,비밀번호가 맞다면 result는  1을 출력해라 
					//>>member.js에서 idCheck 함수를 적용하기 위해서
				}else {
					result = 0; // 비밀번호가 틀렸다면  0 
				}
			}else {
				result = -1; //아이디가 틀렸다면 -1
			}
            
         }catch(Exception e) {
            e.printStackTrace();
         }finally {
            try {
               if(rs != null) rs.close();
               if(pstmt != null) pstmt.close();
               if(conn != null) conn.close();
            }catch(Exception e) {
               e.printStackTrace();
            }
         }// finally 의 끝
		
		return result;
		//userCheck()메소드는 result 를 리턴해야함
	}
	//아이디로 회원 정보를 가져오는 메소드
	//테이블을 조회해서 가져오기 떄문에 return타입은 MemberVO타입으로 리턴해줘야한다.
	public MemberVO getMember(String userid) { // 회원을 구분하는 고유한 값 : userid
		
		MemberVO mVo = null; //return을 MemberVO 로 리턴하기 위해서 변수를 만든다.
		String sql = "select pwd from member where userid = ?"; 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection(); //getConnection 연결 
			pstmt = conn.prepareStatement(sql);//update를 위한 pstmt를 준비 
			pstmt.setString(1, userid); // 첫번째 칼럼에 userid 를 셋팅해 놓는다
			rs = pstmt.executeQuery(); // rs라는 마우스 포인터로 pstmt에 셋팅된  전체를 조회한다.
			
			mVo = new MemberVO();
			if(rs.next()) {
				mVo.setName(rs.getString("name"));
				mVo.setUserid(rs.getString("userid"));
				mVo.setPwd(rs.getString("pwd"));
				mVo.setEmail(rs.getString("email"));
				mVo.setPhone(rs.getString("phone"));
				mVo.setAdmin(rs.getInt("admin"));
				}
         }catch(Exception e) {
            e.printStackTrace();
         }finally {
            try {
               if(rs != null) rs.close();
               if(pstmt != null) pstmt.close();
               if(conn != null) conn.close();
            }catch(Exception e) {
               e.printStackTrace();
            }
         }// finally 의 끝
		return mVo;
	}
	
	
	
}
