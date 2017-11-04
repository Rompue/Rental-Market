package database;

public class RMAuthenticateUserException extends RMException {
	
	/*
	 * Error codes
	 * 0 - no error (though no exception would have been thrown in the first place)
	 * 1 - email and password do not match
	 * 2 - no user in the database with this email
	 * 3 - email field was empty
	 * 4 - password field was empty
	 */
	
	public RMAuthenticateUserException(String errorMessage, int code) {
		super(errorMessage, code);
	}
	
}
