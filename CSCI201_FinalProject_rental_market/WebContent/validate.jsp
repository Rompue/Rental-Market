<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="RM_Database.jsp" %>
<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	try {
		RMUser user = authenticateUser(username, password);
		request.getSession().setAttribute("user", user);
	} catch (RMAuthenticateUserException rmaue) {
		if(rmaue.getCode() == 1) {
%>
			<font color = "red"><strong>Incorrect Password</strong></font>
<%
		}
		if(rmaue.getCode() == 2) {
%>
			<font color = "red"><strong>User Does Not Exist</strong></font>
<%
		}
		if(rmaue.getCode() == 3) {
%>
			<font color = "red"><strong>Please Enter A Username</strong></font>
<%
		}
		if(rmaue.getCode() == 4) {
%>
			<font color = "red"><strong>Please Enter A Password</strong></font>
<%
		}
	}
%>
