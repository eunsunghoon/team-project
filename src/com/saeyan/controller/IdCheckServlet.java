package com.saeyan.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saeyan.dao.MemberDAO;

@WebServlet("/idCheck.do")
public class IdCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public IdCheckServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userid = request.getParameter("userid");
		MemberDAO mDao =MemberDAO.getInstance();
		
		int result = mDao.confirmID(userid);
		//result는 mDao객체에서 confirmID 메소드를 적용해서 값을 가져온다.
		
		request.setAttribute("userid", userid);
		request.setAttribute("result", result);
		//request 영역에 저장된 userid 의 값과, result 값을 지정한다.
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("member/idCheck.jsp");
		dispatcher.forward(request, response);			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
