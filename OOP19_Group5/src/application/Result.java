package application;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Result {

	//--Variables--
    private final LocalDateTime date;
    private final long time;
    Skier skier;

    //--Constructor--
    public Result(LocalDateTime date, long time, Skier skier) {
        this.date = date;
        this.time = time;
        this.skier = skier;
    }
    
    //--Getters & setters--
    public String getDate() {
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm:ss");
        return date.format(sdf);
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
        return sdf.format(time);

    }

}
