package database;

public class RMRespondToRequestException extends RMException {
	
	/*
	 * Error codes
	 * 0 - no error (though no exception would have been thrown in the first place)
	 * 1 - this request was not offered to a post
	 */
	
	public RMRespondToRequestException(String errorMessage, int code) {
		super(errorMessage, code);
	}

}
