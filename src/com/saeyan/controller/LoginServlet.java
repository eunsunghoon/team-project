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
		
		if(result == 1 ) { //id,pw 같아서 로그인 성공 >> 로그인 메인페이지로 이동
			MemberVO mVo = mDao.getMember(userid);
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", mVo); //session 영역에 아이디 비밀번호 지정
			request.setAttribute("message", "회원가입에 성공 하였습니다."); // 로그인이 된 후 request영역에 값을 지정
			url="main.jsp";
		}else if(result == 0) {
			request.setAttribute("message", "비밀번호가 맞지 않습니다."); 
			//request영역 객체에 ${message}에 값을 지정
		}else if(result == -1) {
			request.setAttribute("message", "존재하지 않는 회원입니다.");
			
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
		//정보가 이동할땐 login.jsp >> loginservlet으로  doPost()메소드에서 result에 따라
		//페이지가 이동할떈  redirect / forward 방식으로 페이지 이동하게끔 만들어줘야한다.
	}

}
