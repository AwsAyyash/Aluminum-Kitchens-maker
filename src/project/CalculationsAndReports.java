package project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class CalculationsAndReports {


    public Stage queryTap(Stage homeStage) {

        Stage stage = new Stage();
        Stage finalStage = stage;
        stage.setTitle("Calculations");

        Label welcomeLabel = new Label("Welcome!");
        welcomeLabel.setFont(Font.font("Verdana", 20));


        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15));

        borderPane.setTop(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.TOP_CENTER);

        Scene scene = new Scene(borderPane, 400, 400);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Choose", "query #orders in a city",
                "Max price of RawMaterials", "sum of salaries for workers",
                "Sum of customers payments", "Number of Raw materials supplied by Supplier",
                "Number of Suppliers in city", "The Worker that has a max number of orders worked by him", "Cost", "Profit");
        borderPane.setCenter(comboBox);
        comboBox.getSelectionModel().selectFirst();

        comboBox.setOnAction(actionEvent -> {

            int selectedInput = comboBox.getSelectionModel().getSelectedIndex(); // counts from zero


            switch (selectedInput) {
                case 1 -> {

                    TextField textFieldLocation = new TextField();

                    textFieldLocation.setPromptText("City");
                    Label output = new Label("output");
                    borderPane.setBottom(output);
                    HBox hBox = new HBox(10);
                    hBox.getChildren().addAll(textFieldLocation, output);
                    borderPane.setBottom(hBox);
                    textFieldLocation.setOnAction(actionEvent1 -> {
                        try {
                            String queryCity = "SELECT count(*) FROM  orders where deliveryLocation='" + textFieldLocation.getText() + "' ;";

                            ResultSet resultSet = Operations.executeQueryToGetData(queryCity);
                            resultSet.next();
                            if (resultSet.getString(1) != null)
                                output.setText("Number of orders to " + textFieldLocation.getText() + "= " + resultSet.getString(1));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });


                }
                case 2 -> {
                    Label output = new Label("out");
                    borderPane.setBottom(output);
                    String select = "SELECT R.rawMaterialId, R.rawMaterialPrice FROM RawMaterial R " +
                            "WHERE R.rawMaterialPrice = (select max(rawMaterialPrice) from RawMaterial);";
                    try {
                        ResultSet resultSet = Operations.executeQueryToGetData(select);
                        resultSet.next();
                        if (resultSet.getString(1) != null)
                            output.setText("The RawMarerilId of the highest price= " + resultSet.getString(1) + ", with price=" + resultSet.getString(2));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                case 3 -> {
                    try {
                        new salariesFX().salariesFXStage(finalStage).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 4 -> {
                    String select = "SELECT sum(p.amount) FROM payment p WHERE p.customerId IS NOT NULL;";
                    Label out = new Label();
                    borderPane.setBottom(out);

                    try {
                        ResultSet resultSet = Operations.executeQueryToGetData(select);
                        resultSet.next();
                        out.setText("Sum of payments (profit) from customers= " + resultSet.getString(1));


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                case 5 -> {

                    try {
                        new supplierCountQueryFX().supplierCountQueryStage(finalStage).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finalStage.close();
                    comboBox.getSelectionModel().selectFirst();

                }
                case 6 -> {

                    try {
                        new CityCountQueryFX().CityCountQueryStage(finalStage).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finalStage.close();
                    comboBox.getSelectionModel().selectFirst();

                }
                case 7 -> {

                    String select = "SELECT workerId,count(workerId) FROM orderstoworkers GROUP BY workerID having count(workerId) >=Max((SELECT count(workerId) FROM orderstoworkers GROUP  BY workerID));";
                    Label out = new Label();
                    borderPane.setBottom(out);

                    try {
                        ResultSet resultSet = Operations.executeQueryToGetData(select);
                        resultSet.next();
                        if (resultSet.getString(1) != null)
                            out.setText("The worker id that has max number of works (orders)= " + resultSet.getString(1) + ",\nand number of orders=" + resultSet.getString(2));


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                case 8 -> {

                    try {
                        new CostFX().CostFxStage(finalStage).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finalStage.close();
                    comboBox.getSelectionModel().selectFirst();

                }

                case 9 -> {

                    try {
                        new ProfitFX().profitFXStage(finalStage).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finalStage.close();
                    comboBox.getSelectionModel().selectFirst();

                }


            }
        });
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
                    stage.close();
                    homeStage.show();

                }
        );
        return stage;

    }

}


