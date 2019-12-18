package application;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

public class AniTimer extends AnimationTimer {
	
	//--Variables--
	private long timestamp;
	private long fraction;
	private final Label labelTime;
	private long time;
	private SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss:SSS");

	
	//--Constructors--
	public AniTimer(Label labelTime) {
		this.labelTime = labelTime;
	}



	//--Methods--
	//Starts the timer
	@Override
	public void start() {
		timestamp = System.currentTimeMillis() - fraction;
		super.start();
	}
	
	//Stops the timer
	@Override
	public void stop() {
		super.stop();
		fraction = System.currentTimeMillis() - timestamp;
	}


	//Handles how often the timer updates and updates the label to it and formats it
	@Override
	public void handle(long now) {
		long newTime = System.currentTimeMillis();
		if (timestamp <= newTime) {
			long deltaT = (newTime - timestamp);
			time += deltaT;
			timestamp += deltaT;
			labelTime.setText(timeFormat.format(time));
		}
	}
	
	
    public void reset() {
        time = 0;
    }
	
	
	//-- Getters & setters --
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getFraction() {
		return fraction;
	}

	public void setFraction(long fraction) {
		this.fraction = fraction;
	}


	public void setTime(long time) {
		this.time = time;
	}

	public SimpleDateFormat getTimeFormat() {
		return timeFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.timeFormat = dateFormat;
	}

	public Label getLabelTime() {
		
		return labelTime;
	}
	
    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.S");
        return sdf.format(time);

    }
	
}
