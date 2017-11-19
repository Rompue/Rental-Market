<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%@ page import= "java.util.ArrayList" %>
<%
	int postID = Integer.parseInt(request.getParameter("postID"));
	ArrayList<RMPost> postList = RMDatabase.getAllMarketplacePosts();
	for(RMPost post : postList) {
		if(post.getPostID() == postID) {
			post.deletePost();
		}
	}
%>