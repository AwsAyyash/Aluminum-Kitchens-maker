package project;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.sql.ResultSet;
import java.util.ArrayList;

public class SupplierFX {

    private ArrayList<Supplier> data = new ArrayList<>();
    private ObservableList<Supplier> dataList;


    public SupplierFX() {
    }

    public Stage SupplierStage(Stage homeStage) throws Exception {

        Stage stage = new Stage();
        stage.setTitle("Supplier Table");

        Label welcomeLabel = new Label("Welcome to Supplier Table!");
        welcomeLabel.setFont(Font.font("Verdana", 20));
        welcomeLabel.setTextFill(Color.BLUE);
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15));

        borderPane.setTop(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.TOP_CENTER);

        Scene scene = new Scene(borderPane, 400, 400);


        String selectStatement = "select supplierId,supplierLocation,supplierPhoneNum from Supplier order by supplierId;";

        ResultSet resultSet = Operations.executeQueryToGetData(selectStatement);


        while (resultSet.next())
            data.add(new Supplier(
                    Integer.parseInt(resultSet.getString(1)),
                    resultSet.getString(2),
                    Integer.parseInt(resultSet.getString(3))));


        resultSet.close();


        //---------------------------
        dataList = FXCollections.observableArrayList(data);
        TableView<Supplier> myDataTable = new TableView<>();
        myDataTable.setEditable(true);
        // name of column for display
        TableColumn<Supplier, Integer> supplierIdCol = new TableColumn<>("supplierId");
        supplierIdCol.setMinWidth(50);

        // to get the data from specific column
        supplierIdCol.setCellValueFactory(new PropertyValueFactory<>("supplierId"));


        TableColumn<Supplier, String> supplierLocationCol = new TableColumn<>("supplierLocation");
        supplierLocationCol.setMinWidth(100);
        supplierLocationCol.setCellValueFactory(new PropertyValueFactory<>("supplierLocation"));


        supplierLocationCol.setCellFactory(TextFieldTableCell.forTableColumn());

        supplierLocationCol.setOnEditCommit((TableColumn.CellEditEvent<Supplier, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setSupplierLocation(t.getNewValue()); //display only
            try {
                String updateStatement = "update Supplier set supplierLocation = '" + t.getNewValue() +
                        "' where supplierId = " + t.getRowValue().getSupplierId() + ";";
                Operations.executeStatement(updateStatement);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        TableColumn<Supplier, Integer> supplierPhoneNumCol = new TableColumn<>("supplierPhoneNum");
        supplierPhoneNumCol.setMinWidth(50);
        supplierPhoneNumCol.setCellValueFactory(new PropertyValueFactory<>("supplierPhoneNum"));

        supplierPhoneNumCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        supplierPhoneNumCol.setOnEditCommit((TableColumn.CellEditEvent<Supplier, Integer> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setSupplierPhoneNum(t.getNewValue()); //display only
            try {
                String updateStatement = "update Supplier set supplierPhoneNum = " + t.getNewValue() + " where supplierId = "
                        + t.getRowValue().getSupplierId() + ";";

                Operations.executeStatement(updateStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        myDataTable.setItems(dataList);

        myDataTable.getColumns().addAll(supplierIdCol, supplierLocationCol, supplierPhoneNumCol);
        borderPane.setCenter(myDataTable);
        stage.setScene(scene);

        //------------------------------------------------------
        final TextField addId = new TextField();
        addId.setPromptText("supplierId");
        addId.setMaxWidth(supplierIdCol.getPrefWidth());

        final TextField addLocation = new TextField();
        addLocation.setMaxWidth(supplierLocationCol.getPrefWidth());
        addLocation.setPromptText("supplierLocation");

        final TextField addPhone = new TextField();
        addPhone.setMaxWidth(supplierPhoneNumCol.getPrefWidth());
        addPhone.setPromptText("supplierPhoneNum");


        Label outputLabel = new Label("output");
        outputLabel.setFont(Font.font("Verdana", 20));
        outputLabel.setTextFill(Color.RED);
        HBox textFieldsHBox = new HBox(10);
        textFieldsHBox.getChildren().addAll(addId, addLocation, addPhone);
        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {

            // if (!addId.getText().trim().matches("[0-9]+") && !addColor.getText().trim().matches("[a-zA-Z]+")
            //       && !addPrice.getText().trim().matches("[0-9]+||[0-9]+.\\d{2}")) {
            Supplier rc = new Supplier(
                    Integer.parseInt(addId.getText()),
                    addLocation.getText(),
                    Integer.parseInt(addPhone.getText()));


            if (!isExist(rc)) {

                try {

                    String insertStatement = "Insert into Supplier (supplierId, supplierLocation,  supplierPhoneNum) values("
                            + rc.getSupplierId() + ",'"
                            + rc.getSupplierLocation() + "',"
                            + rc.getSupplierPhoneNum() + ");";


                    Operations.executeStatement(insertStatement);
                    dataList.add(rc);

                    myDataTable.refresh();

                    // outputLabel.setText("The data is inserted!");

                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            } else {

                outputLabel.setText("Exists!, can't be inserted!");
            }
            addId.clear();
            addLocation.clear();
            addPhone.clear();
            //}
        });
        final Button deleteButton = new Button("Delete");

        deleteButton.setOnAction((ActionEvent e) -> {

            ObservableList<Supplier> selectedRows = myDataTable.getSelectionModel().getSelectedItems();
            ArrayList<Supplier> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                myDataTable.getItems().remove(row);
                try {


                    String deleteStatement = "delete from  Supplier  where supplierId = " + row.getSupplierId() + ";";
                    Operations.executeStatement(deleteStatement);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                myDataTable.refresh();
            });
        });
        final Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction((ActionEvent e) -> myDataTable.refresh());
        final Button clearButton = new Button("Clear All");
        clearButton.setOnAction((ActionEvent e) -> confirmStage(myDataTable));

        VBox buttonsVBox = new VBox(10);
        buttonsVBox.getChildren().addAll(textFieldsHBox, addButton, deleteButton, clearButton, refreshButton, outputLabel);

        borderPane.setPadding(new Insets(10));
        borderPane.setBottom(buttonsVBox);
        BorderPane.setMargin(buttonsVBox, new Insets(10));
        BorderPane.setMargin(welcomeLabel, new Insets(10));

        //------------------------------------------------------

        stage.setOnCloseRequest(e -> {
                    stage.close();
                    homeStage.show();

                }
        );
        myDataTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return stage;
    }


    private boolean isExist(Supplier rc) {


        int i = 0;
        while (i < dataList.size()) {
            if (rc.getSupplierId() == dataList.get(i).getSupplierId()) {
                return true;
            }
            i++;
        }
        return false;
    }


    private void confirmStage(TableView<Supplier> table) {
        Stage stage = new Stage();


        Button yesButton = new Button("Confirm");
        yesButton.setOnAction(e -> {
            for (Supplier row : dataList) {
                try {
                    String deleteStatement = "delete from  Supplier where supplierId = " + row.getSupplierId() + ";";

                    Operations.executeStatement(deleteStatement);

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                table.refresh();
            }
            table.getItems().removeAll(dataList);
            stage.close();

        });
        BorderPane borderPane = new BorderPane();
        Label label = new Label("Are you sure to delete everything!, please confirm");
        label.setFont(Font.font("Verdana", 20));

        label.setTextFill(Color.RED);

        borderPane.setTop(label);
        BorderPane.setAlignment(label, Pos.TOP_CENTER);

        borderPane.setCenter(yesButton);
        BorderPane.setAlignment(yesButton, Pos.CENTER);
        Scene scene = new Scene(borderPane, 200, 200);
        stage.setScene(scene);
        stage.show();

    }

}

