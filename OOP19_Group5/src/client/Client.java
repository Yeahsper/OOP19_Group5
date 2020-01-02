package client;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Client extends Application{
    TextField startNumberInput;


        @Override
        public void start(Stage primaryStage) {
            Button btnUpdate = new Button("Update");
            Button btnSearch = new Button("Search");
            startNumberInput = new TextField();
            Label lblLeader = new Label();
            Label lblDifference = new Label();
            Label lblSkier = new Label();

            btnUpdate.setMinWidth(150);
            btnSearch.setMinWidth(150);

            startNumberInput.setPromptText("Enter startnumber");
            startNumberInput.setMinWidth(150);

            lblLeader.setMinWidth(150);
            lblLeader.setText("Emil Iversen");

            lblDifference.setMinWidth(150);
            lblDifference.setText("- 00:01:15");
            lblDifference.setStyle("-fx-font-size: 40");

            lblSkier.setMinWidth(150);
            lblSkier.setText("Sergei Ustigov");

            try {
                GridPane grid = new GridPane();

                grid.setAlignment(Pos.CENTER);
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setHgap(20);
                grid.setVgap(30);
                grid.setConstraints(lblSkier, 0,0);
                grid.setConstraints(btnUpdate, 0, 1);
                grid.setConstraints(lblLeader, 0, 2);

                grid.setConstraints(startNumberInput, 1, 0);
                grid.setConstraints(btnSearch, 1, 1);
                grid.setConstraints(lblDifference, 1, 2);

                grid.getChildren().addAll(lblSkier, startNumberInput, lblDifference, lblLeader, btnUpdate, btnSearch);
                Scene scene = new Scene(grid,400,300);
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

    }


