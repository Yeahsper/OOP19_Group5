package application;

public class Skier {
    private String firstName;
    private String lastName;
    private int startNumber;
    private String fullName;

    public Skier(){
        this.firstName = "";
        this.lastName = "";
        this.startNumber = 0;
        this.fullName = "";
    }
    public Skier(String firstName, String lastName, int startNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public  String getFullName() {
        fullName = firstName.concat(" ").concat(lastName);
        return fullName;
    }
}
