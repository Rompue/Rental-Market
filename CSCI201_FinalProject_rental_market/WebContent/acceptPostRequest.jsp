<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%@ page import= "java.util.ArrayList" %>
<%
	int postID = Integer.parseInt(request.getParameter("postID"));
	int requestID = Integer.parseInt(request.getParameter("requestID"));
	ArrayList<RMPost> postList = RMDatabase.getAllMarketplacePosts();
	for(RMPost post : postList) {
		if(post.getPostID() == postID) {
			ArrayList<RMRequest> requests = post.getRequests();
			for(RMRequest rmRequest : requests) {
				if(rmRequest.getRequestID() == requestID) {
					try{
						rmRequest.acceptRequest();
					} catch(RMRespondToRequestException rmrtre) {
%>
						<font color = "red"><strong><%= rmrtre.getMessage() %></strong></font>
<%
					}
				}
			}
		}
	}
%>