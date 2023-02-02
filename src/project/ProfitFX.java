package project;


import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProfitFX {

    private Connection con;


    public ProfitFX() {
    }

    public Stage profitFXStage(Stage homeStage) throws Exception {

        Stage stage = new Stage();
        stage.setTitle(" cost sum  ");

        Label welcomeLabel = new Label("sum of costs for this month ");
        welcomeLabel.setFont(Font.font("Verdana", 20));
        welcomeLabel.setTextFill(Color.BLUE);
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15));

        borderPane.setTop(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.TOP_CENTER);

        Scene scene = new Scene(borderPane, 400, 400);


        String selectStatement1 = "select  sum(amount)" +
                "from payment where supplierId IS NOT NULL or workerId IS NOT NULL " + " ;";

        ResultSet resultSet = Operations.executeQueryToGetData(selectStatement1);

        double sum1 = 0;//  cost
        double sum2 = 0;


        while (resultSet.next()) {
            sum1 = Double.parseDouble(resultSet.getString(1));
        }


        String selectStatement2 = "select  sum(amount)" +
                "from payment where CustomerId IS NOT NULL  " + " ;";

        ResultSet resultSet2 = Operations.executeQueryToGetData(selectStatement2);


        while (resultSet2.next()) {
            sum2 = Double.parseDouble(resultSet2.getString(1));
        }
        resultSet.close();
        resultSet2.close();

        //---------------------------
        VBox vbox = new VBox(8);
        Label label1 = new Label(" profit ( customer payments - workers and supplier payments ) : ");

        Label label2 = new Label(Double.toString(sum2 - sum1));
        vbox.getChildren().addAll(label1, label2);
        borderPane.setCenter(vbox);
        stage.setScene(scene);

        //------------------------------------------------------

        //------------------------------------------------------

        stage.setOnCloseRequest(e -> {
                    stage.close();
                    homeStage.show();

                }
        );
        return stage;
    }


}




