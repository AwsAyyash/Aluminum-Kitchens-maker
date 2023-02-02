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

public class CityCountQueryFX {


    private Connection con;
    private ArrayList<CityCountQuery> data = new ArrayList<>();
    private ObservableList<CityCountQuery> dataList;


    public CityCountQueryFX() {
    }

    public Stage CityCountQueryStage(Stage homeStage) throws Exception {

        Stage stage = new Stage();
        stage.setTitle("Number of Suppliers in City");

        Label welcomeLabel = new Label("Number of Suppliers in City:");
        welcomeLabel.setFont(Font.font("Verdana", 20));
        welcomeLabel.setTextFill(Color.BLUE);
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15));

        borderPane.setTop(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.TOP_CENTER);

        Scene scene = new Scene(borderPane, 400, 400);


        String selectStatement = "SELECT  S.supplierLocation, count(*) \n" +
                "FROM supplier S\n" +
                "group by S.supplierLocation\n" +
                "order by 2 desc;";

        ResultSet resultSet = Operations.executeQueryToGetData(selectStatement);


        while (resultSet.next())
            data.add(new CityCountQuery(
                    resultSet.getString(1),
                    Integer.parseInt(resultSet.getString(2))));


        resultSet.close();


        //---------------------------
        dataList = FXCollections.observableArrayList(data);
        TableView<CityCountQuery> myDataTable = new TableView<>();
        myDataTable.setEditable(true);
        // name of column for display
        TableColumn<CityCountQuery, String> supplierLocationCol = new TableColumn<>("supplierLocation");
        supplierLocationCol.setMinWidth(50);

        // to get the data from specific column
        supplierLocationCol.setCellValueFactory(new PropertyValueFactory<>("supplierLocation"));


        TableColumn<CityCountQuery, Integer> countCol = new TableColumn<>("count");
        countCol.setMinWidth(50);
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        countCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        myDataTable.setItems(dataList);

        myDataTable.getColumns().addAll(supplierLocationCol, countCol);
        borderPane.setCenter(myDataTable);
        stage.setScene(scene);

        //------------------------------------------------------

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




