package client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import application.AniTimer;
import application.MyAlert;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Small client program that connects to a server through Socket/ServerSocket.
 * It sends and reads messages through the readMessage() and sendMessage() methods.
 * @author Jesper
 *
 */
public class Client extends Application{

	//--Variables--
	private final static int ServerPort = 8081; 
	private InetAddress ip;
	private Socket socket;
	private boolean connected = false;
	private Label lblTime = new Label("00:00.0");
	private Label lblSkier = new Label("SelectedSkier");
	private AniTimer timer = new AniTimer(lblTime);
	DataInputStream input = null;
	DataOutputStream output = null;
	
	//--Methods--
	
	/**
	 * This methods starts from launch(args) from the main() method, must have because extends javafx.Application.
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {

		//--Buttons and Actions--
		
		/**
		 *When you click on the button, it tries to connect to a ServerSocket by creating a
		 *socket and connecting by the inparameters in socket(ip-adress, ServerPort).
		 *If successful, it starts up the sendMessage() and readMessage() methods which is separate threads to send and read to the server.
		 */
		Button btnConnect = new Button("Connect");
		btnConnect.setOnAction(e-> {		
			if(!connected) {
				try {
					ip = InetAddress.getByName("localhost");
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				try {
					socket = new Socket(ip, ServerPort);
					input = new DataInputStream(socket.getInputStream()); 
					output = new DataOutputStream(socket.getOutputStream());
					btnConnect.setText("Connected");
					connected = true;
					MyAlert.showInfo("Successfully connected to server!");
					sendMessage(output, "Enter");
					readMessage(input);
				} catch (IOException e1) {
					e1.printStackTrace();
					MyAlert.showInfo("Could not connect to Server.");
				} 
			}
		});
		
		Button btnSplit = new Button("Split");
		btnSplit.setOnAction(e ->{
			if(connected) {
				sendMessage(output, "Split");
			}else if(!connected) {
				MyAlert.showInfo("You are not connected");
			}
		});
		
		Button btnFinish = new Button("Finish");
		btnFinish.setOnAction(e->{
			if(connected) {
				sendMessage(output, "Finish");
			}else if(!connected) {
				MyAlert.showInfo("You are not connected");
			}
		});


		// --GUI--
		lblTime.setStyle("-fx-font-size: 25");
		lblSkier.setStyle("-fx-font-size: 17");
		
		//BorderPanes
		BorderPane borderPane = new BorderPane();
		BorderPane borderPane2 = new BorderPane();
		
		borderPane2.setLeft(btnSplit);
		borderPane2.setRight(btnFinish);
		
		borderPane.setTop(btnConnect);
		borderPane.setRight(lblTime);
		borderPane.setLeft(lblSkier);
		borderPane.setBottom(borderPane2);
		
		//Scenes and stage
		Scene scene = new Scene(borderPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Russian EPOGenerator");
		primaryStage.setResizable(false);
		primaryStage.show();


	}//start

	/**
	 * Method that starts up a separate thread to send a message to the connected Server/socket.
	 * @param output Which DataOutputStream to send through
	 * @param string What String you want to send through the DataoutputStream
	 */
	public void sendMessage(DataOutputStream output, String string) {
		Thread sendMessage = new Thread(new Runnable() { 
			@Override
			public void run() { 
				boolean tempBool = true;
				while (true) { 
					while(tempBool) {
						try { 
							output.writeUTF(string); 
						} catch (IOException e) { 
							e.printStackTrace(); 
						} 
						tempBool = false;
					}
				} 
			} 
		}); 
		sendMessage.start();
	}//sendMessage
	
	/**
	 * Method that starts up a separate thread to read a message from the server you are connected to.
	 * @param input Which DataInputStream you want to read from.
	 */
	public void readMessage(DataInputStream input) {
		//Thread that handles messages FROM the server
		Thread readMessage = new Thread(new Runnable() { 
			@Override
			public void run() { 
				while (true) { 

					try { 	
						// Read the message sent to this client and then set the clients Label through the JavaFX thread with Platform.runLater().
						String msg = input.readUTF(); 
						System.out.println(msg); 
						Platform.runLater(() -> {

							if(msg.equalsIgnoreCase("TimerStart")) {
								timer.start();
							}
							else if(msg.equalsIgnoreCase("TimerStop")) {
								timer.stop();
								timer.reset();
							}else{
								//I had to the if-statement like this to work, I tried using !() and !msg.contains but neither did work.
								//This is to make sure so you don't update the label with anything else besides the skiers names.
								if(msg.contains("Split") || msg.contains("Finish")) {
									
								}else
									lblSkier.setText(msg);
								
							}

						});
					}
					//The System.exit(1) is needed to shut down the program so it isn't stuck in a neverending loop.
					catch (IOException e) { 
						e.printStackTrace();
						System.exit(1);
					} 
				}
			} 
		}); 
		readMessage.start(); 
	}//readMessage
	
	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}//main


	
	
}//class
