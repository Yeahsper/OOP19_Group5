package application;


import javafx.application.Application;
import javafx.stage.Stage;
import server.Server;

/**
 * Main class to run the Application and Server.
 * @author Jesper
 *
 */
public class Main extends Application {

	/**
	 * Starts up both the server and the GUI in two separate Threads, so no need for a separate program for just the server and/or the GUI.
	 */
	@Override
	public void start(Stage primaryStage) {
		
		Thread server = new Thread(new Server());
		server.start();
		
		Thread mainGUI = new Thread(new MainGUI());
		mainGUI.start();
		

	}

	public static void main(String[] args) {
		launch(args);
	}
}
