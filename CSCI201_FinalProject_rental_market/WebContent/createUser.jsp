<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%
	RMDatabase.initializeDatabase();
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String firstname = request.getParameter("firstname");
	String lastname = request.getParameter("lastname");
	try {
		RMDatabase.createUser(firstname, lastname, username, password);
	} catch (RMCreateUserException rmcue) {
		if(rmcue.getCode() == 1) {
%>
			<font color = "red"><strong><%= rmcue.getMessage() %></strong></font>
<%
		}
		if(rmcue.getCode() == 2) {
%>
			<font color = "red"><strong><%= rmcue.getMessage() %></strong></font>
<%
		}
		if(rmcue.getCode() == 3) {
%>
			<font color = "red"><strong><%= rmcue.getMessage() %></strong></font>
<%
		}
		if(rmcue.getCode() == 4) {
%>
			<font color = "red"><strong><%= rmcue.getMessage() %></strong></font>
<%
		}
		if(rmcue.getCode() == 5) {
%>
			<font color = "red"><strong><%= rmcue.getMessage() %></strong></font>
<%
		}
		if(rmcue.getCode() == 6) {
%>
			<font color = "red"><strong><%= rmcue.getMessage() %></strong></font>
<%
		}
	}
%>

    