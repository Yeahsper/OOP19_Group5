package application;

import java.text.SimpleDateFormat;

public class Skier {

	private String name;
	private int startNumber;
	private int finishNumber;
	private long time;
	private String parsedTime;
	private long differenceToFirst;
	SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
	
	public Skier() {
		
	}

	
	
	public Skier(String fullName, int startNumber) {
		this.name = fullName;
		this.startNumber = startNumber;
	}
	
	public Skier(String name, int startNumber, Long time) {
		this.name = name;
		this.startNumber = startNumber;
		this.time = time;
	}
	
	public Skier(String name, int startNumber, String parsedTime) {
		this.name = name;
		this.startNumber = startNumber;
		this.parsedTime = parsedTime;
	}



	public String getFullName() {
		return name;
	}

	public void setFullName(String fullName) {
		this.name = fullName;
	}

	public int getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
        return sdf.format(time);
    }

	public void setTime(Long time) {
		this.time = time;
	}
	
    public String getDifferenceToFirst() {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
        return sdf.format(time);
    }

    



	public String getParsedTime() {
		return parsedTime;
	}



	public void setTime(long time) {
		this.time = time;
	}



	public void setParsedTime(String parsedTime) {
		this.parsedTime = parsedTime;
	}
	
    
    
}
