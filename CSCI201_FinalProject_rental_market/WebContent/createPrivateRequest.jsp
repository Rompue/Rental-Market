<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%@ page import = "java.sql.Date" %>
<%
	String username = request.getParameter("username");
	String itemname = request.getParameter("itemname");
	String returnby = request.getParameter("returnby");
	int checkNull = 1;
	if(returnby == null) {
		checkNull = -1;
%>
		<font color = "red"><strong>Missing Due Date</strong></font>
<%
	}
	if(returnby.length() == 0) {
		checkNull = -1;
%>
		<font color = "red"><strong>Missing Due Date</strong></font>
<%
	}
	if(checkNull == 1) {
	int year = Integer.parseInt(returnby.substring(0, 4));
	int month = Integer.parseInt(returnby.substring(5, 7));
	int day = Integer.parseInt(returnby.substring(8));
	Date dueDate = new Date(year, month, day);
	RMUser user = (RMUser)request.getSession().getAttribute("user");
	try {
		RMDatabase.sendRequestToUser(user.getUserID(), username, itemname, dueDate);
	} catch (RMCreateRequestException rmcre) {
		if(rmcre.getCode() == 1) {
%>
			<font color = "red"><strong><%= rmcre.getMessage() %></strong></font>
<%
		}
		if(rmcre.getCode() == 2) {
%>
			<font color = "red"><strong><%= rmcre.getMessage() %></strong></font>
<%
		}
		if(rmcre.getCode() == 3) {
%>
			<font color = "red"><strong><%= rmcre.getMessage() %></strong></font>
<%
		}
	}
	}
%>