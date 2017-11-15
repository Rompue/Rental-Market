<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%@ page import= "java.util.ArrayList" %>

<%
	ArrayList<RMPost> postList = RMDatabase.getAllMarketplacePosts();
	for(RMPost post : postList) {
		RMUser user = RMDatabase.getUserForID(post.getUserID());
%>
		<div id = "<%= post.getPostID() %>" class="panel panel-default" style="cursor: pointer;" onclick="showDetail(this)">
      		<div class="panel-heading">
        			<img src="<%= user.getImage() %>" class="img-circle" alt="Profile image" width="20px" height="20px">
        			<span><%= user.getFirstName() %> <%= user.getLastName() %></span>
<%
					String date = "" + post.getPostDate();
%>
        			<span><%= date %></span>
      		</div>
      		<div class="panel-body">
        			<p><%= post.getItemName() %></p>
        			<p><%= post.getPostDescription() %></p>
<%
					String end = "" + post.getDueDate();
%>
        			<p>Until <%= end %></p>
        			<p>Willing to Pay: <%= post.getBorrowAmount() %></p>
      		</div>
    		</div>
<%
	}
%>
