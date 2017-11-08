<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.IOException" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Statement" %>

<%@ page import="java.util.ArrayList" %>

<%@ page import="javax.servlet.RequestDispatcher" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.annotation.WebServlet" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>

<%@ page import="java.sql.PreparedStatement" %>

<%@ page import="database.*" %>

<%!

// Used to create new user accounts
public void createUser(String firstName, String lastName, String email, String password) throws RMCreateUserException {
	
	// Checks to ensure information was actually passed in
	if (firstName == null || firstName.trim().equals("")) throw new RMCreateUserException("First name field was empty", 3);
	else if (lastName == null || lastName.trim().equals("")) throw new RMCreateUserException("Last name field was empty", 4);
	else if (email == null || email.trim().equals("")) throw new RMCreateUserException("Email field was empty", 5);
	else if (password == null || password.trim().equals("")) throw new RMCreateUserException("Password field was empty", 6);
	
	// Initial null SQL variables
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	
	// Initial SQL and database connections
	try {
		Class.forName("com.mysql.jdbc.Driver");
		// Connects to database
		conn = DriverManager.getConnection("jdbc:mysql://localhost/RentalMarketplace?user=root&password=Albertcollege@2017&useSSL=false");
	} catch (SQLException sqle) {
		System.out.println("sqle: " + sqle.getMessage());
	} catch (ClassNotFoundException cnfe) {
		System.out.println("cnfe: " + cnfe.getMessage());
	}
	
	// First, we want to ensure there isn't already a user with this email
	try {
		st = conn.createStatement();
		ps = conn.prepareStatement("SELECT * FROM Person WHERE email=?;");
		ps.setString(1, email);
		rs = ps.executeQuery();
		
		if (rs.next()) { // There is already a user with this email
			if (rs != null) rs.close();
			if (st != null) st.close();
			if (conn != null) conn.close();
			throw new RMCreateUserException("A user with this email already exists", 1);
		}
	} catch (SQLException sqle) {
		System.out.println("sqle: " + sqle.getMessage());
	}
	
	// Since there are no other users, add this user to the database
	try {
		st = conn.createStatement();
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
			if (st != null) st.close();
			if (conn != null) conn.close();
		} catch (SQLException sqle) {
			System.out.println("sqle closing stuff: " + sqle.getMessage());
		}
	}
}

// Used to authenticate user accounts (when email and password is enterd in)
public RMUser authenticateUser(String email, String password) throws RMAuthenticateUserException {
	// Checks to ensure information was actually passed in
	if (email == null || email.trim().equals("")) throw new RMAuthenticateUserException("Email field was empty", 3);
	else if (password == null || password.trim().equals("")) throw new RMAuthenticateUserException("Password field was empty", 4);
	
	// Initial null SQL variables
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	
	// Initial SQL and database connections
	try {
		Class.forName("com.mysql.jdbc.Driver");
		// Connects to database
		conn = DriverManager.getConnection("jdbc:mysql://localhost/RentalMarketplace?user=root&password=Albertcollege@2017&useSSL=false");
	} catch (SQLException sqle) {
		System.out.println("sqle: " + sqle.getMessage());
	} catch (ClassNotFoundException cnfe) {
		System.out.println("cnfe: " + cnfe.getMessage());
	}
	
	RMUser user = null;
	
	// Attempt to log in
	try {
		st = conn.createStatement();
		ps = conn.prepareStatement("SELECT * FROM Person WHERE email=?;");
		ps.setString(1, email);
		rs = ps.executeQuery();
		
		if (rs.next()) { // Found a user, now check if the password matches
			if (rs.getString("passcode").equals(password)) { // Password matches
				// Gets all necessary information and puts it in a user object
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				int positiveRatings = rs.getInt("positiveRatings");
				int negativeRatings = rs.getInt("negativeRatings");
				int totalRatings = rs.getInt("totalRatings");
				
				user = new RMUser(firstName, lastName, email, positiveRatings, negativeRatings, totalRatings);
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
			if (st != null) st.close();
			if (conn != null) conn.close();
		} catch (SQLException sqle) {
			System.out.println("sqle closing stuff: " + sqle.getMessage());
		}
	}
	
	return user;
}

%>