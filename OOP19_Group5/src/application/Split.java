package application;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class Split {
    private Skier selectedItem;
    private String selectedName;
    private int selectedStartNumber;
    private String difference;
    private String sign;
    Skier skier;
    int startInt = 1;
    private long timeLeader = 9999;

    public Split () {

    }

    public void split(TableView<Skier> table, ObservableList<Skier> obList, String time) {
        try {
            this.selectedItem = table.getSelectionModel().getSelectedItem();
            selectedName = table.getSelectionModel().getSelectedItem().getName();
            selectedStartNumber = table.getSelectionModel().getSelectedItem().getStartNumber();


            skier = new Skier(selectedName, selectedStartNumber, time);

        }catch(NullPointerException e) {
            System.out.println("You didn't choose an item in the tableview");
        }


    }
    public String getSelectedName (){
        return selectedName;
    }

    public int getSelectedStartNumber () { return selectedStartNumber;}

    public String getSplitTime( int selectedStartNumber, long selectedStart, long raceTimer, Label currentLeader) {
        String splitTime = "";
        String strTenthOfSeconds = "";
        String strSeconds = "";
        String strMinutes = "";
        String strHours = "";

        long time = compare(raceTimer, timeLeader);

        long index = selectedStart * (selectedStartNumber - 1);
        long skiersTime = raceTimer - index;

        if (skiersTime < time){
            sign = "-";
        }
        else {
            sign = "+";
        }

        if (skiersTime < 0)
            skiersTime = skiersTime * (-1);

        int tenthOfSeconds  = (int) (skiersTime / 100) % 10;
        int seconds = (int) (skiersTime / 1000) % 60 ;
        int minutes = (int) ((skiersTime / (1000*60)) % 60);
        int hours   = (int) ((skiersTime / (1000*60*60)) % 24);

        if(tenthOfSeconds > 0) {
            strTenthOfSeconds = (String.format("%01d", tenthOfSeconds));
            splitTime = strTenthOfSeconds;

        }
        if(seconds > 0) {
            strSeconds = (String.format("%02d", seconds));
            splitTime = strSeconds.concat(".").concat(strTenthOfSeconds);

        }
        if(minutes > 0) {
            strMinutes = (String.format("%02d", minutes));
            splitTime = (strMinutes).concat(":").concat(strSeconds.concat(".").concat(strTenthOfSeconds));

        }

        if(hours > 0) {
            strHours = (String.format("%02d", hours));
            splitTime = strHours.concat(":").concat(strMinutes).concat(":").concat(strSeconds.concat(".").concat(strTenthOfSeconds));

        }





        return splitTime;
    }

    public String getSign() {
        return sign;
    }

    public long compare(long time, long timeLeader) {

        if (timeLeader == 9999) {
            timeLeader = time;
        }
        else {
            if (time < timeLeader) {

            }
        }


        return time;
    }
}