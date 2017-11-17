package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RMDatabase {
	
	public static Connection conn = null;
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
			} catch (SQLException sqle) {
				System.out.println("sqle closing stuff: " + sqle.getMessage());
			}
		}
		
		return user;
	}
	
	public static RMUser getUserForID(int id) {
		// Initial null SQL variables
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		RMUser user = null;
		
		// Attempt to log in
		try {
			ps = conn.prepareStatement("SELECT * FROM Person WHERE userID=?;");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if (rs.next()) { // Found a user, now check if the password matches
				int userID = rs.getInt("userID");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String email = rs.getString("email");
				int positiveRatings = rs.getInt("positiveRatings");
				int negativeRatings = rs.getInt("negativeRatings");
				int totalRatings = rs.getInt("totalRatings");
				String image = rs.getString("image");
				
				user = new RMUser(userID, firstName, lastName, email, positiveRatings, negativeRatings, totalRatings, image);
			}
			else { // No user with this id exists
				return null;
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
			ps = conn.prepareStatement("SELECT * FROM Post WHERE completed=0 AND deleted=0;");
			rs = ps.executeQuery();
			
			// Gets information for each post
			while (rs.next()) {
				int postID = rs.getInt("postID");
				String itemName = rs.getString("itemName");
				String postDescription = rs.getString("postDescription");
				String borrowAmount = rs.getString("borrowAmount");
				Date postDate = rs.getDate("postDate");
				Date dueDate = rs.getDate("dueDate");
				boolean completed = rs.getBoolean("completed");
				boolean deleted = rs.getBoolean("deleted");
				int userID = rs.getInt("userID");
				
				// Adds to list of posts
				posts.add(new RMPost(postID, itemName, postDescription, borrowAmount, postDate, dueDate, completed, deleted, userID));
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
	
	// Gets requests for this lender id (all outgoing requests)
	public static ArrayList<RMRequest> getRequestsForLender(int lenderID) {
		// Initial null SQL variables
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<RMRequest> requests = new ArrayList<RMRequest>();
		
		// Get all posts
		try {
			ps = conn.prepareStatement("SELECT * FROM Request WHERE lenderID=? AND deleted=0;");
			ps.setInt(1, lenderID);
			rs = ps.executeQuery();
			
			// Gets information for each post
			while (rs.next()) {
				int requestID = rs.getInt("requestID");
				String itemName = rs.getString("itemName");
				Date dateCreated = rs.getDate("dateCreated");
				Date dueDate = rs.getDate("dueDate");
				boolean completed = rs.getBoolean("completed");
				boolean deleted = rs.getBoolean("deleted");
				int rating = rs.getInt("rating");
				int borrowerID = rs.getInt("borrowerID");
				int chatID = rs.getInt("chatID");
				int postID = rs.getInt("postID");
				
				// Adds to list of posts
				requests.add(new RMRequest(requestID, itemName, dateCreated, dueDate, completed, deleted, rating, lenderID, borrowerID, chatID, postID));
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
	
	// Gets requests for this borrower id (all incoming requests)
	public static ArrayList<RMRequest> getRequestsForBorrower(int borrowerID) {
		// Initial null SQL variables
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<RMRequest> requests = new ArrayList<RMRequest>();
		
		// Get all posts
		try {
			ps = conn.prepareStatement("SELECT * FROM Request WHERE borrowerID=? AND deleted=0;");
			ps.setInt(1, borrowerID);
			rs = ps.executeQuery();
			
			// Gets information for each post
			while (rs.next()) {
				int requestID = rs.getInt("requestID");
				String itemName = rs.getString("itemName");
				Date requestDate = rs.getDate("requestDate");
				Date dueDate = rs.getDate("dueDate");
				boolean completed = rs.getBoolean("completed");
				boolean deleted = rs.getBoolean("deleted");
				int rating = rs.getInt("rating");
				int lenderID = rs.getInt("lenderID");
				int chatID = rs.getInt("chatID");
				int postID = rs.getInt("postID");
				
				// Adds to list of posts
				requests.add(new RMRequest(requestID, itemName, requestDate, dueDate, completed, deleted, rating, lenderID, borrowerID, chatID, postID));
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
	
	public static void createNewMarketplacePost(int userID, String itemName, String postDescription, String borrowAmount, Date dueDate) throws RMCreatePostException {
		// Checks to ensure information was actually passed in
		if (itemName == null || itemName.trim().equals("")) throw new RMCreatePostException("itemName is empty", 1);
		else if (postDescription == null) throw new RMCreatePostException("postDescription is null", 2);
		else if (borrowAmount == null || borrowAmount.trim().equals("")) throw new RMCreatePostException("borrowAmount is empty", 3);
		else if (dueDate == null) throw new RMCreatePostException("dueDate is null", 4);
		
		// Initial null SQL variables
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// Attempt to log in
		try {
			ps = conn.prepareStatement("INSERT INTO Post (itemName, postDescription, borrowAmount, postDate, dueDate, completed, deleted, userID) VALUES (?, ?, ?, ?, ?, 0, 0, ?);", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, itemName);
			ps.setString(2,  postDescription);
			ps.setString(3, borrowAmount);
			ps.setDate(4, new Date(System.currentTimeMillis()));
			ps.setDate(5, dueDate);
			ps.setInt(6, userID);
			ps.executeUpdate();
			
			// Gets the generated postID so it can create a new chat
			rs = ps.getGeneratedKeys();
			if (rs.next()) { // Should always return true
				int postID = rs.getInt(1);				
				ps.close();
				// Creates a new chat
				ps = conn.prepareStatement("INSERT INTO Chat (postID) VALUES (?);");
				ps.setInt(1, postID);
				ps.execute();
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
