package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * GUI for the application
 * @author Jesper
 *
 */
public class MainGUI implements Runnable {

	// --Objects--

	// Labels
	private Label lblNrCurrentLeader = new Label();
	private Label lblTimeSkier = new Label();

	// Various Objects
	private AniTimer timer = new AniTimer(lblTimeSkier);
	private Skier skier = new Skier();
	private Controller controller = new Controller();
	private Split split = new Split();
	private Serialization serialization = new Serialization();
	private Stage primaryStage = new Stage();

	// Tableview etc.
	private TableView <Skier> table = new TableView<>();
	private static List<Skier> arrList = new ArrayList<Skier>();
	private static ObservableList<Skier> obList = FXCollections.observableList(arrList);

	// Connection
	private final static int ServerPort = 8081; 
	private InetAddress ip;
	private Socket socket;
	private boolean connected = false;
	DataInputStream input = null;
	DataOutputStream output = null;

	// Variables
	private boolean running;
	private long selectedStart;
	private long raceTimer = 0;
	private String parsedTime;
	static String chosenSkier;

	// Constructors
	public MainGUI() {
	}

	/**
	 * Method that starts up a separate thread to send a message to the connected Server/socket.
	 * @param output Which DataOutputStream to send through
	 * @param string What String you want to send through the DataoutputStream
	 */
	public void sendMessage(DataOutputStream output, String string) {
		Thread sendMessage = new Thread(new Runnable() { 
			@Override
			public void run() { 
				while (true) { 
					try { 
						output.writeUTF(string); 
						break;
					} catch (IOException e) { 
						e.printStackTrace(); 
					} 
				}
			} 
		}); 
		sendMessage.start();
	}//sendMessage

	/**
	 * Starts through Main.java when we start up a new Thread, because it implements Runnable.
	 * Pretty much everything here is the graphical user interface.
	 */
	public void run() {

		//Need to use Platform.runLater because of JavaFX threads.
		Platform.runLater(() -> {
			try {
				/** Columns used in gridPane **/
				// NameColumn
				TableColumn<Skier, String> fullNameColumn = new TableColumn<>("Name");
				fullNameColumn.setMinWidth(170);
				fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

				// StartNumberColumn
				TableColumn<Skier, Integer> startNumberColumn = new TableColumn<>("Startnumber");
				startNumberColumn.setMinWidth(100);
				startNumberColumn.setCellValueFactory(new PropertyValueFactory<>("startNumber"));

				// Skiers time. Shows total racetime or splittime depending on user input
				TableColumn<Skier, String> timeColumn = new TableColumn<>("Time");
				timeColumn.setMinWidth(170);
				timeColumn.setCellValueFactory(new PropertyValueFactory<>("parsedTime"));

				table.getColumns().addAll(fullNameColumn, startNumberColumn, timeColumn);

				// Textfields
				TextField nameInput = new TextField();
				nameInput.setPromptText("Name");
				nameInput.setMinWidth(250);

				TextField startNumberInput = new TextField();
				startNumberInput.setText("1");
				startNumberInput.setMaxWidth(50);

				// Labels
				Label lblBestTime = new Label();
				Label lblChosenskier = new Label();
				Label lblDifferenceToLeader = new Label();
				Label lblLeader = new Label();
				Label lblName = new Label("Name");
				Label lblNameLeader = new Label();
				Label lblNumber = new Label("Number");
				Label lblSelectedStartNr = new Label();
				Label lblStartnumber = new Label();
				Label lblSelectedName = new Label();
				Label lblSign = new Label();
				Label lblTimeDelay = new Label();
				Label lblTime = new Label();
				Label lblTypeOfRace = new Label("Select type of start");

				// Label properties
				lblStartnumber.setStyle("-fx-font-size: 17");
				lblChosenskier.setMinWidth(150);
				lblChosenskier.setStyle("-fx-font-size: 17");
				lblTime.setStyle("-fx-font-size: 20");
				lblTimeSkier.setStyle("-fx-font-size: 20");
				lblNrCurrentLeader.setMinWidth(150);
				lblNrCurrentLeader.setStyle("-fx-font-size: 20");

				// Buttons
				Button btnAdd = new Button("Add skier");
				Button btnDelete = new Button("Delete skier");
				Button btnSelect = new Button("Select skier");
				Button btnFinish = new Button("Finish");
				Button btnSaveList = new Button("Save list");
				Button btnStartRace = new Button("Start race");
				Button btnGetList = new Button("Get list");
				Button btnSplit = new Button("Split");
				Button btnPursuit = new Button("Pursuit");
				Button btnMass = new Button("Mass");
				Button btnInd30 = new Button("30 seconds");

				// Button properties
				btnSplit.setMinWidth(100);
				btnGetList.setMinWidth(100);
				btnSelect.setMinWidth(100);
				btnStartRace.setMinWidth(100);
				btnSaveList.setMinWidth(100);
				btnFinish.setMinWidth(100);

				// --Button actions--

				// Add
				btnAdd.setOnAction(e->{
					controller.add(table, obList, nameInput, startNumberInput);
				});

				// Delete
				btnDelete.setOnAction(e->{
					controller.delete(table, obList, startNumberInput);
				});

				// Select
				btnSelect.setOnAction(e -> {
					controller.select(table, obList, lblSelectedStartNr, lblSelectedName, lblSign,
							lblDifferenceToLeader, selectedStart, split.getSelectedStartNumber(), timer.getTime());
					chosenSkier = lblSelectedName.getText();
					System.out.println(chosenSkier);
					sendMessage(output,"SelectedSkier");
				});

				// Start
				btnStartRace.setOnAction(e->{
					if(!running) {
						timer.start();
						raceTimer = timer.getTime();
						btnStartRace.setText("Stop race");
						running = true;
						sendMessage(output, "TimerStart");
						controller.activeButtons(btnAdd, btnDelete, btnSaveList, btnGetList, btnInd30, btnMass, true);


					}
					else {
						timer.stop();
						timer.reset();
						btnStartRace.setText("Start race");
						running = false;
						sendMessage(output, "TimerStop");
						controller.activeButtons(btnAdd, btnDelete, btnSaveList, btnGetList, btnInd30, btnMass, false);
					}
				});

				// Split
				btnSplit.setOnAction(e -> {
					controller.split(table, obList, lblLeader, lblNameLeader, lblBestTime, lblSelectedStartNr, lblSelectedName,
							lblSign, lblDifferenceToLeader, selectedStart, split.getSelectedStartNumber(), timer.getTime());
				});

				// Finish
				btnFinish.setOnAction(e->{
					parsedTime = controller.getParsedTime(split.getSelectedStartNumber(), selectedStart, timer.getTime());
					controller.goal(table, obList, parsedTime);
				});

				// Save list
				btnSaveList.setOnAction(e -> {
					serialization.serialize((ArrayList<Skier>) arrList, "./skiers.xml");
				});


				// Get list
				btnGetList.setOnAction(e -> {

					arrList = serialization.deserialize((ArrayList<Skier>) arrList, "./Skiers.xml");
					table.getItems().removeAll(obList);
					obList = FXCollections.observableList(arrList);
					table.getItems().addAll(obList);
				});



				// 30 sec individual start
				btnInd30.setOnAction(e -> { // TODO: Start individual racetimer, one for each skier
					selectedStart = 30000;
					btnInd30.setStyle("-fx-background-color: #ff93ae;");
					btnMass.setStyle(null);
					//	Thread thread = new Thread(new Counter());
					//	thread.start();
				});

				btnMass.setOnAction(e -> {
					selectedStart = 0;
					btnMass.setStyle("-fx-background-color: #ff93ae;");
					btnInd30.setStyle(null);
				});

				btnPursuit.setDisable(true);

				// --Layout GUI--
				BorderPane root = new BorderPane();
				VBox vBoxRight = new VBox();
				GridPane gridPane = new GridPane();
				gridPane.setPadding(new Insets(10, 10, 10, 10));
				gridPane.setVgap(30);
				gridPane.setHgap(5);

				GridPane.setConstraints(lblName, 0, 1);
				GridPane.setConstraints(lblNumber, 0, 2);
				GridPane.setConstraints(nameInput, 1, 1);
				GridPane.setConstraints(startNumberInput, 1, 2);

				gridPane.getChildren().addAll(lblName, nameInput, lblNumber, startNumberInput);

				HBox hBox = new HBox();
				hBox.setPadding(new Insets(10, 10, 10, 5));
				hBox.setSpacing(20);
				hBox.getChildren().addAll(btnAdd, btnDelete, btnSelect);

				HBox hBox2 = new HBox();
				hBox2.setPadding(new Insets(10, 10, 10, 5));
				hBox2.setSpacing(20);
				hBox2.getChildren().addAll(lblTimeDelay, lblChosenskier, lblTimeSkier);

				HBox hBox3 = new HBox();
				hBox3.setPadding(new Insets(10, 10, 10, 5));
				hBox3.setSpacing(20);
				hBox3.getChildren().addAll(lblSelectedStartNr,  lblSelectedName, lblSign, lblDifferenceToLeader);

				HBox hBox4 = new HBox();
				hBox4.setPadding(new Insets(10, 10, 10, 5));
				hBox4.setSpacing(20);
				hBox4.getChildren().addAll(lblLeader, lblNameLeader, lblBestTime);

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
				hBox8.getChildren().addAll(btnSaveList, btnGetList);

				VBox vBox2 = new VBox();
				vBox2.setPadding(new Insets(10, 10, 10,5));
				vBox2.getChildren().addAll(hBox, hBox2, hBox3, hBox4, hBox5, hBox6, hBox7, hBox8);

				vBoxRight.setPadding(new Insets(10, 10, 10,5));
				vBoxRight.getChildren().addAll(gridPane, vBox2);

				root.setLeft(table);
				root.setRight(vBoxRight);

				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Russian EPO-Meter");
				primaryStage.setResizable(false);
				primaryStage.show();

				// Need to use this to completely shut down everything when you exit, including the server.
				primaryStage.setOnCloseRequest(e -> {
					System.exit(1);
				});


				//Server-Client stuff


				try {
					ip = InetAddress.getByName("localhost");
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}

				/**
				 *Tries to connect to a ServerSocket by creating a
				 *socket and connecting by the inparameters in socket(ip-adress, ServerPort).
				 *If successful, it starts up the readMessage() methods which is separate threads and reads messages from clients.
				 */
				try {
					socket = new Socket(ip, ServerPort);
					input = new DataInputStream(socket.getInputStream()); 
					output = new DataOutputStream(socket.getOutputStream());

					/**
					 * Starts up a separate thread to read a message from the server you are connected to.
					 * @param input Which DataInputStream you want to read from.
					 */
					Thread readMessage = new Thread(new Runnable() { 
						@Override
						public void run() { 
							while (true) { 

								try { 	
									// Read the message sent to this client and then set the clients Label through the JavaFX thread 
									String msg = input.readUTF(); 
									System.out.println(msg); 
									Platform.runLater(() -> {

										//What these two if-statements does is pretty much the same as pressing the buttons in the interface.
										if(msg.equalsIgnoreCase("Finish")) {
											parsedTime = controller.getParsedTime(split.getSelectedStartNumber(), selectedStart, timer.getTime());
											controller.goal(table, obList, parsedTime);
										}
										if(msg.equalsIgnoreCase("Split")) {
											controller.split(table, obList, lblLeader, lblNameLeader, lblBestTime, lblSelectedStartNr, lblSelectedName,
													lblSign, lblDifferenceToLeader, selectedStart, split.getSelectedStartNumber(), timer.getTime());
										}
									});
								} catch (IOException e) { 
									e.printStackTrace(); 

								} 
							}
						} 
					}); 
					readMessage.start(); 

				} catch (IOException e1) { 
					e1.printStackTrace(); 
				}//server-client 

			} catch(Exception e) {
				e.printStackTrace();
			}//Platform.runLater
		});
	}//run();



	//--Getters and Setters--
	public static String getChosenSkier() {
		return chosenSkier;
	}



}//class