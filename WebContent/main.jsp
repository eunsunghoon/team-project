<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" scr="../member.js"></script>
</head>
<body>
	<h1>회원 전용 페이지</h1>
	<form action="logout.do">
		<table>
			<tr>
				<td>안녕하세요. ${loginUser.name}(${loginUser.userid})님</td>
				<!--loginServlet에서  session.setAttribute("loginUser", mVo); 
				이걸로 지정해주고나서 값을 가져온다.
				 -->
			</tr>
			<td colspan="2" align="center">
			<input type="submit" value="로그아웃">&nbsp;&nbsp;
			<input type="button" value="회원정보 변경"
				onclick="location.href='memberUpdate.do?userid=${loginUser.userid}'">
			</td>
			</tr>
		</table>
	</form>
</body>
</html>