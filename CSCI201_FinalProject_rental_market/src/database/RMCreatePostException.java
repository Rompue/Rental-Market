package database;

public class RMCreatePostException extends RMException {
	
	/*
	 * Error codes
	 * 0 - no error (though no exception would have been thrown in the first place)
	 * 1 - itemName is empty
	 * 2 - postDescription is null
	 * 3 - borrowAmount is empty
	 * 4 - dueDate is null
	 */
	
	public RMCreatePostException(String errorMessage, int code) {
		super(errorMessage, code);
	}
	
}
