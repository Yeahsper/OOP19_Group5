package application;

import java.text.SimpleDateFormat;
import java.util.regex.MatchResult;

/**
 * Class for the Skier objects.
 * @author Jesper
 *
 */
public class Skier {

	private String name;
	private int startNumber;
	private long time;
	private long differenceToFirst;
	private String parsedTime;
	private AniTimer timer;

	// Constructors
	public Skier() {
	}

	public Skier(String fullName, int startNumber) {
		super();
		this.name = fullName;
		this.startNumber = startNumber;
	}

//	TODO: Start individual timer
	/**  
	public Skier(String fullName, int startNumber, Anitimer timer) {
		super();
		this.name = fullName;
		this.startNumber = startNumber;
		this.timer = timer;
	} 
	**/

	public Skier(String name, int startNumber, long time) {
		super();
		this.name = name;
		this.startNumber = startNumber;
		this.time = time;

	}

	public Skier(String name, int startNumber, String parsedTime) {
		this.name = name;
		this.startNumber = startNumber;
		this.parsedTime = parsedTime;
	}

	// Getters
	public String getFullName() { return name; }

	public int getStartNumber() { return startNumber; }

	public String getName() { return name; }

	public String getParsedTime() { return parsedTime; }

	public String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
		return sdf.format(time);
	}
	public String getDifferenceToFirst() {
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
		return sdf.format(time);
	}

	// Setters
	public void setFullName(String fullName) {
		this.name = fullName;
	}

	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
	}

	public void setDifferenceToFirst(long differenceToFirst) {
		this.differenceToFirst = differenceToFirst;
	}

	public void setParsedTime(String parsedTime) {
		this.parsedTime = parsedTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTime(long time) {
		this.time = time;
	}

/** TODO start individual timer
	public AniTimer getTimer() {
		return timer;
	}

	public void startTimer() {
		timer.start();
	}

	public void setTimer(AniTimer timer) {
		this.timer = timer;
	} **/
} // Class