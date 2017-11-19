<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%
	RMUser user = (RMUser)request.getSession().getAttribute("user");
%>
	<div class="profileimage">
        <img src="<%= user.getImage() %>" class="img-circle" alt="Profile image" width="100px" height="100px">  
           <button type="button" class="btn">Change Picture (possible)</button>
    </div>
    <h3><%= user.getFullName() %> <small> <%= user.getEmail() %></small></h3>
    <h4><%= user.getPositivePercentage() %> Positive Rating</h4><hr>
    <div class="well well-sm" id="comments">Comments</div>
    		<div class="panel panel-success">
        		<div class="panel-heading">
          		<img src="https://unsplash.it/20/20" class="img-circle" alt="Profile image" width="20px" height="20px">
          		<span>[positive] Username</span>
          		<span>Created Date</span>
        		</div>
        		<div class="panel-body">
          		<p>comment</p>
        		</div>
      	</div>
      	<div class="panel panel-danger">
        		<div class="panel-heading">
          		<img src="https://unsplash.it/20/20" class="img-circle" alt="Profile image" width="20px" height="20px">
          		<span>[negative] Username</span>
          		<span>Created Date</span>
        		</div>
        		<div class="panel-body">
          		<p>comment</p>
        		</div>
      	</div>