<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%
	RMUser user = (RMUser)request.getSession().getAttribute("user");
%>
<h3 class="username"><%= user.getFirstName() %> <%= user.getLastName() %></h3>