package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public ArrayList<RMChatMessage> getComments() {
		ArrayList<RMChatMessage> comments = new ArrayList<RMChatMessage>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// Gets the chat for this post
		try {
			ps = RMDatabase.conn.prepareStatement("SELECT * FROM Chat WHERE postID=?;");
			ps.setInt(1, postID);
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
		
		// Gets the chat for this post
		try {
			ps = RMDatabase.conn.prepareStatement("SELECT * FROM Chat WHERE postID=?;");
			ps.setInt(1, postID);
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
		
		RMDatabase.sendNotificationToUser(userID, "Someone commented on your post.");
	}
	
	public void deletePost() {
		PreparedStatement ps = null;
		
		try {
			// Deletes this post
			ps = RMDatabase.conn.prepareStatement("UPDATE Post SET deleted=? WHERE postID=?;");
			ps.setBoolean(1, true);
			ps.setInt(2, postID);
			ps.executeUpdate();
			this.deleted = true;
			ps.close();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} finally {
			try {
				if (ps != null) ps.close();
			} catch (SQLException sqle) {
				System.out.println("sqle closing stuff: " + sqle.getMessage());
			}
		}
	}
	
	public ArrayList<RMRequest> getRequests() {
		ArrayList<RMRequest> requests = new ArrayList<RMRequest>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// Gets all requests that are linked to this post
		try {
			ps = RMDatabase.conn.prepareStatement("SELECT * FROM Request WHERE postID=? AND deleted=?;");
			ps.setInt(1, postID);
			ps.setBoolean(2, false);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int requestID = rs.getInt("requestID");
				String itemName = rs.getString("itemName");
				Date requestDate = rs.getDate("requestDate");
				Date dueDate = rs.getDate("dueDate");
				boolean completed = rs.getBoolean("completed");
				boolean deleted = rs.getBoolean("deleted");
				int rating = rs.getInt("rating");
				int borrowerID = rs.getInt("borrowerID");
				int lenderID = rs.getInt("lenderID");
				
				// Adds the request to the array
				requests.add(new RMRequest(requestID, itemName, requestDate, dueDate, completed, deleted, rating, borrowerID, lenderID, postID));
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
		
		return requests;
	}
	
	void completePost() {
		PreparedStatement ps = null;
		
		try {
			// Deletes this post
			ps = RMDatabase.conn.prepareStatement("UPDATE Post SET completed=? WHERE postID=?;");
			ps.setBoolean(1, true);
			ps.setInt(2, postID);
			ps.executeUpdate();
			this.deleted = true;
			ps.close();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} finally {
			try {
				if (ps != null) ps.close();
			} catch (SQLException sqle) {
				System.out.println("sqle closing stuff: " + sqle.getMessage());
			}
		}
	}
}
