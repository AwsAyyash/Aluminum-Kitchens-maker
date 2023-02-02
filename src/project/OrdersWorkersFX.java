package project;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;


public class OrdersWorkersFX {

    private ArrayList<OrdersToWorkers> data = new ArrayList<>();
    private ObservableList<OrdersToWorkers> dataList;

    private TextField addWorkerId = new TextField() ;
    private TextField addOrderId = new TextField() ;

    public OrdersWorkersFX() {
    }

    public Stage OrdersWorkersStage (Stage homeStage) throws Exception
    {


        Stage stage = new Stage();
        stage.setTitle("Workers and Orders");

        Label welcomeLabel = new Label("Workers to Orders Table");
        welcomeLabel.setFont(Font.font("Verdana", 20));
        welcomeLabel.setTextFill(Color.BLUE);
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15));

        borderPane.setTop(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.TOP_CENTER);

        Scene scene = new Scene(borderPane, 930, 650);


        String selectStatement = "SELECT " +
                "* from orderstoworkers" +
                " ;";

        ResultSet resultSet = Operations.executeQueryToGetData(selectStatement);


        //resultSet.getString(1);
        while (resultSet.next()) {

            data.add(new OrdersToWorkers(
                    Integer.parseInt(resultSet.getString(1)),
                    Integer.parseInt(resultSet.getString(2))
            ));
        }
        resultSet.close();


        //---------------------------
        dataList = FXCollections.observableArrayList(data);
        TableView<OrdersToWorkers> myDataTable = new TableView<>();
        myDataTable.setEditable(true);
        // name of column for display
        TableColumn<OrdersToWorkers, Integer> WorkerIdCol = new TableColumn<>("workerId");
        WorkerIdCol.setMinWidth(50);

        // to get the data from specific column
        WorkerIdCol.setCellValueFactory(new PropertyValueFactory<>("WorkerId"));

        TableColumn<OrdersToWorkers, Integer> orderIdCol = new TableColumn<>("OrderId");
        orderIdCol.setMinWidth(50);

        // to get the data from specific column
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("OrderId"));


        myDataTable.setItems(dataList);

        myDataTable.getColumns().addAll(WorkerIdCol, orderIdCol);
        borderPane.setCenter(myDataTable);
        stage.setScene(scene);

        //------------------------------------------------------
        addWorkerId.setPromptText("Worker Id");
        // addCustomerId.setMaxWidth(customerIdCol.getPrefWidth())

        addOrderId.setPromptText("Order Id");


        Label outputLabel = new Label("output");
        outputLabel.setFont(Font.font("Verdana", 20));
        outputLabel.setTextFill(Color.RED);

        HBox textFieldsHBox = new HBox(10);
        textFieldsHBox.getChildren().addAll(addWorkerId, addOrderId);

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {

            outputLabel.setText(null);

            if (!isValid()) {
                outputLabel.setText("please enter a valid input!");

            } else {

                OrdersToWorkers OTW = new OrdersToWorkers(
                        Integer.parseInt(addWorkerId.getText()),
                        Integer.parseInt(addOrderId.getText())
                );




                if (isExistWorker(OTW) && isExistOrder(OTW)) {

                    try {


                        String insertStatOrderToWorker = "insert into orderstoworkers values(  "
                                + OTW.getWorkerId() + " , "
                                + OTW.getOrderId() + " ); " ;

                        Operations.executeStatement(insertStatOrderToWorker);

                        dataList.add(OTW);

                        myDataTable.refresh();


                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                } else {

                    outputLabel.setText("Order ID or Worker ID dosent exist , make sure that they both exist");
                }
            }
            addWorkerId.clear();
            addOrderId.clear();



        });
        final Button deleteButton = new Button("Delete");

        deleteButton.setOnAction((ActionEvent e) -> {

            ObservableList<OrdersToWorkers> selectedRows = myDataTable.getSelectionModel().getSelectedItems();
            ArrayList<OrdersToWorkers> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                myDataTable.getItems().remove(row);
                try {

                    String deleteStatement = "delete from  OrdersToWorkers where orderId =  '" + row.getOrderId() +  " 'AND " + "workerId =' "
                            +row.getWorkerId() +" ';" ;
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

    private void confirmStage(TableView<OrdersToWorkers> table) {
        Stage stage = new Stage();


        Button yesButton = new Button("Confirm");
        yesButton.setOnAction(e -> {
            for (OrdersToWorkers row : dataList) {
                try {
                    String deleteStatement = "delete from  OrdersToWorkers where orderid = " + row.getOrderId() + ";";

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
        Scene scene = new Scene(borderPane, 600, 310);
        stage.setScene(scene);
        stage.show();


    }

    private boolean isExistWorker(OrdersToWorkers OW)  {

        String selectStat = "SELECT workerId from Worker where workerId = " + OW.getWorkerId() + "; ";

        try {
            ResultSet resultSet = Operations.executeQueryToGetData(selectStat);

            resultSet.next(); // to point on the first row!
            if (resultSet.getString(1) != null)
                return true;
        } catch (Exception e) {
            System.out.println(e);
        }


        return false;
    }



    private boolean isExistOrder(OrdersToWorkers OTW) {


        String selectStat = "SELECT OrderId from Orders where OrderId = " + OTW.getOrderId() + "; ";

        try {
            ResultSet resultSet = Operations.executeQueryToGetData(selectStat);

            resultSet.next(); // to point on the first row!
            if (resultSet.getString(1) != null)
                return true;
        } catch (Exception e) {
            System.out.println(e);
        }


        return false;
    }


    private boolean isValid() {

        boolean valid = ((addWorkerId.getText().trim().matches("[0-9]+")) &&
                (addOrderId.getText().trim().matches("[0-9]+"))   );
        return valid;
    }



}

