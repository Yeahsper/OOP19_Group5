package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class MyAlert {

	private static Alert alert;
	
	public MyAlert() {
		
	};
	

	public static void showWarning(String string) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setContentText(string);
		alert.show();
	}
	
	public static void showInfo(String string) {
		alert = new Alert(AlertType.NONE);
		alert.setTitle("Stop your ski!");
		alert.setContentText(string);
		ButtonType buttonOk = new ButtonType("Close");
		alert.getButtonTypes().addAll(buttonOk);
		alert.showAndWait();
	}
}
