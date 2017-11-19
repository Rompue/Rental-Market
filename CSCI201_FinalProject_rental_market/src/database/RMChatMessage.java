package database;

import java.sql.Date;

public class RMChatMessage {
	
	private int userID;
	private int chatID;
	
	private String message;
	private Date dateSent;
	
	public RMChatMessage(int userID, int chatID, String message, Date dateSent) {
		this.userID = userID;
		this.chatID = chatID;
		this.message = message;
		this.dateSent = dateSent;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public int getChatID() {
		return chatID;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Date getDateSent() {
		return dateSent;
	}

}
