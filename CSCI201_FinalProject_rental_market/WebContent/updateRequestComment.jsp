<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%@ page import= "java.util.ArrayList" %>
<%
	RMUser user = (RMUser)request.getSession().getAttribute("user");
	int requestID = Integer.parseInt(request.getParameter("requestID"));	
	int userID = Integer.parseInt(request.getParameter("userID"));
	String message = request.getParameter("message");
	ArrayList<RMRequest> requestList = RMDatabase.getRequestsForBorrower(user.getUserID());
	RMRequest rmRequest = null;
	for(RMRequest temp : requestList) {
		if(temp.getRequestID() == requestID) {
			rmRequest = temp;
		}
	}
	if(rmRequest == null) {
		requestList = RMDatabase.getRequestsForLender(user.getUserID());
		for(RMRequest temp : requestList) {
			if(temp.getRequestID() == requestID) {
				rmRequest = temp;
			}
		}
	}
	String flag = "true";
	if(rmRequest == null) {
		flag = "false";
	}
	if(flag.equals("true")){
			try{
				rmRequest.makeComment(userID, message);
				ArrayList<RMChatMessage> comments = rmRequest.getComments();
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
%>