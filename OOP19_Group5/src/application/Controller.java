package application;

import javafx.collections.ObservableList;
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
	
	public void add(TableView<Skier> table, ObservableList<Skier> obList, TextField textName, TextField textStartNumber) {
		Skier skier;

		if((textName.getText().trim().isEmpty())) {
			MyAlert.showInfo("The \"name\" field is empty");
		}else {
			if(selectedStartNumber > 0) {
				skier = new Skier(textName.getText(), selectedStartNumber);
				selectedStartNumber = 0;
			}else{
				skier = new Skier(textName.getText(), startInt);
			}

			textStartNumber.setText((startInt+1)+"");
			obList.addAll(skier);
			table.setItems(obList);
			table.refresh();
			textName.clear();
			startInt++;

		}
	}//add
	
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

	

	public void select(TableView<Skier> table, ObservableList<Skier> obList, Label lblSelectedStartNr, Label lblSelectedName, Label lblSign, Label lblDifferenceToLeader, long selectedStart, int selectedStartNumber, long time) {
		lblSelectedStartNr.setText("");
		lblSelectedName.setText("");
		lblSign.setText("");
		lblDifferenceToLeader.setText("");

		String parsedTime = getParsedTime(selectedStartNumber, selectedStart, time);
		split.split(table, obList, parsedTime);

		lblSelectedStartNr.setText(Integer.toString(split.getSelectedStartNumber()));
		lblSelectedName.setText(split.getSelectedName());
	}//select
	
	public void split(TableView<Skier> table, ObservableList<Skier> obList, Label lblLeader, Label lblNameLeader, Label lblBestTime, Label lblSelectedStartNr, Label lblSelectedName, Label lblSign, Label lblDifferenceToLeader, long selectedStart, int selectedStartNumber, long time) {
		lblSelectedStartNr.setText("");
		lblSelectedName.setText("");
		lblSign.setText("");
		lblDifferenceToLeader.setText("");
		lblLeader.setText("");
		lblNameLeader.setText("");
		lblBestTime.setText("");
		
		System.out.println(selectedStart);
		
		split.calculateSkiersTime(time, selectedStart, selectedStartNumber);
		split.compare(split.getSkiersTime(), split.getBestTime());
		String parsedTime = getParsedTime(selectedStartNumber, selectedStart, time);
		goal(table, obList, parsedTime);

		// Update hBox4 if no skier has passed
		if (split.getSkiersPassed() == 1) {
			split.setSkiersPassed(2);
			lblLeader.setText("Leader");
			lblNameLeader.setText(split.getStrNameLeader());
			lblBestTime.setText(split.getStrTimeLeader());
		}

		// Update hBox3 if skier doesn't have the best time so far
		if (split.getBestTime() < split.getSkiersTime()) {
			lblSelectedStartNr.setText(String.valueOf(selectedStartNumber));
			lblSelectedName.setText(split.getSelectedName());
			lblSign.setText(split.getSign());
			lblDifferenceToLeader.setText(split.getStrDifference());
		}

		// Update hBox3 and 4 if skier has the best time
		else {
			lblSelectedStartNr.setText(String.valueOf(selectedStartNumber));
			lblSelectedName.setText(split.getSelectedName());
			lblSign.setText(split.getSign());
			lblDifferenceToLeader.setText(split.getStrDifference());
			lblLeader.setText("Leader");
			lblNameLeader.setText(split.getStrNameLeader());
			lblBestTime.setText(split.getStrBestTime());
		}
	}//split
	
}//class