package com.saeyan.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saeyan.dao.MemberDAO;
import com.saeyan.dto.MemberVO;


@WebServlet("/memberUpdate.do")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public MemberUpdateServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		MemberDAO mDao = MemberDAO.getInstance();
		MemberVO mVo = mDao.getMember(userid);
		request.setAttribute("mVo", mVo);
		//폼에서 입력받은 정보를 mVo라는 요청 객체에 저장합니다.
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("member/memberUpdate.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
	      String name = request.getParameter("name");
	      String userid = request.getParameter("userid");
	      String pwd = request.getParameter("pwd");
	      String email = request.getParameter("email");
	      String phone = request.getParameter("phone");
	      String admin = request.getParameter("admin");
	      
	      MemberVO mVo = new MemberVO();
	      
	      mVo.setName(name);
	      mVo.setUserid(userid);
	      mVo.setPwd(pwd);
	      mVo.setEmail(email);
	      mVo.setPhone(phone);
	      mVo.setAdmin(Integer.parseInt(admin));
	      MemberDAO mDao = MemberDAO.getInstance();
	      mDao.updateMember(mVo);
	      response.sendRedirect("login.do");
	}

}
