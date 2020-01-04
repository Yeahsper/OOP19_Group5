package application;

import java.text.SimpleDateFormat;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

/**
 * Class that handles the Timer.
 * @author Jesper
 *
 */
public class AniTimer extends AnimationTimer {

	//--Variables--
	private long timestamp;
	private long fraction;
	private Label labelTime;
	private long time;

	private SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss.S");

	//--Constructors--
	
    public AniTimer() { 	
	}//empty constructor

	
	public AniTimer(Label labelTime){
    this.labelTime = labelTime;

	}


	//--Methods--
    
	@Override
	public void start() {
		timestamp = System.currentTimeMillis() - fraction;
		super.start();
	}//start

	@Override
	public void stop() {
		super.stop();
		fraction = System.currentTimeMillis() - timestamp;
	}//stop

	//Handles how often the timer updates and updates the label and formats it
	@Override
	public void handle(long now) {
        long newTime = System.currentTimeMillis();
        if (timestamp <= newTime) {
            long deltaT = (newTime - timestamp);
            time += deltaT;
            timestamp +=  deltaT;

            String strTime = timeFormat.format(time);
            labelTime.setText(strTime.substring(0, strTime.length() - 2));
        }
    }//handle

	public void reset() {
		time = 0;
	}//reset


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

	public long getTime() {
		return time;
	}

	public void setTimeFormat(SimpleDateFormat timeFormat) {
		this.timeFormat = timeFormat;
	}
}