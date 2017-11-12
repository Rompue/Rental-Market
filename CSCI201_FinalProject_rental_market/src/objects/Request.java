package objects;

import java.util.List;

public class Request {
	private int id;
	
	private String itemName;
	private String itemDescription;
	private User requester;
	private User lender;
	
	
	private String createdDate;
	private String completedDate;
	
	
	private String amountOfMoney;
	private String neededDate;
	private String returnDate;
	private String fullRequestString; //$2 need before 10/28 return before 10/31
	
	private int statusNumber; // 1 for pending, 2 for ongoing, 3 for completed
	private String status; 
	private List<Chat> chats;
	
	private String ratingString;
	
	public Request() {
		// TODO Auto-generated constructor stub
	}
	
}
