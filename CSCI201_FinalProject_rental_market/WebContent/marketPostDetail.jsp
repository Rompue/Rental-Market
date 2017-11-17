<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%@ page import= "java.util.ArrayList" %>
<%
	String idStr = request.getParameter("postID");
	int id = Integer.parseInt(idStr);
	ArrayList<RMPost> postList = RMDatabase.getAllMarketplacePosts();
	if(id == -1) {
		if(postList.size() > 0) {
			id = postList.get(0).getPostID();
		}
	}
	for(RMPost post : postList) {
		if(post.getPostID() == id) {
			RMUser user = RMDatabase.getUserForID(post.getUserID());
%>
			<div class="panel panel-default">
      			<div class="panel-heading">
        				<img src="<%= user.getImage() %>" class="img-circle" alt="Profile image" width="20px" height="20px">
        				<span><%= user.getFirstName() %> <%= user.getLastName() %></span>
        				<%
					String date = "Created on " + post.getPostDate();
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
        				<hr>
				<div class="panel">
          			<div class="panel-heading">
            				<!-- <img src="https://unsplash.it/20/20" class="img-circle" alt="Profile image" width="20px" height="20px"> -->
            				<span>Chatting with the user</span>
          			</div>
          			<div class="panel-body">
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