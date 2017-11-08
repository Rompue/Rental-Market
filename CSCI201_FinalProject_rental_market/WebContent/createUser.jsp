<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="RM_Database.jsp" %>
<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String firstname = request.getParameter("firstname");
	String lastname = request.getParameter("lastname");
	try {
		createUser(firstname, lastname, username, password);
	} catch (RMCreateUserException rmcue) {
		if(rmcue.getCode() == 1) {
%>
			<font color = "red"><strong>Email Has Been Registered</strong></font>
<%
		}
		if(rmcue.getCode() == 2) {
%>
			<font color = "red"><strong>Fail to Create User</strong></font>
<%
		}
		if(rmcue.getCode() == 3) {
%>
			<font color = "red"><strong>Please Enter A First Name</strong></font>
<%
		}
		if(rmcue.getCode() == 4) {
%>
			<font color = "red"><strong>Please Enter A Last Name</strong></font>
<%
		}
		if(rmcue.getCode() == 5) {
%>
			<font color = "red"><strong>Please Enter An Email</strong></font>
<%
		}
		if(rmcue.getCode() == 6) {
%>
			<font color = "red"><strong>Please Enter A Password</strong></font>
<%
		}
	}
%>

    