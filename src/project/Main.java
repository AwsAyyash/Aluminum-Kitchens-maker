package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);


    }


    @Override
    public void start(Stage stage) {

        stage = new Stage();
        Stage finalStage = stage;
        stage.setTitle("Home Page");

        Label welcomeLabel = new Label("Welcome!");
        welcomeLabel.setFont(Font.font("Verdana", 20));


        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15));

        borderPane.setTop(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.TOP_CENTER);

        Scene scene = new Scene(borderPane, 400, 400);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Choose", "Raw Material Table!", "Customer and orders",
                "Payment", "Supplier", "Worker", "WorkerOrders", "Reports and calculations");
        borderPane.setCenter(comboBox);
        comboBox.getSelectionModel().selectFirst();

        comboBox.setOnAction(actionEvent -> {

            int selectedInput = comboBox.getSelectionModel().getSelectedIndex(); // counts from zero

            switch (selectedInput) { //CustomerOrdersStage
                case 1 -> {
                    borderPane.setBottom(null);

                    try {
                        new RawMaterialFX().rawMaterialStage(finalStage).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finalStage.close();
                    comboBox.getSelectionModel().selectFirst();

                }
                case 2 -> {
                    borderPane.setBottom(null);

                    try {
                        new CustomerOrdersFX().CustomerOrdersStage(finalStage).show();
                        finalStage.close();
                        comboBox.getSelectionModel().selectFirst();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                case 3 -> {
                    borderPane.setBottom(null);

                    try {
                        new PaymentFX().PaymentStage(finalStage).show();
                        finalStage.close();
                        comboBox.getSelectionModel().selectFirst();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 4 -> {
                    borderPane.setBottom(null);

                    try {
                        new SupplierFX().SupplierStage(finalStage).show();
                        finalStage.close();
                        comboBox.getSelectionModel().selectFirst();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 5 -> {
                    borderPane.setBottom(null);

                    try {
                        new WorkerFX().WorkerStage(finalStage).show();
                        finalStage.close();
                        comboBox.getSelectionModel().selectFirst();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 6 -> {
                    borderPane.setBottom(null);
                    try {
                        new OrdersWorkersFX().OrdersWorkersStage(finalStage).show();
                        finalStage.close();
                        comboBox.getSelectionModel().selectFirst();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 7 -> {
                    borderPane.setBottom(null);
                    new CalculationsAndReports().queryTap(finalStage).show();
                    finalStage.close();
                    comboBox.getSelectionModel().selectFirst();

                }
            }
        });
        stage.setScene(scene);
        finalStage.show();


    }

}
