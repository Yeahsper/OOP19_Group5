package application;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * Class that handles the split times.
 * @author Jesper
 *
 */
public class Split {

    //--Variables--
    private long timeLeader;
    private long difference;
    private long index;
    private long bestTime;
    private long skiersTime;

    private int selectedStartNumber;
    private int skiersPassed;

    private String strSkiersTime;
    private String strTimeLeader;
    private String strNameLeader;
    private String strDifference;
    private String strBestTime;
    private String sign;
    private Skier selectedItem;
    private String selectedName;
    private String minus = "minus";
    private String plus = "plus";
    private String none =" ";
    private String dsq = "DSQ";
    Skier skier;

    // Constructors
    public Split () {
    }


    // --Methods--

    /**
     * Gets a split time from the selected object in a TableView.
     * @param table
     * @param obList
     * @param time
     */
    public void split(TableView<Skier> table, ObservableList<Skier> obList, String time, int selectedStartnumber) {
        try {
            this.selectedItem = table.getSelectionModel().getSelectedItem();
            selectedName = table.getSelectionModel().getSelectedItem().getName();
            this.selectedStartNumber = selectedStartnumber;
            skier = new Skier(selectedName, selectedStartNumber, time);
            this.sign = " ";

        }catch(NullPointerException e) {
            System.out.println("You didn't choose an item in the tableview");
        }
    }



    /**
     * Takes the difference between the current best time
     * and the chosen skier. Saves the result as a string
     * @param difference
     */
    private void setStrDifference(long difference){

        strDifference = "";
        String strTenthOfSeconds = "";
        String strSeconds = "";
        String strMinutes = "";
        String strHours = "";

        if (difference < 0)
            difference = difference * (-1);

        int tenthOfSeconds  = (int) (difference / 100) % 10;
        int seconds = (int) (difference / 1000) % 60 ;
        int minutes = (int) ((difference / (1000*60)) % 60);
        int hours   = (int) ((difference / (1000*60*60)) % 24);

        if(tenthOfSeconds > 0) {
            strTenthOfSeconds = (String.format("%01d", tenthOfSeconds));
            strDifference = strTenthOfSeconds;
        }

        if(seconds > 0) {
            strSeconds = (String.format("%02d", seconds));
            strDifference = strSeconds.concat(".").concat(strTenthOfSeconds);
        }

        if(minutes > 0) {
            strMinutes = (String.format("%02d", minutes));
            strDifference = (strMinutes).concat(":").concat(strSeconds.concat(".").concat(strTenthOfSeconds));
        }

        if(hours > 0) {
            strHours = (String.format("%02d", hours));
            strDifference = strHours.concat(":").concat(strMinutes).concat(":").concat(strSeconds.concat(".").concat(strTenthOfSeconds));
        }
    }

    /**
     * Saves the current best time as a string
     * @param skiersTime
     */
    private void setStrBestTime(long skiersTime) {

        String strTenthOfSeconds = "";
        String strSeconds = "";
        String strMinutes = "";
        String strHours = "";

        if (skiersTime < 0)
            skiersTime = skiersTime * (-1);

        int tenthOfSeconds  = (int) (skiersTime / 100) % 10;
        int seconds = (int) (skiersTime / 1000) % 60 ;
        int minutes = (int) ((skiersTime / (1000*60)) % 60);
        int hours   = (int) ((skiersTime / (1000*60*60)) % 24);

        if(tenthOfSeconds > 0) {
            strTenthOfSeconds = (String.format("%01d", tenthOfSeconds));
            strBestTime = strTenthOfSeconds;
        }

        if(seconds > 0) {
            strSeconds = (String.format("%02d", seconds));
            strBestTime = strSeconds.concat(".").concat(strTenthOfSeconds);
        }

        if(minutes > 0) {
            strMinutes = (String.format("%02d", minutes));
            strBestTime = (strMinutes).concat(":").concat(strSeconds.concat(".").concat(strTenthOfSeconds));
        }

        if(hours > 0) {
            strHours = (String.format("%02d", hours));
            strBestTime = strHours.concat(":").concat(strMinutes).concat(":").concat(strSeconds.concat(".").concat(strTenthOfSeconds));
        }

        setStrTimeLeader(strBestTime);
    }


    /**
     * Calculates skiers time if selected start = individual 30.
     * @param time
     * @param selectedStart
     * @param selectedStartNumber
     */
    public void calculateSkiersTime(long time, long selectedStart, int selectedStartNumber) {

        strSkiersTime = "";
        String strTenthOfSeconds = "";
        String strSeconds = "";
        String strMinutes = "";
        String strHours = "";

        long index = selectedStart * (selectedStartNumber - 1);
        long skiersTime = time - index;

        setSkiersTime(skiersTime);
        if (skiersTime < 0) skiersTime = skiersTime * (-1);

        if (skiersPassed == 0) {
            setBestTime(skiersTime);
            setSkiersPassed(1);
        }



        int tenthOfSeconds  = (int) (skiersTime / 100) % 10;
        int seconds = (int) (skiersTime / 1000) % 60 ;
        int minutes = (int) ((skiersTime / (1000*60)) % 60);
        int hours   = (int) ((skiersTime / (1000*60*60)) % 24);

        if(tenthOfSeconds > 0) {
            strTenthOfSeconds = (String.format("%01d", tenthOfSeconds));
            strSkiersTime = strTenthOfSeconds;
        }

        if(seconds > 0) {
            strSeconds = (String.format("%02d", seconds));
            strSkiersTime = strSeconds.concat(".").concat(strTenthOfSeconds);
        }

        if(minutes > 0) {
            strMinutes = (String.format("%02d", minutes));
            strSkiersTime = (strMinutes).concat(":").concat(strSeconds.concat(".").concat(strTenthOfSeconds));
        }

        if(hours > 0) {
            strHours = (String.format("%02d", hours));
            strSkiersTime = strHours.concat(":").concat(strMinutes).concat(":").concat(strSeconds.concat(".").concat(strTenthOfSeconds));
        }
        setStrSkiersTime(strSkiersTime);

    }

    /**
     * Compares skiers time with current best time
     * @param skiersTime
     * @param bestTime
     */
    public void compare(long skiersTime, long bestTime) {

        if (skiersTime < bestTime && skiersTime > 0) { // If skier has the best time
            System.out.println("I första if");
            System.out.println("SkierTime = " + getSkiersTime() + ", bestTime = " + getBestTime() + " och skiersPassed = " + getSkiersPassed());
            setSign(minus);
            setNameLeader(selectedName);
            setBestTime(skiersTime);
            setStrBestTime(skiersTime);
            setDifference(skiersTime, bestTime);
            setStrDifference(getDifference());
            System.out.println("Sign = " + getSign());
            System.out.println("strDifference = " + getStrDifference());
            setTimeLeader(skiersTime);
        }

        if (getSkiersPassed() == 1){ // if skier is the first one to pass
            System.out.println("I andra if");
            System.out.println("SkiersPassed = " + getSkiersPassed());
            setNameLeader(selectedName);
            setBestTime(skiersTime);
            setStrBestTime(skiersTime);
            setDifference(skiersTime, bestTime);
            setStrDifference(getDifference());
            setTimeLeader(skiersTime);
            setSign(none);

        }
        else {
            System.out.println("I else: ");
            System.out.println("SkierTime = " + getSkiersTime() + ", bestTime = " + getBestTime() + " och skiersPassed = " + getSkiersPassed());
            setSign(plus);
            setDifference(skiersTime, bestTime);
            setStrDifference(getDifference());
        }
    }

    //TODO: Show individual timer when chosen skier
    /**   public String showSkiersTime(long time, long selectedStart, int selectedStartNumber) {

     strSkiersTime = "";
     String strTenthOfSeconds = "";
     String strSeconds = "";
     String strMinutes = "";
     String strHours = "";

     long index = selectedStart * (selectedStartNumber - 1);
     long skiersTime = time - index;

     setSkiersTime(skiersTime);

     if (skiersPassed == 0) {
     setBestTime(skiersTime);
     setSkiersPassed(1);
     }

     int tenthOfSeconds  = (int) (skiersTime / 100) % 10;
     int seconds = (int) (skiersTime / 1000) % 60 ;
     int minutes = (int) ((skiersTime / (1000*60)) % 60);
     int hours   = (int) ((skiersTime / (1000*60*60)) % 24);

     if(tenthOfSeconds > 0) {
     strTenthOfSeconds = (String.format("%01d", tenthOfSeconds));
     strSkiersTime = strTenthOfSeconds;
     }

     if(seconds > 0) {
     strSeconds = (String.format("%02d", seconds));
     strSkiersTime = strSeconds.concat(".").concat(strTenthOfSeconds);
     }

     if(minutes > 0) {
     strMinutes = (String.format("%02d", minutes));
     strSkiersTime = (strMinutes).concat(":").concat(strSeconds.concat(".").concat(strTenthOfSeconds));
     }

     if(hours > 0) {
     strHours = (String.format("%02d", hours));
     strSkiersTime = strHours.concat(":").concat(strMinutes).concat(":").concat(strSeconds.concat(".").concat(strTenthOfSeconds));
     }
     // setStrSkiersTime(strSkiersTime);
     return strSkiersTime;
     } **/

    // --Getters--
    public String getSelectedName (){ return selectedName; }

    public int getselectedStartNumber () { return selectedStartNumber; }

    public long getBestTime() { return bestTime; }

    public String getStrTimeLeader() { return strTimeLeader; }

    public long getTimeLeader() { return timeLeader; }

    public String getStrBestTime() { return strBestTime; }

    public long getSkiersTime() { return skiersTime; }

    public String getStrNameLeader() { return strNameLeader; }

    public String getSign() { return sign; }

    public long getDifference() { return difference; }

    public String getStrDifference() { return strDifference; }

    public int getSkiersPassed() {return skiersPassed; }


    // --Setters--
    public void setSign(String sign){
        if (sign.equals(minus)) this.sign = "-";
        if (sign.equals(plus)) this.sign = "+";
    }

    private void setBestTime(long bestTime) { this.bestTime = bestTime; }

    private void setStrSkiersTime(String strSkiersTime) { this.strSkiersTime = strSkiersTime; }

    private void setSkiersTime(long skiersTime) { this.skiersTime = skiersTime; }

    private void setNameLeader(String selectedName) { this.strNameLeader = selectedName; }

    private void setTimeLeader(long timeLeader) { this.timeLeader = timeLeader; }

    void setSkiersPassed(int i) { skiersPassed = i; }

    private void setStrTimeLeader(String strBestTime) { this.strTimeLeader = strBestTime; }

    private void setDifference(long skiersTime, long bestTime) {
        if (skiersTime < bestTime)
            difference = bestTime - skiersTime;
        else
            difference = skiersTime - bestTime;
    }
} // Class
