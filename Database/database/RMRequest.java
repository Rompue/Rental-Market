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
		
		if (lenderID == userID) {
			RMDatabase.sendNotificationToUser(borrowerID, "New comment on your request from the lender.");
		}
		else if (borrowerID == userID) {
			RMDatabase.sendNotificationToUser(lenderID, "New comment on your request from the borrower.");
		}
	}
	
	public void deleteRequest() {
		PreparedStatement ps = null;
		
		try {
			// Deletes this request
			ps = RMDatabase.conn.prepareStatement("UPDATE Request SET deleted=? WHERE requestID=?;");
			ps.setBoolean(1, true);
			ps.setInt(2, requestID);
			ps.executeUpdate();
			this.deleted = true;
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
	
	public void acceptRequest() throws RMRespondToRequestException {
		if (postID == 0) throw new RMRespondToRequestException("Attempted to accept request that was not part of a post", 1);
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// Gets the post with this postID
			ps = RMDatabase.conn.prepareStatement("SELECT * FROM Post WHERE postID=?;");
			ps.setInt(1, postID);
			rs = ps.executeQuery();
			
			RMPost p = null;
			
			if (rs.next()) { // Should always return true
				String pItemName = rs.getString("itemName");
				String pPostDescription = rs.getString("postDescription");
				String pBorrowAmount = rs.getString("borrowAmount");
				Date pPostDate = rs.getDate("postDate");
				Date pDueDate = rs.getDate("dueDate");
				boolean pCompleted = rs.getBoolean("completed");
				boolean pDeleted = rs.getBoolean("deleted");
				int pUserID = rs.getInt("userID");
				
				p = new RMPost(postID, pItemName, pPostDescription, pBorrowAmount, pPostDate, pDueDate, pCompleted, pDeleted, pUserID);
				
				rs.close();
				rs = null;
				ps.close();
			}
			
			ArrayList<RMRequest> offeredRequests = p.getRequests();
			
			// Deletes all the other requests
			for (RMRequest r : offeredRequests) {
				if (r.getRequestID() == getRequestID()) continue;
				else r.declineRequest();
			}
			
			// Completes this post
			p.completePost();
			
			// Sets this request as a regular request by no longer associating it with a post
			ps = RMDatabase.conn.prepareStatement("UPDATE Request SET postID=? WHERE requestID=?;");
			ps.setNull(1, java.sql.Types.INTEGER);
			ps.setInt(2, requestID);
			ps.executeUpdate();
			
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
		
		RMDatabase.sendNotificationToUser(lenderID, "Your request to a marketplace post was accepted.");
	}
	
	public void declineRequest() throws RMRespondToRequestException {
		if (postID == 0) throw new RMRespondToRequestException("Attempted to decline request that was not part of a post", 1);
		deleteRequest();
		RMDatabase.sendNotificationToUser(lenderID, "Your request to a marketplace post was declined.");
	}
	
	public void completeRequest(boolean positiveRating) {
		// Updates this user's rating
		RMUser borrower = RMDatabase.getUserForID(borrowerID);
		borrower.addRating(positiveRating);
		
		PreparedStatement ps = null;
		try {
			// Completes this request
			ps = RMDatabase.conn.prepareStatement("UPDATE Request SET completed=?, rating=? WHERE requestID=?;");
			ps.setBoolean(1, true);
			ps.setInt(2, positiveRating ? +1 : -1);
			ps.executeUpdate();
			this.completed = true;
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} finally {
			try {
				if (ps != null) ps.close();
			} catch (SQLException sqle) {
				System.out.println("sqle closing stuff: " + sqle.getMessage());
			}
		}
		
		RMDatabase.sendNotificationToUser(borrowerID, "One of your requests has been completed with a " + (positiveRating ? "positive" : "negative") + " rating.");
	}
}
