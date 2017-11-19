<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%@ page import= "java.util.ArrayList" %>
<%
	int postID = Integer.parseInt(request.getParameter("postID"));	
	int userID = Integer.parseInt(request.getParameter("userID"));
	String message = request.getParameter("message");
	ArrayList<RMPost> postList = RMDatabase.getAllMarketplacePosts();
	for(RMPost post : postList) {
		if(post.getPostID() == postID) {
			try{
				post.makeComment(userID, message);
				ArrayList<RMChatMessage> comments = post.getComments();
				for(RMChatMessage comment : comments) {
%>
					<span class="well well-sm message">
						<img src="<%= RMDatabase.getUserForID(comment.getUserID()).getImage() %>" class="img-circle" alt="Profile image" width="20px" height="20px">
						<span><%= RMDatabase.getUserForID(comment.getUserID()).getFullName() %> : </span><br>
	                    	<span><%= comment.getMessage() %></span>
	                </span><br>
<%
				}
			} catch (RMMakeCommentException rmmce) {
				
			}
		}
	}
%>