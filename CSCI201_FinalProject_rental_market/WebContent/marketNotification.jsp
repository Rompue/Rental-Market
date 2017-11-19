<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String notifyType = request.getParameter("notifyType");
	if(notifyType.equals("Post Update")) {
%>
		<font color = "red"><strong>Post Update on Market</strong></font>
<%
	}
	if(notifyType.equals("Request Update")) {
%>
		<font color = "red"><strong>Request Update Received</strong></font>
<%
	}
	if(notifyType.equals("Post Comment")) {
%>
		<font color = "red"><strong>New Comment on Post</strong></font>
<%
	}
	if(notifyType.equals("Request Comment")) {
%>
		<font color = "red"><strong>New Comment on Request</strong></font>
<%
	}
	if(notifyType.equals("New Post Request")) {
%>
		<font color = "red"><strong>New Post Request Received</strong></font>
<%
	}
	if(notifyType.equals("New Rating")) {
%>
		<font color = "red"><strong>New Rating Available</strong></font>
<%
	}
%>