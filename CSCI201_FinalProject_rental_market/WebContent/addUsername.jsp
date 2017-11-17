<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%
	RMUser user = (RMUser)request.getSession().getAttribute("user");
%>
<h3 class="username"><%= user.getFullName() %></h3>
<br />
<div id = "usernameheader"><%= user.getEmail() %></div>