package application;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MyButton extends Button {
	
	//--Variables--
	private Skier selectedItem;
	
	//--Constructors
	public MyButton() {
		
	}
	
	//--Methods--
	public void delete(TableView<Skier> table) {
		this.selectedItem = table.getSelectionModel().getSelectedItem();
		table.getItems().remove(selectedItem);
	}
	
	public void update(TableView<Skier> table, TextField textName, TextField textStartNumber) {
		this.selectedItem = table.getSelectionModel().getSelectedItem();
		String tempString = textStartNumber.getText();
		int tempInt = Integer.parseInt(tempString);
		
		Skier skier = new Skier(textName.getText(), tempInt);
		table.getItems().remove(selectedItem);
		table.getItems().add(skier);
		clearText(textName, textStartNumber);
	}//update
	
	public void add(TableView<Skier> table, ObservableList<Skier> obList, TextField textName, TextField textStartNumber) {
		String tempString = textStartNumber.getText();
		int tempInt = Integer.parseInt(tempString);
		
		Skier skier = new Skier(textName.getText(), tempInt);
		obList.addAll(skier);
		table.setItems(obList);
		table.refresh();
		clearText(textName, textStartNumber);
	}//add
	
	public void clearText(TextField textName, TextField textStartNumber) {
		textName.clear();
		textStartNumber.clear();
	}//clearText
}//class