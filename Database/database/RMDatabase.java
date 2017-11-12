package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RMDatabase {
	
	private static Connection conn = null;
	private static boolean databaseInitialized = false;
	
	public static void initializeDatabase() {
		if (!databaseInitialized) {
			// Initial SQL and database connections
			try {
				Class.forName("com.mysql.jdbc.Driver");
				// Connects to database
				conn = DriverManager.getConnection("jdbc:mysql://localhost/RentalMarketplace?user=root&password=CSCI-201&useSSL=false");
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			} catch (ClassNotFoundException cnfe) {
				System.out.println("cnfe: " + cnfe.getMessage());
			}
			databaseInitialized = true;
		}
	}
	
	// Used to create new user accounts
	public static void createUser(String firstName, String lastName, String email, String password) throws RMCreateUserException {
		
		// Checks to ensure information was actually passed in
		if (firstName == null || firstName.trim().equals("")) throw new RMCreateUserException("First name field was empty", 3);
		else if (lastName == null || lastName.trim().equals("")) throw new RMCreateUserException("Last name field was empty", 4);
		else if (email == null || email.trim().equals("")) throw new RMCreateUserException("Email field was empty", 5);
		else if (password == null || password.trim().equals("")) throw new RMCreateUserException("Password field was empty", 6);
		
		// Initial null SQL variables
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// First, we want to ensure there isn't already a user with this email
		try {
			ps = conn.prepareStatement("SELECT * FROM Person WHERE email=?;");
			ps.setString(1, email);
			rs = ps.executeQuery();
			
			if (rs.next()) { // There is already a user with this email
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				throw new RMCreateUserException("A user with this email already exists", 1);
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		}
		
		// Since there are no other users, add this user to the database
		try {
			ps = conn.prepareStatement("INSERT INTO Person (firstName, lastName, email, passcode, positiveRatings, negativeRatings, totalRatings) VALUES (?, ?, ?, ?, 0, 0, 0);");
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, email);
			ps.setString(4, password);
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
	}

	// Used to authenticate user accounts (when email and password is enterd in)
	public static RMUser authenticateUser(String email, String password) throws RMAuthenticateUserException {
		// Checks to ensure information was actually passed in
		if (email == null || email.trim().equals("")) throw new RMAuthenticateUserException("Email field was empty", 3);
		else if (password == null || password.trim().equals("")) throw new RMAuthenticateUserException("Password field was empty", 4);
		
		// Initial null SQL variables
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		RMUser user = null;
		
		// Attempt to log in
		try {
			ps = conn.prepareStatement("SELECT * FROM Person WHERE email=?;");
			ps.setString(1, email);
			rs = ps.executeQuery();
			
			if (rs.next()) { // Found a user, now check if the password matches
				if (rs.getString("passcode").equals(password)) { // Password matches
					// Gets all necessary information and puts it in a user object
					int userID = rs.getInt("userID");
					String firstName = rs.getString("firstName");
					String lastName = rs.getString("lastName");
					int positiveRatings = rs.getInt("positiveRatings");
					int negativeRatings = rs.getInt("negativeRatings");
					int totalRatings = rs.getInt("totalRatings");
					String image = rs.getString("image");
					
					user = new RMUser(userID, firstName, lastName, email, positiveRatings, negativeRatings, totalRatings, image);
				}
				else { // Email and password were incorrect
					throw new RMAuthenticateUserException("Incorrect password for " + email, 1);
				}
			}
			else { // No user with this email exists
				throw new RMAuthenticateUserException("No user with email " + email + " exists", 2);
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException sqle) {
				System.out.println("sqle closing stuff: " + sqle.getMessage());
			}
		}
		
		return user;
	}

	// Gets all marketplace posts from the database
	public static ArrayList<RMPost> getAllMarketplacePosts() {
		// Initial null SQL variables
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<RMPost> posts = new ArrayList<RMPost>();
		
		// Get all posts
		try {
			ps = conn.prepareStatement("SELECT * FROM Person WHERE email=?;");
			rs = ps.executeQuery();
			
			// Gets information for each post
			while (rs.next()) {
				int postID = rs.getInt("postID");
				String itemName = rs.getString("itemName");
				String postDescription = rs.getString("postDescription");
				String borrowAmount = rs.getString("borrowAmount");
				Date postDate = rs.getDate("postDate");
				boolean completed = rs.getBoolean("completed");
				boolean deleted = rs.getBoolean("deleted");
				int userID = rs.getInt("userID");
				int chatID = rs.getInt("chatID");
				
				// Adds to list of posts
				posts.add(new RMPost(postID, itemName, postDescription, borrowAmount, postDate, completed, deleted, userID, chatID));
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
		
		return posts;
	}
}