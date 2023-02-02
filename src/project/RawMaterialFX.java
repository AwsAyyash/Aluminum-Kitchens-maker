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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RawMaterialFX {

    private Connection con;
    private ArrayList<RawMaterial> data = new ArrayList<>();
    private ObservableList<RawMaterial> dataList;
    private ArrayList<Integer> suppId = new ArrayList<>();

    public RawMaterialFX() {
    }

    public Stage rawMaterialStage(Stage homeStage) throws Exception {

        Stage stage = new Stage();
        stage.setTitle("Raw Material Table");

        Label welcomeLabel = new Label("Welcome to Raw Material Table!");
        welcomeLabel.setFont(Font.font("Verdana", 20));
        welcomeLabel.setTextFill(Color.BLUE);
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15));

        borderPane.setTop(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.TOP_CENTER);

        Scene scene = new Scene(borderPane, 400, 400);


        String selectStatement = "select rawMaterialId,color,rawMaterialPrice, supplierId from RawMaterial order by rawMaterialId;";


        ResultSet resultSet = Operations.executeQueryToGetData(selectStatement);


        while (resultSet.next())
            data.add(new RawMaterial(
                    Integer.parseInt(resultSet.getString(1)),
                    resultSet.getString(2),
                    Double.parseDouble(resultSet.getString(3)),
                    Integer.parseInt((resultSet.getString(4)))));

        resultSet.close();
        //-------------------------
        String suppselectStatement = "select supplierId from Supplier;";
        ResultSet suppresultSet = Operations.executeQueryToGetData(suppselectStatement);

        while (suppresultSet.next())
            suppId.add(Integer.parseInt(suppresultSet.getString(1)));
        suppresultSet.close();


        //---------------------------
        dataList = FXCollections.observableArrayList(data);
        TableView<RawMaterial> myDataTable = new TableView<>();
        myDataTable.setEditable(true);
        // name of column for display
        TableColumn<RawMaterial, Integer> rawMaterialIdCol = new TableColumn<>("rawMaterialId");
        rawMaterialIdCol.setMinWidth(50);

        // to get the data from specific column
        rawMaterialIdCol.setCellValueFactory(new PropertyValueFactory<>("rawMaterialId"));


        TableColumn<RawMaterial, String> colorCol = new TableColumn<>("color");
        colorCol.setMinWidth(100);
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));


        colorCol.setCellFactory(TextFieldTableCell.forTableColumn());

        colorCol.setOnEditCommit((TableColumn.CellEditEvent<RawMaterial, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setColor(t.getNewValue()); //display only
            try {
                String updateStatement = "update RawMaterial set color = '" + t.getNewValue() +
                        "' where rawMaterialId = " + t.getRowValue().getRawMaterialId() + ";";
                Operations.executeStatement(updateStatement);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        TableColumn<RawMaterial, Double> rawMaterialPriceCol = new TableColumn<>("rawMaterialPrice");
        rawMaterialPriceCol.setMinWidth(50);
        rawMaterialPriceCol.setCellValueFactory(new PropertyValueFactory<>("rawMaterialPrice"));

        rawMaterialPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        rawMaterialPriceCol.setOnEditCommit((TableColumn.CellEditEvent<RawMaterial, Double> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setRawMaterialPrice(t.getNewValue()); //display only
            try {
                String updateStatement = "update RawMaterial set rawMaterialPrice = " + t.getNewValue() + " where rawMaterialId = "
                        + t.getRowValue().getRawMaterialId() + ";";

                Operations.executeStatement(updateStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        TableColumn<RawMaterial, Integer> supplierIDCol = new TableColumn<>("supplierId");
        supplierIDCol.setMinWidth(50);
        supplierIDCol.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        supplierIDCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        supplierIDCol.setOnEditCommit((TableColumn.CellEditEvent<RawMaterial, Integer> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setSupplierId(t.getNewValue()); //display only
            try {
                String updateStatement = "update RawMaterial set supplierId = " + t.getNewValue() + " where rawMaterialId = "
                        + t.getRowValue().getRawMaterialId() + ";";

                Operations.executeStatement(updateStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        myDataTable.setItems(dataList);

        myDataTable.getColumns().addAll(rawMaterialIdCol, colorCol, rawMaterialPriceCol, supplierIDCol);
        borderPane.setCenter(myDataTable);
        stage.setScene(scene);

        //------------------------------------------------------
        final TextField addId = new TextField();
        addId.setPromptText("rawMaterialId");
        addId.setMaxWidth(rawMaterialIdCol.getPrefWidth());

        final TextField addColor = new TextField();
        addColor.setMaxWidth(colorCol.getPrefWidth());
        addColor.setPromptText("color");

        final TextField addPrice = new TextField();
        addPrice.setMaxWidth(rawMaterialPriceCol.getPrefWidth());
        addPrice.setPromptText("rawMaterialPrice");

        final TextField addsupplierID = new TextField();
        addsupplierID.setMaxWidth(supplierIDCol.getPrefWidth());
        addsupplierID.setPromptText("supplierId");

        Label outputLabel = new Label(" ");
        outputLabel.setFont(Font.font("Verdana", 20));
        outputLabel.setTextFill(Color.RED);
        HBox textFieldsHBox = new HBox(10);
        textFieldsHBox.getChildren().addAll(addId, addColor, addPrice, addsupplierID);
        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            outputLabel.setText(" ");

            // if (!addId.getText().trim().matches("[0-9]+") && !addColor.getText().trim().matches("[a-zA-Z]+")
            //       && !addPrice.getText().trim().matches("[0-9]+||[0-9]+.\\d{2}")) {
            RawMaterial rc = new RawMaterial(
                    Integer.parseInt(addId.getText()),
                    addColor.getText(),
                    Double.parseDouble(addPrice.getText()),
                    Integer.parseInt((addsupplierID.getText())));


            if ((!isExist(rc)) && (isSupplierExist(rc))) {

                try {

                    String insertStatement = "Insert into RawMaterial (rawMaterialId, color, rawMaterialPrice, supplierId) values("
                            + rc.getRawMaterialId() + ",'"
                            + rc.getColor() + "',"
                            + rc.getRawMaterialPrice() + "," +
                            rc.getSupplierId() + ");";

                    Operations.executeStatement(insertStatement);
                    dataList.add(rc);

                    myDataTable.refresh();

                    // outputLabel.setText("The data is inserted!");

                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            } else if (isExist(rc)) {

                outputLabel.setText("Raw Material Already Exists!");
            } else {
                outputLabel.setText("Wrong Supplier!!");
            }
            addId.clear();
            addColor.clear();
            addPrice.clear();
            addsupplierID.clear();
        });
        final Button deleteButton = new Button("Delete");

        deleteButton.setOnAction((ActionEvent e) -> {

            ObservableList<RawMaterial> selectedRows = myDataTable.getSelectionModel().getSelectedItems();
            ArrayList<RawMaterial> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                myDataTable.getItems().remove(row);
                try {

                    String deleteStatement = "delete from  RawMaterial where rawMaterialId = " + row.getRawMaterialId() + ";";
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


    private boolean isExist(RawMaterial rc) {

        int i = 0;
        while (i < dataList.size()) {
            if (rc.getRawMaterialId() == dataList.get(i).getRawMaterialId()) {
                return true;
            }
            i++;
        }
        return false;
    }

    private boolean isSupplierExist(RawMaterial rc) {


        int i = 0;
        while (i < suppId.size()) {
            if (rc.getSupplierId() == suppId.get(i)) {
                return true;
            }
            i++;
        }
        return false;
    }

    private void confirmStage(TableView<RawMaterial> table) {
        Stage stage = new Stage();


        Button yesButton = new Button("Confirm");
        yesButton.setOnAction(e -> {
            for (RawMaterial row : dataList) {
                try {
                    String deleteStatement = "delete from  RawMaterial where rawMaterialId = " + row.getRawMaterialId() + ";";

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

