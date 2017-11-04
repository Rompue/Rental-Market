package database;

public class RMCreateUserException extends RMException {
	
	/*
	 * Error codes
	 * 0 - no error (though no exception would have been thrown in the first place)
	 * 1 - another user with email already exists
	 * 2 - some SQL connection or networking error occurred
	 * 3 - first name field was empty
	 * 4 - last name field was empty
	 * 5 - email field was empty
	 * 6 - password field was empty
	 */
	
	public RMCreateUserException(String errorMessage, int code) {
		super(errorMessage, code);
	}
	
}
