package database;

import java.sql.Date;

public class RMRequest {
	
	private int requestID;
	private String itemName;
	private Date dateCreated;
	private Date dueDate;
	private boolean completed;
	private boolean deleted;
	private int rating;
	
	private int borrowerID;
	private int lenderID;
	private int postID;
	
	public RMRequest(int requestID, String itemName, Date dateCreated, Date dueDate, boolean completed, boolean deleted, int rating, int borrowerID, int lenderID, int postID) {
		this.requestID = requestID;
		this.itemName = itemName;
		this.dateCreated = dateCreated;
		this.dueDate = dueDate;
		this.completed = completed;
		this.deleted = deleted;
		this.rating = rating;
		this.borrowerID = borrowerID;
		this.lenderID = lenderID;
		this.postID = postID;
	}
	
	public int getRequestID() {
		return requestID;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public Date getDateCreated() {
		return dateCreated;
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
	
	public int getPostID() {
		return postID;
	}
}
