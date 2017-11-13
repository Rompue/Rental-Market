<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%
	RMUser user = (RMUser)request.getSession().getAttribute("user");
%>
<img src="<%= user.getImage() %>" class="img-circle" alt="Profile image" width="100px" height="100px">