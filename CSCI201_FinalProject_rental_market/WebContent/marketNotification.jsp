<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%@ page import= "java.util.Vector" %>
<%
	RMUser user = (RMUser)request.getSession().getAttribute("user");
	Vector<RMNotification> notifications = RMDatabase.getNotificationsForUser(user.getUserID());
	for(RMNotification notify : notifications) {
		if(notify.getActive()) {
			notify.dismissNotification();
%>
			<tr>
    				<td><font color = "red"><strong><%= notify.getText() %></strong></font></td>
    			</tr>
<%
		}
	}
%>