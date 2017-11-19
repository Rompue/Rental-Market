package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	private int postID;
	
	public RMRequest(int requestID, String itemName, Date requestDate, Date dueDate, boolean completed, boolean deleted, int rating, int borrowerID, int lenderID, int postID) {
		this.requestID = requestID;
		this.itemName = itemName;
		this.requestDate = requestDate;
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
	
	public int getPostID() {
		return postID;
	}
	
	public ArrayList<RMChatMessage> getComments() {
		ArrayList<RMChatMessage> comments = new ArrayList<RMChatMessage>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// Gets the chat for this request
		try {
			ps = RMDatabase.conn.prepareStatement("SELECT * FROM Chat WHERE requestID=?;");
			ps.setInt(1, requestID);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				int chatID = rs.getInt("chatID");
				rs.close();
				ps.close();
				
				// Gets the ChatMessage objects
				ps = RMDatabase.conn.prepareStatement("SELECT * FROM ChatMessage WHERE chatID=?;");
				ps.setInt(1, chatID);
				rs = ps.executeQuery();
				
				while (rs.next()) {
					int userID = rs.getInt("userID");
					String message = rs.getString("message");
					Date dateSent = rs.getDate("dateSent");
					
					// Adds the chat message to the list of messages
					RMChatMessage chatMessage = new RMChatMessage(userID, chatID, message, dateSent);
					comments.add(chatMessage);
				}
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
			} catch (SQLException sqle) {
				System.out.println("sqle closing stuff: " + sqle.getMessage());
			}
		}
		
		return comments;
	}
	
	public void makeComment(int userID, String message) throws RMMakeCommentException {
		// Checks if message is valid
		if (message == null || message.trim().equals("")) throw new RMMakeCommentException("message is empty", 2);
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// Gets the chat for this request
		try {
			ps = RMDatabase.conn.prepareStatement("SELECT * FROM Chat WHERE requestID=?;");
			ps.setInt(1, requestID);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				int chatID = rs.getInt("chatID");
				rs.close();
				rs = null;
				ps.close();
				
				// Creates a new ChatMessage with this chatID
				ps = RMDatabase.conn.prepareStatement("INSERT INTO ChatMessage (message, dateSent, userID, chatID) VALUES (?, ?, ?, ?);");
				ps.setString(1, message);
				ps.setDate(2, new Date(System.currentTimeMillis()));
				ps.setInt(3, userID);
				ps.setInt(4, chatID);
				ps.executeUpdate();
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
			} catch (SQLException sqle) {
				System.out.println("sqle closing stuff: " + sqle.getMessage());
			}
		}
	}
}
