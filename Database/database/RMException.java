package database;

public class RMException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	private String errorMessage;
	
	public RMException(String errorMessage, int errorCode) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String getMessage() {
		return errorMessage;
	}
	
	public int getCode() {
		return errorCode;
	}

}
