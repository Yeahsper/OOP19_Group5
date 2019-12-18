package application;

public class Skier {

	private String firstName;
	private String lastName;
	private String fullName;
	private int startNumber;
	private AniTimer timer;
	
	public Skier() {
		
	}

	
	
	public Skier(String fullName, int startNumber) {
		super();
		this.fullName = fullName;
		this.startNumber = startNumber;
	}



	public Skier(String firstName, String lastName, int startNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.startNumber = startNumber;
	}



	public Skier(String firstName, String lastName, String fullName, int startNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.startNumber = startNumber;
	}



	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getFullName() {
		return fullName;
	}



	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
	}
	
	
}
