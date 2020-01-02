package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
//kkjjkk
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		MainGUI mainGUI = new MainGUI();
		mainGUI.display();
	}

	public static void main(String[] args) {
		launch(args);
	}
}


//Tid-((startnummer-1)*30) = enskild tid