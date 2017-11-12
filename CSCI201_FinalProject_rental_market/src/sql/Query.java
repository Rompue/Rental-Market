package sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;


import objects.Chat;
import objects.Request;
import objects.User;

public class Query {
	
	private Connection conn;
	private	Statement st;
	private	ResultSet rs;
	private User client;

		
	// establish a connection with the database
	// @param inputs:
	public Query() {
		// TODO Auto-generated constructor stub
				
	}
	
	public User getUserProfile() {
		return client;
	}
	
	// TODO 
	// @return a list of requests in marketplace
	public List<Request> getRequestsInMarket(){
		
	}
	
	// TODO 
	// @return a list of requests of the client
	public List<Request> getClientsRequests(){
		
	}
	
	// TODO 
	// @return a single request by id
	public Request getRequest(int requestId) {
		
	}
	
	// TODO 
	// @return a list of chats that belongs to a specific request
	public List<Chat> getChatsOfARequest(int requestId) {
		
	}
	
	// TODO 
	// @param inputs:
	// @return whether successfully created
	public boolean createNewRequest() {
		
	}
	
	// TODO 
	// when chat with someone
	// @param inputs:
	// @return whether the message is successfully sent
	public boolean sendAMessage() {
		
	}
	
	// TODO 
	// when leave a comment
	// @param inputs:
	// @return whether the comment is successfully sent
	public boolean comment() {
		
	}
	
	// TODO 
	// when complete a request
	// @param inputs:
	// @return whether the request is successfully completed
	public boolean completeARequest() {
		
	}
	
	// TODO 
	// when delete a request
	// @param inputs:
	// @return whether the request is successfully deleted
	public boolean deleteRequest() {
		
	}
}
