<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%@ page import= "java.util.ArrayList" %>
<%
	int postID = Integer.parseInt(request.getParameter("postID"));
	int userID = Integer.parseInt(request.getParameter("userID"));
	ArrayList<RMPost> postList = RMDatabase.getAllMarketplacePosts();
	for(RMPost post : postList) {
		if(post.getPostID() == postID) {
			try {
				post.sendRequest(userID);
			} catch (RMCreateRequestException rmcre) {
%>
				<font color = "red"><strong><%= rmcre.getMessage() %></strong></font>
<%
			}
		}
	}
%>