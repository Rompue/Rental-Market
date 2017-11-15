package database;

public class RMUser {
	
	private int userID;
	private String firstName;
	private String lastName;
	private String email;
	private int positiveRatings;
	private int negativeRatings;
	private int totalRatings;
	private String image;
	
	public RMUser(int userID, String firstName, String lastName, String email, int positiveRatings, int negativeRatings, int totalRatings, String image) {
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.positiveRatings = positiveRatings;
		this.negativeRatings = negativeRatings;
		this.totalRatings = totalRatings;
		this.image = image;
	}
	
	public int getUserID() {
		return userID;
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
	
	public int getPositivePercentage() {
		if(totalRatings == 0) {
			return 0;
		}
		return (positiveRatings / totalRatings) * 100;
	}
	
	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}
	
	public String getImage() {
		return image;
	}
	
}
