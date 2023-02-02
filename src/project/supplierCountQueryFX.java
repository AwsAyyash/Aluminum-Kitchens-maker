package project;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class supplierCountQueryFX {

    private Connection con;
    private ArrayList<supplierCountQuery> data = new ArrayList<>();
    private ObservableList<supplierCountQuery> dataList;


    public supplierCountQueryFX() {
    }

    public Stage supplierCountQueryStage(Stage homeStage) throws Exception {

        Stage stage = new Stage();
        stage.setTitle("Number of Raw Material supplied by supplier Table");

        Label welcomeLabel = new Label("Welcome to Number of Raw Material supplied by supplier Table Table!");
        welcomeLabel.setFont(Font.font("Verdana", 20));
        welcomeLabel.setTextFill(Color.BLUE);
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15));

        borderPane.setTop(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.TOP_CENTER);

        Scene scene = new Scene(borderPane, 400, 400);


        String selectStatement = "select R.supplierId, count(*)" +
                "from RawMaterial R\n" +
                "group by R.supplierId\n" +
                "order by 2 desc;";

        ResultSet resultSet = Operations.executeQueryToGetData(selectStatement);


        while (resultSet.next())
            data.add(new supplierCountQuery(
                    Integer.parseInt(resultSet.getString(1)),
                    Integer.parseInt(resultSet.getString(2))));


        resultSet.close();


        //---------------------------
        dataList = FXCollections.observableArrayList(data);
        TableView<supplierCountQuery> myDataTable = new TableView<>();
        myDataTable.setEditable(true);
        // name of column for display
        TableColumn<supplierCountQuery, Integer> supplierIdCol = new TableColumn<>("supplierId");
        supplierIdCol.setMinWidth(50);

        // to get the data from specific column
        supplierIdCol.setCellValueFactory(new PropertyValueFactory<>("supplierId"));


        TableColumn<supplierCountQuery, Integer> countCol = new TableColumn<>("count");
        countCol.setMinWidth(50);
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        countCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        myDataTable.setItems(dataList);

        myDataTable.getColumns().addAll(supplierIdCol, countCol);
        borderPane.setCenter(myDataTable);
        stage.setScene(scene);


        //------------------------------------------------------

        stage.setOnCloseRequest(e -> {
                    stage.close();
                    homeStage.show();

                }
        );
        myDataTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return stage;
    }


}




