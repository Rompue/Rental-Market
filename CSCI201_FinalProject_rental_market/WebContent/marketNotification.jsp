<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String notifyType = request.getParameter("notifyType");
	if(notifyType.equals("New Post")) {
%>
		<font color = "red"><strong>New Post on Market</strong></font>
<%
	}
	if(notifyType.equals("New Request")) {
%>
		<font color = "red"><strong>New Request Received</strong></font>
<%
	}
%>