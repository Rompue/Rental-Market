<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%
	RMUser user = (RMUser)request.getSession().getAttribute("user");
	String url = request.getParameter("picUrl");
	System.out.println(url);
	user.updateProfilePicture(url);
%>