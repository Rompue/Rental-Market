<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%
	RMDatabase.initializeDatabase();
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	try {
		RMUser user = RMDatabase.authenticateUser(username, password);
		request.getSession().setAttribute("user", user);
		//request.getSession().setAttribute("name", user.getEmail());
	} catch (RMAuthenticateUserException rmaue) {
		if(rmaue.getCode() == 1) {
%>
			<font color = "red"><strong><%= rmaue.getMessage() %></strong></font>
<%
		}
		if(rmaue.getCode() == 2) {
%>
			<font color = "red"><strong><%= rmaue.getMessage() %></strong></font>
<%
		}
		if(rmaue.getCode() == 3) {
%>
			<font color = "red"><strong><%= rmaue.getMessage() %></strong></font>
<%
		}
		if(rmaue.getCode() == 4) {
%>
			<font color = "red"><strong><%= rmaue.getMessage() %></strong></font>
<%
		}
	}
%>
