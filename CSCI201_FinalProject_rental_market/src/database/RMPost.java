package database;

import java.sql.Date;

public class RMPost {
	
	private int postID;
	private String itemName;
	private String postDescription;
	private String borrowAmount;
	private Date postDate;
	private Date dueDate;
	private boolean completed;
	private boolean deleted;
	
	private int userID;
	
	public RMPost(int postID, String itemName, String postDescription, String borrowAmount, Date postDate, Date dueDate, boolean completed, boolean deleted, int userID) {
		this.postID = postID;
		this.itemName = itemName;
		this.postDescription = postDescription;
		this.borrowAmount = borrowAmount;
		this.postDate = postDate;
		this.dueDate = dueDate;
		this.completed = completed;
		this.deleted = deleted;
		this.userID = userID;
	}
	
	public int getPostID() {
		return postID;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public String getPostDescription() {
		return postDescription;
	}
	
	public String getBorrowAmount() {
		return borrowAmount;
	}
	
	public Date getPostDate() {
		return postDate;
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
	
	public int getUserID() {
		return userID;
	}
	
	public void sendRequest(int lenderID) throws RMCreateRequestException {
		RMDatabase.createNewRequest(lenderID, this.getUserID(), this.getItemName(), this.getDueDate(), this.getPostID());
	}
}
