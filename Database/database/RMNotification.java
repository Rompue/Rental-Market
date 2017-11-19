package database;

import java.sql.Date;

public class RMNotification {
	private int notificationID;
	private int userID;
	private Date notificationDate;
	private String text;
	private boolean active;
		
	public RMNotification(int notificationID, int userID, Date notificationDate, String text, boolean active) {
		this.notificationID = notificationID;
		this.userID = userID;
		this.notificationDate = notificationDate;
		this.text = text;
		this.active = active;
	}
	
	public int getNotificationID() {
		return notificationID;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public Date getNotificationDate() {
		return notificationDate;
	}
	
	public String getText() {
		return text;
	}
	
	public boolean getActive() {
		return active;
	}
}
