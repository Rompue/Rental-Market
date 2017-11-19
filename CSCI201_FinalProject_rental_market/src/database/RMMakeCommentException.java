package database;

public class RMMakeCommentException extends RMException {
	
	/*
	 * Error codes
	 * 0 - no error (though no exception would have been thrown in the first place)
	 * 1 - message is empty
	 */
	
	public RMMakeCommentException(String errorMessage, int code) {
		super(errorMessage, code);
	}
	
}