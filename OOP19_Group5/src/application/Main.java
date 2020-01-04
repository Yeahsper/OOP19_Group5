package application;


import javafx.application.Application;
import javafx.stage.Stage;
import server.Server;

public class Main extends Application {

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
