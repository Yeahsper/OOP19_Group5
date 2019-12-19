package application;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI {

	TableView <Skier> table = new TableView<>();
	List<Skier> arrList = new ArrayList<Skier>();
	ObservableList<Skier> obList = FXCollections.observableList(arrList);
	Skier skier = new Skier();
	TextField nameInput, startNumberInput;
	Controller myButton = new Controller();
	boolean running;

	Label lblDifference = new Label();
	AniTimer timer = new AniTimer(lblDifference);

	public MainGUI() {

	}


	public void display() {
		Stage primaryStage = new Stage();

		try {
			/** Kolumner **/
			// NamnKolumn
			TableColumn<Skier, String> fullNameColumn = new TableColumn<>("Name");
			fullNameColumn.setMinWidth(170);
			fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

			// Startnummer
			TableColumn<Skier, Integer> startNumberColumn = new TableColumn<>("Startnumber");
			startNumberColumn.setMinWidth(100);
			startNumberColumn.setCellValueFactory(new PropertyValueFactory<>("startNumber"));

			// Differens
			TableColumn<Skier, Skier> timeColumn = new TableColumn<>("Time");
			timeColumn.setMinWidth(170);
			timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));


			table.getColumns().addAll(fullNameColumn, startNumberColumn, timeColumn);

			nameInput = new TextField();
			nameInput.setPromptText("Name");
			nameInput.setMinWidth(250);

			startNumberInput = new TextField();
			startNumberInput.setPromptText("Startnumber");
			startNumberInput.setMaxWidth(50);

			// Knappar
			Button btnAdd = new Button("Add skier");
			btnAdd.setOnAction(e->{
				myButton.add(table, obList, nameInput, startNumberInput);
			});

			Button btnDelete = new Button("Delete skier");
			btnDelete.setOnAction(e->{
				myButton.delete(table, obList, startNumberInput);
			});

			Button btnGoal = new Button("Finish");
			btnGoal.setMinWidth(100);
			btnGoal.setOnAction(e->{
				myButton.goal(table, obList, timer.getTime());
			});

			Button btnStartRace = new Button("Start race");
			btnStartRace.setMinWidth(100);

			Button btnSave = new Button("Save list");
			btnSave.setMinWidth(100);
			btnSave.setOnAction(e -> {
				ArrayList<Skier> arrlist = new ArrayList<>(table.getItems());
				Serialization serialization = new Serialization();
				serialization.serialize((ArrayList<Skier>) arrList, "./skiers.xml");

			});

			btnStartRace.setOnAction(e->{
				if(!running) {
					timer.start();
					btnStartRace.setText("Stop race");
					running = true;
				}
				else {
					timer.stop();
					timer.reset();
					btnStartRace.setText("Start race");
					running = false;

				}
			});

			Button btnGetList = new Button("Get list");
			btnGetList.setMinWidth(100);
			btnGetList.setOnAction(e -> {
				Serialization deserialization = new Serialization();
				arrList = deserialization.deserialize((ArrayList<Skier>) arrList, "./skiers.xml");
				ObservableList<Skier> obList = FXCollections.observableList(arrList);
				table.getItems().addAll(obList);
			});
			Button btnHunt = new Button("Hunt");
			Button btnIndividual15 = new Button("Mass");
			Button btnIndividual30 = new Button("30 sek");

			// Labels
			Label lblName = new Label("Name");
			Label lblNumber = new Label("Number");

			// Layout
			BorderPane root = new BorderPane();
			VBox vBoxRight = new VBox();
			GridPane gridPane = new GridPane();
			gridPane.setPadding(new Insets(10, 10, 10, 10));
			gridPane.setVgap(30);
			gridPane.setHgap(5);

			// Name and time current skier

			// Search skier
			GridPane.setConstraints(lblName, 0, 1);
			GridPane.setConstraints(lblNumber, 0, 2);
			GridPane.setConstraints(nameInput, 1, 1);
			GridPane.setConstraints(startNumberInput, 1, 2);

			// Add Skier
			GridPane.setConstraints(btnAdd, 0, 3);

			// Delete Skier
			GridPane.setConstraints(btnDelete, 1, 3);

			// Show leader
			Label lblNrCurrentLeader = new Label();
			lblNrCurrentLeader.setStyle("-fx-font-size: 20");
			lblNrCurrentLeader.setText("Lucas Bauer");
			GridPane.setConstraints(lblNrCurrentLeader, 0, 4);

			// Show difference to leader

			lblDifference.setStyle("-fx-font-size: 30");
			lblDifference.setText("00:00.000");
			GridPane.setConstraints(lblDifference, 0, 5);

			// RaceSettings

			Label lblTypeOfRace = new Label("Select type of start");

			Label lblChosenskier = new Label("Gunde Svan");
			lblChosenskier.setStyle("-fx-font-size: 20");
			Label lblTime = new Label("00:00.000");
			lblTime.setStyle("-fx-font-size: 30");
			// Save result


			//Get resultlist/Startlist
			gridPane.getChildren().addAll(lblName, nameInput, lblNumber, startNumberInput);

			HBox hBox = new HBox();
			hBox.setPadding(new Insets(10, 10, 10, 5));
			hBox.setSpacing(20);
			hBox.getChildren().addAll(btnStartRace, btnGoal);

			HBox hBox2 = new HBox();
			hBox2.setPadding(new Insets(10, 10, 10, 5));
			hBox2.setSpacing(20);
			hBox2.getChildren().addAll(btnHunt, btnIndividual15, btnIndividual30);

			HBox hBox3 = new HBox();
			hBox3.setPadding(new Insets(10, 10, 10, 5));
			hBox3.setSpacing(20);
			hBox3.getChildren().addAll(lblNrCurrentLeader, lblDifference);

			HBox hBox6 = new HBox();
			hBox6.setPadding(new Insets(10, 10, 10, 5));
			hBox6.setSpacing(20);
			hBox6.getChildren().addAll(lblChosenskier, lblTime);

			HBox hBox4 = new HBox();
			hBox4.setPadding(new Insets(10, 10, 10, 5));
			hBox4.setSpacing(20);
			hBox4.getChildren().addAll(lblTypeOfRace);

			HBox hBox5 = new HBox();
			hBox5.setPadding(new Insets(10, 10, 10, 5));
			hBox5.setSpacing(20);
			hBox5.getChildren().addAll(btnAdd, btnDelete);

			VBox vBox2 = new VBox();
			vBox2.setPadding(new Insets(10, 10, 10,5));
			vBox2.getChildren().addAll(hBox5, hBox3, hBox6, hBox, hBox4, hBox2);


			vBoxRight.setPadding(new Insets(10, 10, 10,5));
			vBoxRight.getChildren().addAll(gridPane, vBox2);

			root.setLeft(table);
			root.setRight(vBoxRight);

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Russian EPO-Meter");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}


}