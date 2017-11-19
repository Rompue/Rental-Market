package database;

public class RMCreateRequestException extends RMException {
	
	/*
	 * Error codes
	 * 0 - no error (though no exception would have been thrown in the first place)
	 * 1 - itemName is empty
	 * 2 - dueDate is null
	 * 3 - no user has this borrower email
	 */
	
	public RMCreateRequestException(String errorMessage, int code) {
		super(errorMessage, code);
	}
	
}