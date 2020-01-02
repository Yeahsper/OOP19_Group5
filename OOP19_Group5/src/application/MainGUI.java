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
	Controller controller = new Controller();
	Split split = new Split();
	boolean running;
	Label lblNrCurrentLeader = new Label();
	Label lblTimeSkier = new Label();
	AniTimer timer = new AniTimer(lblTimeSkier);
	int startnumber = 0;
	long selectedStart = 0;
	long raceTimer = 0;
	long currentLeaderTime = 9999;

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
			TableColumn<Skier, String> timeColumn = new TableColumn<>("Time");
			timeColumn.setMinWidth(170);
			timeColumn.setCellValueFactory(new PropertyValueFactory<>("parsedTime"));


			table.getColumns().addAll(fullNameColumn, startNumberColumn, timeColumn);

			nameInput = new TextField();
			nameInput.setPromptText("Name");
			nameInput.setMinWidth(250);

			startNumberInput = new TextField();
			startNumberInput.setText("1");
			startNumberInput.setMaxWidth(50);

			Label lblLeader = new Label("1");
			Label lblStartnumberLeader = new Label();
			Label lblStartnumber = new Label();
			lblStartnumber.setStyle("-fx-font-size: 17");
			Label lblNameLeader = new Label();
			Label lblTimeLeader = new Label("0");
			Label lblSign = new Label();
			Label lblCurrentLeaderName = new Label();
			Label lblCurrentLeaderTime = new Label();

			Label lblChosenskier = new Label();
			lblChosenskier.setText("Choose skier");
			lblChosenskier.setMinWidth(150);
			lblChosenskier.setStyle("-fx-font-size: 17");
			Label lblTime = new Label();
			lblTime.setStyle("-fx-font-size: 20");

			// Knappar
			Button btnAdd = new Button("Add skier");
			btnAdd.setOnAction(e->{
				controller.add(table, obList, nameInput, startNumberInput);
			});

			Button btnDelete = new Button("Delete skier");
			btnDelete.setOnAction(e->{
				controller.delete(table, obList, startNumberInput);
			});

			Button btnSelect = new Button("Select skier");
			btnSelect.setMinWidth(100);
			btnSelect.setOnAction(e -> {
				lblChosenskier.setText("");
				lblStartnumber.setText("");
				lblTimeSkier.setText("");

				String parsedTime = controller.getParsedTime(split.getSelectedStartNumber(), selectedStart, timer.getTime());
				split.split(table, obList, parsedTime);
				parsedTime = controller.getParsedTime(split.getSelectedStartNumber(), selectedStart, timer.getTime());
				split.split(table, obList, parsedTime);

				lblChosenskier.setText(split.getSelectedName());
				lblStartnumber.setText(String.valueOf(split.getSelectedStartNumber()));
				lblTimeSkier.setText(parsedTime);



			});

			Button btnFinish = new Button("Finish");
			btnFinish.setMinWidth(100);
			btnFinish.setOnAction(e->{
			//	String parsedTime = controller.getParsedTime(timer.getTime());
			//	controller.goal(table, obList, parsedTime);
			});


			Button btnSave = new Button("Save list");
			btnSave.setMinWidth(100);
			btnSave.setOnAction(e -> {

				Serialization serialization = new Serialization();
				serialization.serialize((ArrayList<Skier>) arrList, "./skiers.xml");

			});

			Button btnStartRace = new Button("Start race");
			btnStartRace.setMinWidth(100);
			btnStartRace.setOnAction(e->{
				if(!running) {
					timer.start();
					raceTimer = timer.getTime();

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

			Button btnSplit = new Button("Split");
			btnSplit.setMinWidth(100);
			btnSplit.setOnAction(e -> {

		         String splitTime = split.getSplitTime(split.getSelectedStartNumber(), selectedStart, timer.getTime(), lblTimeLeader);

		         split.compare(timer.getTime(), currentLeaderTime);

				lblStartnumberLeader.setText(String.valueOf(split.getSelectedStartNumber()));
				lblNameLeader.setText(split.getSelectedName());
				lblSign.setText(split.getSign());
				lblTimeLeader.setText(splitTime);

			});


			Button btnPursuit = new Button("Pursuit");
			Button btnMass = new Button("Mass");
			Button btnInd30 = new Button("30 sek");
			btnInd30.setOnAction(e -> {
				selectedStart = 30000;
			});


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

			// Show leader

			lblNrCurrentLeader.setMinWidth(150);
			lblNrCurrentLeader.setStyle("-fx-font-size: 20");
			//lblNrCurrentLeader.setText(split.getName());


			// Show difference to leader

			lblTimeSkier.setStyle("-fx-font-size: 20");

		//	GridPane.setConstraints(lblDifference, 0, 5);

			// RaceSettings

			Label lblTypeOfRace = new Label("Select type of start");


			// Save result


			//Get resultlist/Startlist
			gridPane.getChildren().addAll(lblName, nameInput, lblNumber, startNumberInput);

			HBox hBox = new HBox();
			hBox.setPadding(new Insets(10, 10, 10, 5));
			hBox.setSpacing(20);
			hBox.getChildren().addAll(btnAdd, btnDelete, btnSelect);

			HBox hBox2 = new HBox();
			hBox2.setPadding(new Insets(10, 10, 10, 5));
			hBox2.setSpacing(20);
			hBox2.getChildren().addAll(lblStartnumber, lblChosenskier, lblTimeSkier);

			HBox hBox3 = new HBox();
			hBox3.setPadding(new Insets(10, 10, 10, 5));
			hBox3.setSpacing(20);
			hBox3.getChildren().addAll(lblStartnumberLeader,  lblNameLeader, lblSign, lblTimeLeader);

			HBox hBox4 = new HBox();
			hBox4.setPadding(new Insets(10, 10, 10, 5));
			hBox4.setSpacing(20);
			hBox4.getChildren().addAll(lblLeader, lblCurrentLeaderName, lblCurrentLeaderTime);

			HBox hBox5 = new HBox();
			hBox5.setPadding(new Insets(10, 10, 10, 5));
			hBox5.setSpacing(20);
			hBox5.getChildren().addAll(btnStartRace, btnSplit, btnFinish);

			HBox hBox6 = new HBox();
			hBox6.setPadding(new Insets(10, 10, 10, 5));
			hBox6.setSpacing(20);
			hBox6.getChildren().addAll(lblTypeOfRace);

			HBox hBox7 = new HBox();
			hBox7.setPadding(new Insets(10, 10, 10, 5));
			hBox7.setSpacing(25);
			hBox7.getChildren().addAll(btnPursuit, btnMass, btnInd30);

			HBox hBox8 = new HBox();
			hBox8.setPadding(new Insets(10, 10, 10, 5));
			hBox8.setSpacing(20);
			hBox8.getChildren().addAll(btnSave, btnGetList);



			VBox vBox2 = new VBox();
			vBox2.setPadding(new Insets(10, 10, 10,5));
			vBox2.getChildren().addAll(hBox, hBox2, hBox3, hBox4, hBox5, hBox6, hBox7, hBox8);


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