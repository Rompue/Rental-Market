package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public void dismissNotification() {
		this.active = false;
		
		// Initial null SQL variables
		PreparedStatement ps = null;
		// Marks this notification as read
		try {
			ps = RMDatabase.conn.prepareStatement("UPDATE Notification SET active=? WHERE notificationID=?;");
			ps.setBoolean(1, false);
			ps.setInt(2, notificationID);
			ps.executeUpdate();
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
