<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%@ page import= "java.util.ArrayList" %>
<%
	RMUser user = (RMUser)request.getSession().getAttribute("user");
	String idStr = request.getParameter("requestID");
	int id = Integer.parseInt(idStr);
	ArrayList<RMRequest> requestList = RMDatabase.getRequestsForBorrower(user.getUserID());
	if(id == -1) {
		if(requestList.size() > 0) {
			id = requestList.get(0).getPostID();
		}
		if(requestList.size() == 0) {
			requestList = RMDatabase.getRequestsForLender(user.getUserID());
			if(requestList.size() > 0) {
				id = requestList.get(0).getPostID();
			}
			requestList = RMDatabase.getRequestsForBorrower(user.getUserID());
		}
	}
	RMRequest rmRequest = null;
	for(RMRequest temp : requestList) {
		if(temp.getRequestID() == id) {
			rmRequest = temp;
		}
	}
	if(rmRequest == null) {
		requestList = RMDatabase.getRequestsForLender(user.getUserID());
		for(RMRequest temp : requestList) {
			if(temp.getRequestID() == id) {
				rmRequest = temp;
			}
		}
	}
	String flag = "true";
	if(rmRequest == null) {
		flag = "false";
	}
	if(flag.equals("true")){
	if((rmRequest.getCompleted() == true)) {
%>
		<div class="panel panel-sucess">
        		<div class="panel-heading">
<%
				String statusStr = "";
				if(rmRequest.getBorrowerID() == user.getUserID()) {
					RMUser lender = RMDatabase.getUserForID(rmRequest.getLenderID());
					statusStr += "[Borrowed from] " + lender.getFullName();
				}
				if(rmRequest.getLenderID() == user.getUserID()) {
					RMUser borrower = RMDatabase.getUserForID(rmRequest.getLenderID());
					statusStr += "[Borrowed from] " + borrower.getFullName();
				}
%>
            		<span>[Completed] <%= statusStr %></span>
<%
				String date = "Created on " + rmRequest.getRequestDate();
%>
              	<span><%= date %></span>
            </div>
            <div class="panel-body">
            		<p><%= rmRequest.getItemName() %></p>
            		<p>Discription of the item.</p>
            		<p>Until: <%= rmRequest.getDueDate() %>
            		<p>Amount</p>
              	<button type="button" class="btn">Delete Request</button>
              	<hr>
              	<div class="panel">
                		<div class="panel-heading">
                			<img src="https://unsplash.it/20/20" class="img-circle" alt="Profile image" width="20px" height="20px">
                			<span>Chatting with Username</span>
                		</div>
                		<div class="panel-body">
                    		<span class="well well-sm message">
                    			<span>Hi, I'm Kun.</span>
                    		</span><br>
                    		<span class="well well-sm message">
                        		<span>I can lend my TI84 to you tomorrow.</span>
                      	</span>
                      	<div class="input-group">
                        		<input type="text" class="form-control">
                        		<div class="input-group-btn">
                          		<button class="btn btn-default" type="submit">Send</button>
                        		</div>
                      	</div>
              		</div>
              	</div>
            	</div>
      </div>
<%
	}
	if((rmRequest.getCompleted() == false)) {
%>
		<div class="panel panel-warning">
        		<div class="panel-heading">
<%
				String statusStr = "";
				if(rmRequest.getBorrowerID() == user.getUserID()) {
					RMUser lender = RMDatabase.getUserForID(rmRequest.getLenderID());
					statusStr += "[Borrowed from] " + lender.getFullName();
				}
				if(rmRequest.getLenderID() == user.getUserID()) {
					RMUser borrower = RMDatabase.getUserForID(rmRequest.getLenderID());
					statusStr += "[Borrowed from] " + borrower.getFullName();
				}
%>
            		<span>[Ongoing] <%= statusStr %></span>
<%
				String date = "Created on " + rmRequest.getRequestDate();
%>
              	<span><%= date %></span>
            </div>
            <div class="panel-body">
            		<p><%= rmRequest.getItemName() %></p>
            		<p>Discription of the item.</p>
            		<p>Until: <%= rmRequest.getDueDate() %>
            		<p>Amount</p>
              	<button type="button" class="btn">Delete Request</button>
              	<hr>
              	<div class="panel">
                		<div class="panel-heading">
                			<img src="https://unsplash.it/20/20" class="img-circle" alt="Profile image" width="20px" height="20px">
                			<span>Chatting with Username</span>
                		</div>
                		<div class="panel-body">
                    		<span class="well well-sm message">
                    			<span>Hi, I'm Kun.</span>
                    		</span><br>
                    		<span class="well well-sm message">
                        		<span>I can lend my TI84 to you tomorrow.</span>
                      	</span>
                      	<div class="input-group">
                        		<input type="text" class="form-control">
                        		<div class="input-group-btn">
                          		<button class="btn btn-default" type="submit">Send</button>
                        		</div>
                      	</div>
              		</div>
              	</div>
            	</div>
      </div>
<%
	}
	}
%>
