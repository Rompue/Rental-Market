<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%@ page import= "java.util.ArrayList" %>
<%
	RMUser user = (RMUser)request.getSession().getAttribute("user");
	ArrayList<RMRequest> requestList = RMDatabase.getRequestsForBorrower(user.getUserID());
	for(RMRequest rmRequest : requestList) {
		if((rmRequest.getCompleted() == false)) {
%>		
			<div id = "<%= rmRequest.getRequestID() %>" class="panel panel-warning middlerow" onclick="showRequestDetail(this)">
          		<div class="panel-heading">
<%
					RMUser lender = RMDatabase.getUserForID(rmRequest.getLenderID());
%>
            			<img src="<%= lender.getImage() %>" class="img-circle" alt="Profile image" width="20px" height="20px">
            			<span>[Ongoing] [Borrowed From] <%= lender.getFullName() %></span>
<%
					String date = "Created on " + rmRequest.getRequestDate();
%>
           			<span><%= date %></span>
          		</div>
          		<div class="panel-body">
            			<p>Item: <%= rmRequest.getItemName() %></p>
            			<p>Discription of the item.</p>
            			<p>Until: <%= rmRequest.getDueDate() %></p>
            			<p>Amount</p>
          		</div>
        		</div>	
<%
		}
	}
	requestList = RMDatabase.getRequestsForLender(user.getUserID());
	for(RMRequest rmRequest : requestList) {
		if((rmRequest.getCompleted() == false)) {
%>
			<div id = "<%= rmRequest.getRequestID() %>" class="panel panel-warning middlerow" onclick="showRequestDetail(this)">
          		<div class="panel-heading">
<%
					RMUser borrower = RMDatabase.getUserForID(rmRequest.getBorrowerID());
%>
            			<img src="<%= borrower.getImage() %>" class="img-circle" alt="Profile image" width="20px" height="20px">
            			<span>[Ongoing] [Lent to] <%= borrower.getFullName() %></span>
<%
					String date = "Created on " + rmRequest.getRequestDate();
%>
           			<span><%= date %></span>
          		</div>
          		<div class="panel-body">
            			<p>Item: <%= rmRequest.getItemName() %></p>
            			<p>Discription of the item.</p>
            			<p>Until: <%= rmRequest.getDueDate() %></p>
            			<p>Amount</p>
          		</div>
        		</div>	
<%
		}
	}
	requestList = RMDatabase.getRequestsForBorrower(user.getUserID());
	for(RMRequest rmRequest : requestList) {
		if((rmRequest.getCompleted() == true)) {
%>
			<div id = "<%= rmRequest.getRequestID() %>" class="panel panel-success middlerow" onclick="showRequestDetail(this)">
          		<div class="panel-heading">
<%
					RMUser lender = RMDatabase.getUserForID(rmRequest.getLenderID());
%>
            			<img src="<%= lender.getImage() %>" class="img-circle" alt="Profile image" width="20px" height="20px">
            			<span>[Completed] [Borrowed by] <%= lender.getFullName() %></span>
<%
					String date = "Created on " + rmRequest.getRequestDate();
%>
           			<span><%= date %></span>
          		</div>
          		<div class="panel-body">
            			<p>Item: <%= rmRequest.getItemName() %></p>
            			<p>Discription of the item.</p>
            			<p>Until: <%= rmRequest.getDueDate() %></p>
            			<p>Amount</p>
          		</div>
        		</div>	
<%
		}
	}
	requestList = RMDatabase.getRequestsForLender(user.getUserID());
	for(RMRequest rmRequest : requestList) {
		if((rmRequest.getCompleted() == true)) {
%>
			<div id = "<%= rmRequest.getRequestID() %>" class="panel panel-success middlerow" onclick="showRequestDetail(this)">
          		<div class="panel-heading">
<%
					RMUser borrower = RMDatabase.getUserForID(rmRequest.getLenderID());
%>
            			<img src="<%= borrower.getImage() %>" class="img-circle" alt="Profile image" width="20px" height="20px">
            			<span>[Completed] [Lent to] <%= borrower.getFullName() %></span>
<%
					String date = "Created on " + rmRequest.getRequestDate();
%>
           			<span><%= date %></span>
          		</div>
          		<div class="panel-body">
            			<p>Item: <%= rmRequest.getItemName() %></p>
            			<p>Discription of the item.</p>
            			<p>Until: <%= rmRequest.getDueDate() %></p>
            			<p>Amount</p>
          		</div>
        		</div>	
<%
		}
	}
%>