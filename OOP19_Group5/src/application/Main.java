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

public class Main extends Application {
	TableView <Skier> table;
	TextField nameInput, startNumberInput;

	@Override
	public void start(Stage primaryStage) {//
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
			TableColumn<Skier, Integer> differenceColumn = new TableColumn<>("Difference");
			differenceColumn.setMinWidth(170);
			differenceColumn.setCellValueFactory(new PropertyValueFactory<>("startNumber"));

			table = new TableView<>();
			table.setItems(getSkier());
			table.getColumns().addAll(fullNameColumn, startNumberColumn, differenceColumn);

			nameInput = new TextField();
			nameInput.setPromptText("Name");
			nameInput.setMaxWidth(170);

			startNumberInput = new TextField();
			startNumberInput.setPromptText("Startnumber");
			startNumberInput.setMaxWidth(170);

			// Knappar
			Button btnSearchName = new Button("Search");
			Button btnSearchStartNumber = new Button("Search");
			Button btnUpdate = new Button("Add and refresh list");
			Button btnStartRace = new Button("Start race");
			Button btnHunt = new Button("Hunt");
			Button btnIndividual15 = new Button("Mass");
			Button btnIndividual30 = new Button("30 sek");

			// Layout
			BorderPane root = new BorderPane();
			VBox vBoxRight = new VBox();
			GridPane gridPane = new GridPane();
			gridPane.setPadding(new Insets(10, 10, 10, 10));
			gridPane.setVgap(30  );
			gridPane.setHgap(5);

			// Name and time current skier
			Label lblNameCurrentSkier = new Label();
			lblNameCurrentSkier.setStyle("-fx-font-size: 18");
			lblNameCurrentSkier.setText("Gunde Svan");
			GridPane.setConstraints(lblNameCurrentSkier, 0, 0);
			Label lblTimeCurrentSkier = new Label();
			lblTimeCurrentSkier.setStyle("-fx-font-size: 18");
			lblTimeCurrentSkier.setText("00:00:00");
			GridPane.setConstraints(lblTimeCurrentSkier, 1, 0);

			// Search skier
			GridPane.setConstraints(btnSearchName, 0, 1);
			GridPane.setConstraints(btnSearchStartNumber, 0, 2);
			GridPane.setConstraints(nameInput, 1, 1);
			GridPane.setConstraints(startNumberInput, 1, 2);

			// Update
			GridPane.setConstraints(btnUpdate, 0, 3);

			// Show leader
			Label lblNrCurrentLeader = new Label();
			lblNrCurrentLeader.setStyle("-fx-font-size: 20");
			lblNrCurrentLeader.setText("Lucas Bauer");
			GridPane.setConstraints(lblNrCurrentLeader, 0, 4);

			// Show difference to leader
			Label lblDifference = new Label();
			lblDifference.setStyle("-fx-font-size: 40");
			lblDifference.setText("00:00:00");
			GridPane.setConstraints(lblDifference, 0, 5);

			// RaceSettings
            GridPane.setConstraints(btnStartRace, 0, 6);
            Label lblTypeOfRace = new Label("Select type of start");
            GridPane.setConstraints(lblTypeOfRace, 0, 7);


			gridPane.getChildren().addAll(lblNameCurrentSkier, lblTimeCurrentSkier, btnSearchName, nameInput, btnSearchStartNumber, startNumberInput,
                    btnUpdate, lblNrCurrentLeader, lblDifference, btnStartRace, lblTypeOfRace);

            HBox hBox = new HBox();
            hBox.setPadding(new Insets(10, 10, 10, 10));
            hBox.setSpacing(20);
            hBox.getChildren().addAll(btnHunt, btnIndividual15, btnIndividual30);

			vBoxRight.setPadding(new Insets(10, 10, 10,10));
			vBoxRight.getChildren().addAll(gridPane, hBox);

			root.setLeft(table);
			root.setRight(vBoxRight);

			Scene scene = new Scene(root,800,900);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Russian EPOGenerator");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public ObservableList<Skier> getSkier() {
		ObservableList<Skier> skiers = FXCollections.observableArrayList();
		skiers.add(new Skier("Gunde", "Svan", 1));
		skiers.add(new Skier("Dimitri", "Youshenko", 2));
		skiers.add(new Skier("Lucas", "Bauer", 3));
		return skiers;
	}
}
