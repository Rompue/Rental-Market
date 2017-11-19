<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%@ page import= "java.util.ArrayList" %>
<%
	RMUser currUser = (RMUser)request.getSession().getAttribute("user");
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
                			<span><b>Leave a Comment</b></span>
                		</div>
                		<div id = "postCommentSec" class="panel-body">
<%
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
%>
					</div>
					<div class="input-group">
                        	<input id = "userComment" name = "userComment" type="text" class="form-control">
                        	<div class="btn-group">
                        		<button class="btn btn-default" type="submit" onclick = "updatePostComment('<%= post.getPostID() %>', '<%= currUser.getUserID() %>', '<%= user.getEmail() %>')">Send</button>
          				</div>
                     </div>
              	</div>
     		 </div>
    		</div>
 <%
		}
	}
 %>