package database;

import java.sql.Date;

public class RMRequest {
	
	private int requestID;
	private String itemName;
	private Date requestDate;
	private Date dueDate;
	private boolean completed;
	private boolean deleted;
	private int rating;
	
	private int borrowerID;
	private int lenderID;
	private int chatID;
	private int postID;
	
	public RMRequest(int requestID, String itemName, Date requestDate, Date dueDate, boolean completed, boolean deleted, int rating, int borrowerID, int lenderID, int chatID, int postID) {
		this.requestID = requestID;
		this.itemName = itemName;
		this.requestDate = requestDate;
		this.dueDate = dueDate;
		this.completed = completed;
		this.deleted = deleted;
		this.rating = rating;
		this.borrowerID = borrowerID;
		this.lenderID = lenderID;
		this.chatID = chatID;
		this.postID = postID;
	}
	
	public int getRequestID() {
		return requestID;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public Date getRequestDate() {
		return requestDate;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	
	public boolean getCompleted() {
		return completed;
	}
	
	public boolean getDeleted() {
		return deleted;
	}
	
	public int getRating() {
		return rating;
	}
	
	public int getBorrowerID() {
		return borrowerID;
	}
	
	public int getLenderID() {
		return lenderID;
	}
	
	public int getChatID() {
		return chatID;
	}
	
	public int getPostID() {
		return postID;
	}
}