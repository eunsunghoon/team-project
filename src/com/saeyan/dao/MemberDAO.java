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
	public int userCheck(String userid,String pwd) { //�Է¹޴°� id�� pw�� ������
		int result = -1;
		String sql = "select pwd from member where userid = ?"; 
		// ��� ���̺��� userid= ? �� pwd �� �ҷ��Ͷ�
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection(); //getConnection ���� 
			pstmt = conn.prepareStatement(sql);//update�� ���� pstmt�� �غ� 
			pstmt.setString(1, userid); // ù��° Į���� userid �� ������ ���´�
			rs = pstmt.executeQuery(); // rs��� ���콺 �����ͷ� pstmt�� ���õ�  ��ü�� ��ȸ�Ѵ�.
			
			if(rs.next()) {
				if(rs.getString("pwd")!=null && rs.getString("pwd").equals(pwd)) {
					//pwd�� null�� �ƴϰ� rs���� Į���� userid�� �߿��� "pwd"�ΰŶ� pwd�ΰŶ� ���� �ϴٸ�
					result = 1;
					//���̵�,��й�ȣ�� �´ٸ� result��  1�� ����ض� 
					//>>member.js���� idCheck �Լ��� �����ϱ� ���ؼ�
				}else {
					result = 0; // ��й�ȣ�� Ʋ�ȴٸ�  0 
				}
			}else {
				result = -1; //���̵� Ʋ�ȴٸ� -1
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
         }// finally �� ��
		
		return result;
		//userCheck()�޼ҵ�� result �� �����ؾ���
	}
	//���̵�� ȸ�� ������ �������� �޼ҵ�
	//���̺��� ��ȸ�ؼ� �������� ������ returnŸ���� MemberVOŸ������ ����������Ѵ�.
	public MemberVO getMember(String userid) { // ȸ���� �����ϴ� ������ �� : userid
		
		MemberVO mVo = null; //return�� MemberVO �� �����ϱ� ���ؼ� ������ �����.
		String sql = "select pwd from member where userid = ?"; 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection(); //getConnection ���� 
			pstmt = conn.prepareStatement(sql);//update�� ���� pstmt�� �غ� 
			pstmt.setString(1, userid); // ù��° Į���� userid �� ������ ���´�
			rs = pstmt.executeQuery(); // rs��� ���콺 �����ͷ� pstmt�� ���õ�  ��ü�� ��ȸ�Ѵ�.
			
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
         }// finally �� ��
		return mVo;
	}
	
	
	
}
