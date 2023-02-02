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

public class WorkerFX {

    private ArrayList<Worker> data = new ArrayList<>();
    private ObservableList<Worker> dataList;


    public WorkerFX() {

    }

    public Stage WorkerStage(Stage homeStage) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Workers Employment");

        Label welcomeLabel = new Label("Welcome to Workers Employment");
        welcomeLabel.setFont(Font.font("Verdana", 20));
        welcomeLabel.setTextFill(Color.BLUE);
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15));

        borderPane.setTop(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.TOP_CENTER);

        Scene scene = new Scene(borderPane, 560, 520);


        String selectStatement = "select workerID,workerName,age,workerPhoneNum,insuranceNum,employmentDate from Worker order by WorkerID;";

        /* workerID,
              String workerName,
              int age,
              int workerPhoneNum,
              int insuranceNum,
              String employmentDate*/
        ResultSet resultSet = Operations.executeQueryToGetData(selectStatement);


        while (resultSet.next())
            data.add(new Worker(
                    Integer.parseInt(resultSet.getString(1)),
                    resultSet.getString(2),
                    Integer.parseInt(resultSet.getString(3)),
                    Integer.parseInt(resultSet.getString(4)),
                    Integer.parseInt(resultSet.getString(5)),
                    resultSet.getString(6)
            ));

        resultSet.close();


        //---------------------------
        dataList = FXCollections.observableArrayList(data);
        TableView<Worker> myDataTable = new TableView<>();
        myDataTable.setEditable(true);
        // name of column for display
        TableColumn<Worker, Integer> WorkerIdCol = new TableColumn<>("WorkerID");
        // rawMaterialIdCol.setMinWidth(50);

        // to get the data from specific column
        WorkerIdCol.setCellValueFactory(new PropertyValueFactory<>("WorkerID"));


        TableColumn<Worker, String> WorkerNameCol = new TableColumn<>("WorkerName");
        // colorCol.setMinWidth(100);
        WorkerNameCol.setCellValueFactory(new PropertyValueFactory<>("WorkerName"));


        WorkerNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        WorkerNameCol.setOnEditCommit((TableColumn.CellEditEvent<Worker, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setWorkerName(t.getNewValue()); //display only
            try {
                String updateStatement = "update Worker set WorkerName = '" + t.getNewValue() +
                        "' where WorkerId = " + t.getRowValue().getWorkerID() + ";";
                Operations.executeStatement(updateStatement);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        TableColumn<Worker, Integer> ageCol = new TableColumn<>("age");
        // rawMaterialPriceCol.setMinWidth(50);
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        ageCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        ageCol.setOnEditCommit((TableColumn.CellEditEvent<Worker, Integer> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setAge(t.getNewValue()); //display only
            try {
                String updateStatement = "update Worker set age = '" + t.getNewValue() + " 'where WorkerId = "
                        + t.getRowValue().getWorkerID() + ";";

                Operations.executeStatement(updateStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        TableColumn<Worker, Integer> WorkerPhoneNumCol = new TableColumn<>("WorkerPhoneNum");
        // colorCol.setMinWidth(100);
        WorkerPhoneNumCol.setCellValueFactory(new PropertyValueFactory<>("WorkerPhoneNum"));


        WorkerPhoneNumCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        WorkerPhoneNumCol.setOnEditCommit((TableColumn.CellEditEvent<Worker, Integer> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setWorkerPhoneNum(t.getNewValue()); //display only
            try {
                String updateStatement = "update Worker set workerPhoneNum = '" + t.getNewValue() +
                        " ' where WorkerID = " + t.getRowValue().getWorkerID() + ";";
                Operations.executeStatement(updateStatement);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        TableColumn<Worker, Integer> WorkerInsuranceNumCol = new TableColumn<>("InsuranceNum");
        // colorCol.setMinWidth(100);
        WorkerInsuranceNumCol.setCellValueFactory(new PropertyValueFactory<>("InsuranceNum"));


        WorkerInsuranceNumCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        WorkerInsuranceNumCol.setOnEditCommit((TableColumn.CellEditEvent<Worker, Integer> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setInsuranceNum(t.getNewValue()); //display only
            try {
                String updateStatement = "update Worker set insuranceNum =  " + t.getNewValue() +
                        "              where WorkerID = " + t.getRowValue().getWorkerID() + ";";
                Operations.executeStatement(updateStatement);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        TableColumn<Worker, String> employmentDateCol = new TableColumn<>("employmentDate");
        // colorCol.setMinWidth(100);
        employmentDateCol.setCellValueFactory(new PropertyValueFactory<>("employmentDate"));


        employmentDateCol.setCellFactory(TextFieldTableCell.forTableColumn());

        employmentDateCol.setOnEditCommit((TableColumn.CellEditEvent<Worker, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmploymentDate(t.getNewValue()); //display only
            try {
                String updateStatement = "update Worker set employmentDate = '" + t.getNewValue() +
                        "' where WorkerID = " + t.getRowValue().getWorkerID() + ";";
                Operations.executeStatement(updateStatement);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        myDataTable.setItems(dataList);

        myDataTable.getColumns().addAll(WorkerIdCol, WorkerNameCol, ageCol, WorkerPhoneNumCol, WorkerInsuranceNumCol, employmentDateCol);
        borderPane.setCenter(myDataTable);
        stage.setScene(scene);

        //------------------------------------------------------
        final TextField addId = new TextField();
        addId.setPromptText("Id");
        //addId.setMaxWidth(rawMaterialIdCol.getPrefWidth());

        final TextField addName = new TextField();
        // addColor.setMaxWidth(colorCol.getPrefWidth());
        addName.setPromptText("Name");

        final TextField addAge = new TextField();
        //addPrice.setMaxWidth(rawMaterialPriceCol.getPrefWidth());
        addAge.setPromptText("Age");


        final TextField addPhoneNum = new TextField();
        //addPrice.setMaxWidth(rawMaterialPriceCol.getPrefWidth());
        addPhoneNum.setPromptText("PhoneNum");

        final TextField addInsuranceNum = new TextField();
        //addPrice.setMaxWidth(rawMaterialPriceCol.getPrefWidth());
        addInsuranceNum.setPromptText("InsuranceNum");

        final TextField addEmploymentDate = new TextField();
        //addPrice.setMaxWidth(rawMaterialPriceCol.getPrefWidth());
        addEmploymentDate.setPromptText("EmploymentDate");

        Label outputLabel = new Label("output");
        outputLabel.setFont(Font.font("Verdana", 20));
        outputLabel.setTextFill(Color.RED);
        HBox textFieldsHBox = new HBox(10);
        textFieldsHBox.getChildren().addAll(addId, addName, addAge, addPhoneNum, addInsuranceNum, addEmploymentDate);
        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {

            outputLabel.setText(null);

// help //
            if (!addId.getText().trim().matches("[0-9]+") && !addName.getText().trim().matches("[a-zA-Z]*")
                    && !addAge.getText().trim().matches("[0-9]+") && !addPhoneNum.getText().trim().matches("[0-9]+") &&
                    !addInsuranceNum.getText().trim().matches("[0-9]+")) {
                outputLabel.setText("please enter a valid input!");

            } else {
                Worker Worker = new Worker(
                        Integer.parseInt(addId.getText()),
                        addName.getText(),
                        Integer.parseInt(addAge.getText()),
                        Integer.parseInt(addPhoneNum.getText()),
                        Integer.parseInt(addInsuranceNum.getText()),
                        addEmploymentDate.getText()
                );


                if (!isExist(Worker)) {

                    try {


                        String insertStatement = "Insert into Worker (workerId, WorkerName, age , WorkerPhoneNum , insuranceNum , employmentDate) values( "
                                + Worker.getWorkerID() + " ,'"
                                + Worker.getWorkerName() + " ', "
                                + Worker.getAge() + " , "
                                + Worker.getWorkerPhoneNum() + " , "
                                + Worker.getInsuranceNum() + " , '"
                                + Worker.getEmploymentDate() +

                                " ');";

                        Operations.executeStatement(insertStatement);
                        dataList.add(Worker);

                        myDataTable.refresh();


                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                } else {

                    outputLabel.setText("Exists!, can't be inserted!");
                }
            }
            addId.clear();
            addName.clear();
            addAge.clear();
            addPhoneNum.clear();
            addInsuranceNum.clear();
            addEmploymentDate.clear();


            //}
        });
        final Button deleteButton = new Button("Delete");

        deleteButton.setOnAction((ActionEvent e) -> {

            ObservableList<Worker> selectedRows = myDataTable.getSelectionModel().getSelectedItems();
            ArrayList<Worker> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                myDataTable.getItems().remove(row);
                try {

                    String deleteStatement = "delete from  Worker where WorkerId = " + row.getWorkerID() + ";";
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


    private boolean isExist(Worker worker) {


        int i = 0;
        while (i < dataList.size()) {
            if (worker.getWorkerID() == dataList.get(i).getWorkerID()) {
                return true;
            }
            i++;
        }
        return false;
    }

    private void confirmStage(TableView<Worker> table) {
        Stage stage = new Stage();


        Button yesButton = new Button("Confirm");
        yesButton.setOnAction(e -> {
            for (Worker row : dataList) {
                try {
                    String deleteStatement = "delete from  Worker where WorkerId = " + row.getWorkerID() + ";";

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
        Scene scene = new Scene(borderPane, 600, 300);
        stage.setScene(scene);
        stage.show();

    }


}

