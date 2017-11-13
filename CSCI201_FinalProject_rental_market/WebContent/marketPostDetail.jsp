<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("divID");
%>
<div class="panel panel-default">
      <div class="panel-heading">
        <img src="https://unsplash.it/20/20" class="img-circle" alt="Profile image" width="20px" height="20px">
        <span><%= id %></span>
        <span><%= id %></span>
      </div>
      <div class="panel-body">
        <p>Item requested</p>
        <p>Description of the item.</p>
        <p>$2 need before 10/28 return before 10/31</p>
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