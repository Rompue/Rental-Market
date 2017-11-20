<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%
	RMUser user = (RMUser)request.getSession().getAttribute("user");
	user = RMDatabase.getUserForID(user.getUserID());
%>
	<div class="profileimage">
        <img src="<%= user.getImage() %>" class="img-circle" alt="Profile image" width="100px" height="100px">  
           <button type="button" id="changeProf" class="btn btn-default btn-block" onclick="getProfilePicLink()">Change Picture</button>
    </div>
    <h3><%= user.getFullName() %> <small> <%= user.getEmail() %></small></h3>
    <h4><%= user.getPositivePercentage()  %>% Positive Rating</h4><hr>