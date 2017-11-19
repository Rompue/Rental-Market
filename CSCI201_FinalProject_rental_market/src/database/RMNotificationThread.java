package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class RMNotificationThread extends Thread {
	
	private int userID;
	private boolean shouldContinue;
	
	private Vector<RMNotification> notifications = new Vector<RMNotification>();
	
	public RMNotificationThread(int userID) {
		this.userID = userID;
		shouldContinue = false;
		this.start();
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void startReceivingNotifications() {
		shouldContinue = true;
	}
	
	public void stopReceivingNotifications() {
		shouldContinue = false;
	}
	
	public Vector<RMNotification> getNotifications() {
		return notifications;
	}
	
	public void run() {
		try {
			while (true) {
				if (shouldContinue) {
					loadNotifications();
				}
				Thread.sleep(5000);
			}
		} catch (InterruptedException ie) {
			System.out.println("ie: " + ie.getMessage());
		}
	}
	
	void loadNotifications() {
		notifications.clear();
		
		// Initial null SQL variables
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// Get all notifications for this user
		try {
			ps = RMDatabase.conn.prepareStatement("SELECT * FROM Notification WHERE userID=? AND active=?;");
			ps.setInt(1, userID);
			ps.setBoolean(2, true);
			rs = ps.executeQuery();
			
			// Gets information for each notification
			while (rs.next()) {
				int notificationID = rs.getInt("notificationID");
				String text = rs.getString("text");
				Date notificationDate = rs.getDate("notificationDate");
				boolean active = rs.getBoolean("active");
				notifications.add(new RMNotification(notificationID, userID, notificationDate, text, active));
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
