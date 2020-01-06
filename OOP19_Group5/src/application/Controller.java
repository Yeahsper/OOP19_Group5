package application;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Class that handles all the JavaFX buttons
 * @author Jesper
 *
 */
public class Controller {

    //--Variables--
	private Skier selectedItem;
	private String selectedName;
	private int selectedStartNumber;
	private Skier skier;
	private int startInt = 1;
	private AniTimer timer;
	private Split split = new Split();
	
	//--Constructors
	public Controller() {

	}

	//--Methods--
	
	/**
	 * Method to add new Skiers.
	 * @param table Which Tableview to use.
	 * @param obList Which ObservableList to use.
	 * @param textName Which TextField to use for the name.
	 * @param textStartNumber Which TextField to use for the starting number.
	 */
	public void add(TableView<Skier> table, ObservableList<Skier> obList, TextField textName, TextField textStartNumber) {
		Skier skier;
		String startNumberString = textStartNumber.getText();
		int startNumber = Integer.parseInt(startNumberString);
		if((textName.getText().trim().isEmpty())) {
			MyAlert.showInfo("The \"name\" field is empty");
		}else {
			if(selectedStartNumber > 0) {
				skier = new Skier(textName.getText(), selectedStartNumber);
				selectedStartNumber = 0;
				textStartNumber.setText((startInt+1)+"");
			}else{
				skier = new Skier(textName.getText(), startNumber);
				textStartNumber.setText((startNumber+1)+"");
			}

			
			obList.addAll(skier);
			table.setItems(obList);
			table.refresh();
			textName.clear();
			startInt++;

		}
	}//add
	
	/**
	 * Method to delete a user. As long as the startnumber !<= 1, then also decrease that with 1.
	 * @param table Which Tableview to use.
	 * @param obList Which ObservableList to use.
	 * @param startNumberField Which TextField to use for the starting number.
	 */
	public void delete(TableView<Skier> table, ObservableList<Skier> obList, TextField startNumberField) {
		try {
			this.selectedItem = table.getSelectionModel().getSelectedItem();
			selectedStartNumber = table.getSelectionModel().getSelectedItem().getStartNumber();
			if(!(startInt <= 1)) {
				startInt--;
			}
			startNumberField.setText(""+selectedStartNumber);
		}catch(NullPointerException e) {
			System.out.println("You didnt choose an item in the table view");
		}

		table.getItems().remove(selectedItem);
	}//delete


	/**
	 * Method to run whenever a Skier reaches the goal.
	 * @param table Which Tableview to use.
	 * @param obList Which ObservableList to use.
	 * @param time Which time to use to set skiers time.
	 */
	public void goal(TableView<Skier> table, ObservableList<Skier> obList, String time) {
		try {
			this.selectedItem = table.getSelectionModel().getSelectedItem();
			selectedName = table.getSelectionModel().getSelectedItem().getName();
			selectedStartNumber = table.getSelectionModel().getSelectedItem().getStartNumber();


			skier = new Skier(selectedName, selectedStartNumber, time);
			obList.addAll(skier);
			table.setItems(obList);
			table.getItems().remove(selectedItem);
			table.refresh();
		}catch(NullPointerException e) {
			System.out.println("You didn't choose an item in the tableview");
		}
	}//goal



	/**
	 * Method that parses the time from a Long to a String
	 * @param selectedStartNumber Used to know which Skier to change.
	 * @param selectedStart Used to know what timing-system to use (Mass-start or Individual start)
	 * @param raceTimer Used to calculate the chosen skiers individual time. raceTimer starts when
	 *                  the user click "Start race" and stop when the user click "Stop race"
	 * @return the parsed time as String.
	 */
	public String getParsedTime( int selectedStartNumber, long selectedStart, long raceTimer) {

		long index = selectedStart * (selectedStartNumber - 1);
		long skiersTime = raceTimer - index;

		int tenthOfSeconds  = (int) (skiersTime / 100) % 10;
		String strTenthOfSeconds = (String.format("%01d",tenthOfSeconds));

		int seconds = (int) (skiersTime / 1000) % 60 ;
		String strSeconds = (String.format("%02d", seconds));

		int minutes = (int) ((skiersTime / (1000*60)) % 60);
		String strMinutes = (String.format("%02d", minutes));

		int hours   = (int) ((skiersTime / (1000*60*60)) % 24);
		String strHours = (String.format("%02d", hours));


		String parsedTime = strHours.concat(":").concat(strMinutes).concat(":").concat(strSeconds.concat(".").concat(strTenthOfSeconds));

		return parsedTime;
	}//getParsedTime

	
	/**
	 * Method to select a Skier in the TableView.
	 * All lbl(Labels) are used by MainGUI to present information about the selected skier
	 * @param table TableView that holds information about each skier such as name and startnumber.
	 * @param obList Used to get the necessary information about the skier you´ve chosen
	 * @param lblSelectedStartNr Used to show the chosen skiers startnumber
	 * @param lblSelectedName Used to show the name of the skier
	 * @param lblSign Shows whether the skier is ahead (-) or behind (+) the current fastest time
	 * @param lblDifferenceToLeader Shows the difference in time to the current fastest time.
	 * @param selectedStart Used when calculate the skiers individual time. If type of start is individual30, selectedStart = 30000ms otherwise 0
	 * @param selectedStartNumber Skiers startnumber.
	 * @param time Total racetime.
	 */
	public void select(TableView<Skier> table, ObservableList<Skier> obList, Label lblSelectedStartNr, Label lblSelectedName, Label lblSign, Label lblDifferenceToLeader, long selectedStart, int selectedStartNumber, long time) {
        lblSelectedStartNr.setText("");
        lblSelectedName.setText("");
        lblSign.setText("");
        lblDifferenceToLeader.setText("");
        setSelectedStartNumber(selectedStartNumber);

        String parsedTime = getParsedTime(selectedStartNumber, selectedStart, time);
        split.split(table, obList, parsedTime, selectedStartNumber);


        lblSelectedStartNr.setText(Integer.toString(selectedStartNumber));
        lblSelectedName.setText(split.getSelectedName());
	}//select
	
	/**
	 * Split calculates the split-time for the chosen skier and presents the result to the user in MainGUI.
	 * @param table TableView that holds information about each skier such as name and startnumber.
	 * @param obList Used to get the necessary information about the skier you´ve chosen
	 * @param lblLeader Just prints out the text "Leader"
	 * @param lblNameLeader Name of current leader
	 * @param lblBestTime Shows current best time
	 * @param lblSelectedStartNr Startnumber of the chosen skier
	 * @param lblSelectedName Name of chosen skier
	 * @param lblSign Shows whether the skier is ahead (-) or behind (+) the current fastest time
	 * @param lblDifferenceToLeader Shows the difference in time to the current fastest time.
	 * @param selectedStart Equals 30000 if individual start is chosen, otherwise 0
	 * @param selectedStartNumber Holds the startnumber of the selected skier
	 * @param time Total racetime
	 */
	public void split(TableView<Skier> table, ObservableList<Skier> obList, Label lblLeader, Label lblNameLeader, Label lblBestTime, Label lblSelectedStartNr, Label lblSelectedName, Label lblSign, Label lblDifferenceToLeader, long selectedStart, int selectedStartNumber, long time) {

        split.calculateSkiersTime(time, selectedStart, selectedStartNumber);
        split.compare(split.getSkiersTime(), split.getBestTime());
        String parsedTime = getParsedTime(selectedStartNumber, selectedStart, time);
        goal(table, obList, parsedTime);

        // Update hBox4 if no skier has passed
        if (split.getSkiersPassed() == 1) {
            lblSign.setText(split.getSign());
            lblLeader.setText("Leader");
            lblNameLeader.setText(split.getStrNameLeader());
            lblBestTime.setText(split.getStrTimeLeader());
            split.setSkiersPassed(2);
        }

        // Update hBox3 if skier doesn't have the best time so far
        if (split.getBestTime() < split.getSkiersTime()) {
            lblSelectedStartNr.setText(String.valueOf(selectedStartNumber));
            lblSelectedName.setText(split.getSelectedName());
            lblSign.setText("+");
            lblDifferenceToLeader.setText(split.getStrDifference());
         }

        // Update hBox3 and 4 if skier has the best time
        else {
            lblSelectedStartNr.setText(String.valueOf(selectedStartNumber));
            lblSelectedName.setText(split.getSelectedName());
            lblSign.setText("-");
            lblDifferenceToLeader.setText(split.getStrDifference());
            lblLeader.setText("Leader");
            lblNameLeader.setText(split.getStrNameLeader());
            lblBestTime.setText(split.getStrBestTime());
        }
    }//split


	
	/**
	 * Enables or disables JavaFX buttons.
	 * @param btnAdd
	 * @param btnDelete
	 * @param btnSaveList
	 * @param btnGetList
	 * @param btnInd30
	 * @param btnMass
	 * @param bool
	 */
	public void activeButtons(Button btnAdd, Button btnDelete, Button btnSaveList, Button btnGetList, Button btnInd30, Button btnMass, boolean bool) {
		btnAdd.setDisable(bool);
		btnDelete.setDisable(bool);
		btnSaveList.setDisable(bool);
		btnGetList.setDisable(bool);
		btnInd30.setDisable(bool);
		btnMass.setDisable(bool);
	}//activeButtons
	
	
    // Getter and setters
    private void setSelectedStartNumber(int selectedStartNumber) { this.selectedStartNumber = selectedStartNumber; }
    public int getSelectedStartNumber() { return selectedStartNumber; }
}//class