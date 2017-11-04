package database;

public class RMUser {
	
	private String firstName;
	private String lastName;
	private String email;
	private int positiveRatings;
	private int negativeRatings;
	private int totalRatings;
	
	public RMUser(String firstName, String lastName, String email, int positiveRatings, int negativeRatings, int totalRatings) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.positiveRatings = positiveRatings;
		this.negativeRatings = negativeRatings;
		this.totalRatings = totalRatings;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public int getPositiveRatings() {
		return positiveRatings;
	}
	
	public int getNegativeRatings() {
		return negativeRatings;
	}
	
	public int getTotalRatings() {
		return totalRatings;
	}
	
	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}
	
}
