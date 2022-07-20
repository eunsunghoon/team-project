package com.saeyan.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.saeyan.dao.MemberDAO;
import com.saeyan.dto.MemberVO;


@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
     
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "member/login.jsp";
		
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUser")!=null) {
			url = "main.jsp";
		}
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "member/login.jsp";
		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pwd");
		
		MemberDAO mDao = MemberDAO.getInstance();
		int result = mDao.userCheck(userid, pwd);
		
		if(result == 1 ) { //id,pw ���Ƽ� �α��� ���� >> �α��� ������������ �̵�
			MemberVO mVo = mDao.getMember(userid);
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", mVo); //session ������ ���̵� ��й�ȣ ����
			request.setAttribute("message", "ȸ�����Կ� ���� �Ͽ����ϴ�."); // �α����� �� �� request������ ���� ����
			url="main.jsp";
		}else if(result == 0) {
			request.setAttribute("message", "��й�ȣ�� ���� �ʽ��ϴ�."); 
			//request���� ��ü�� ${message}�� ���� ����
		}else if(result == -1) {
			request.setAttribute("message", "�������� �ʴ� ȸ���Դϴ�.");
			
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
		//������ �̵��Ҷ� login.jsp >> loginservlet����  doPost()�޼ҵ忡�� result�� ����
		//�������� �̵��ҋ�  redirect / forward ������� ������ �̵��ϰԲ� ���������Ѵ�.
	}

}
