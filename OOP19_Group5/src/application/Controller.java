package application;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Controller {

	//--Variables--
	private Skier selectedItem;
	private String selectedName;
	private int selectedStartNumber;
	Skier skier;
	int startInt = 1;
	//--Constructors
	public Controller() {

	}

	//--Methods--
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


	}


	public void goal(TableView<Skier> table, ObservableList<Skier> obList, Long time) {
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


	}

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
}//class