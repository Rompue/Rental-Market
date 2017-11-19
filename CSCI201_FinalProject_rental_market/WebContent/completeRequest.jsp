<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%@ page import= "java.util.ArrayList" %>
<%
	RMUser user = (RMUser)request.getSession().getAttribute("user");
	int requestID = Integer.parseInt(request.getParameter("requestID"));	
	String positiveRating = request.getParameter("positiveRating");
	ArrayList<RMRequest> requestList = RMDatabase.getRequestsForBorrower(user.getUserID());
	RMRequest rmRequest = null;
	for(RMRequest temp : requestList) {
		if(temp.getRequestID() == requestID) {
			rmRequest = temp;
			if(positiveRating.equals("true")) {
				rmRequest.completeRequest(true);
			}
			if(positiveRating.equals("false")) {
				rmRequest.completeRequest(false);
			}
		}
	}
	if(rmRequest == null) {
		requestList = RMDatabase.getRequestsForLender(user.getUserID());
		for(RMRequest temp : requestList) {
			if(temp.getRequestID() == requestID) {
				rmRequest = temp;
				if(positiveRating.equals("true")) {
					rmRequest.completeRequest(true);
				}
				if(positiveRating.equals("false")) {
					rmRequest.completeRequest(false);
				}
			}
		}
	}
%>