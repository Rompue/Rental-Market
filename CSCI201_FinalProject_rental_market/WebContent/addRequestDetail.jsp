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
		<div class="panel panel-success">
        		<div class="panel-heading">
<%
				String statusStr = "";
				RMUser otherUser = null;
				if(rmRequest.getBorrowerID() == user.getUserID()) {
					otherUser = RMDatabase.getUserForID(rmRequest.getLenderID());
					statusStr += "[Borrowed From] " + otherUser.getFullName();
				}
				if(rmRequest.getLenderID() == user.getUserID()) {
					otherUser = RMDatabase.getUserForID(rmRequest.getBorrowerID());
					statusStr += "[Lend To] " + otherUser.getFullName();
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
            		<p>Until: <%= rmRequest.getDueDate() %>
              	<button type="button" class="btn">Delete Request</button>
              	<hr>
              	<div class="panel">
                		<div class="panel-heading">
                			<span><b>Leave a Comment</b></span>
                		</div>
                		<div id = "requestCommentSec" class="panel-body">
<%
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
%>
					</div>
					<div class="input-group">
                        	<input id = "userComment" name = "userComment" type="text" class="form-control">
                        	<div class="btn-group">
                        		<button class="btn btn-default" type="submit" onclick = "updateRequestComment('<%= rmRequest.getRequestID() %>', '<%= user.getUserID() %>', '<%= otherUser.getEmail() %>')">Send</button>
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
				RMUser otherUser = null;
				if(rmRequest.getBorrowerID() == user.getUserID()) {
					otherUser = RMDatabase.getUserForID(rmRequest.getLenderID());
					statusStr += "[Borrowed From] " + otherUser.getFullName();
				}
				if(rmRequest.getLenderID() == user.getUserID()) {
					otherUser = RMDatabase.getUserForID(rmRequest.getBorrowerID());
					statusStr += "[Lend To] " + otherUser.getFullName();
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
            		<p>Until: <%= rmRequest.getDueDate() %>
              	<button type="button" class="btn">Delete Request</button>
              	<hr>
              	<div class="panel">
                		<div class="panel-heading">
                			<span><b>Leave a Comment</b></span>
                		</div>
                		<div id = "requestCommentSec" class="panel-body">
<%
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
%>
					</div>
					<div class="input-group">
                        	<input id = "userComment" name = "userComment" type="text" class="form-control">
                        	<div class="btn-group">
                        		<button class="btn btn-default" type="submit" onclick = "updateRequestComment('<%= rmRequest.getRequestID() %>', '<%= user.getUserID() %>', '<%= otherUser.getEmail() %>')">Send</button>
          				</div>
                     </div>
              	</div>
            	</div>
      </div>
<%
	}
	}
%>
