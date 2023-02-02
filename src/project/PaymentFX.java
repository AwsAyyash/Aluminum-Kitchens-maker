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

import java.sql.ResultSet;
import java.util.ArrayList;

public class PaymentFX {


    private ArrayList<Payment> data = new ArrayList<>();
    private ObservableList<Payment> dataList;


    public PaymentFX() {
        super();
    }

    public Stage PaymentStage(Stage homeStage) throws Exception {


        Stage stage = new Stage();
        stage.setTitle("Payment Table");

        Label welcomeLabel = new Label(" welcome to payment table ");
        welcomeLabel.setFont(Font.font("Verdana", 20));
        welcomeLabel.setTextFill(Color.BLUE);
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15));

        borderPane.setTop(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.TOP_CENTER);

        Scene scene = new Scene(borderPane, 560, 520);


        String selectStatement = "select * from payment order by paymentId;";

        ResultSet resultSet = Operations.executeQueryToGetData(selectStatement);


        while (resultSet.next()) {
            Payment payment = new Payment();
            payment.setPaymentId(Integer.parseInt(resultSet.getString(1)));
            payment.setAmount(Double.parseDouble(resultSet.getString(2)));
            payment.setPaymentDate(resultSet.getString(3));
            if (resultSet.getString(4) != null)
                payment.setWorkerId(Integer.parseInt(resultSet.getString(4)));

            if (resultSet.getString(5) != null)
                payment.setSupplierId(Integer.parseInt(resultSet.getString(5)));
            if (resultSet.getString(6) != null)
                payment.setCustomerId(Integer.parseInt(resultSet.getString(6)));
            data.add(payment);
        }
        resultSet.close();


        //---------------------------
        dataList = FXCollections.observableArrayList(data);
        TableView<Payment> myDataTable = new TableView<>();
        myDataTable.setEditable(true);
        // name of column for display
        TableColumn<Payment, Integer> PaymentIdCol = new TableColumn<>("PaymentId");
        // rawMaterialIdCol.setMinWidth(50);

        // to get the data from specific column
        PaymentIdCol.setCellValueFactory(new PropertyValueFactory<>("PaymentId"));


        TableColumn<Payment, Double> PaymentAmountCol = new TableColumn<>("Amount");
        // colorCol.setMinWidth(100);
        PaymentAmountCol.setCellValueFactory(new PropertyValueFactory<>("Amount"));


        PaymentAmountCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        PaymentAmountCol.setOnEditCommit((TableColumn.CellEditEvent<Payment, Double> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setAmount(t.getNewValue()); //display only
            try {
                String updateStatement = "update Payment set Amount = '" + t.getNewValue() +
                        "' where paymentId = " + t.getRowValue().getPaymentId() + ";";
                Operations.executeStatement(updateStatement);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        TableColumn<Payment, String> PaymentDateCol = new TableColumn<>("PaymentDate");
        // colorCol.setMinWidth(100);
        PaymentDateCol.setCellValueFactory(new PropertyValueFactory<>("PaymentDate"));


        PaymentDateCol.setCellFactory(TextFieldTableCell.forTableColumn());

        PaymentDateCol.setOnEditCommit((TableColumn.CellEditEvent<Payment, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setPaymentDate(t.getNewValue()); //display only
            try {
                String updateStatement = "update Payment set PaymentDate = '" + t.getNewValue() +
                        "' where WorkerId = " + t.getRowValue().getPaymentId() + ";";
                Operations.executeStatement(updateStatement);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        TableColumn<Payment, Integer> WorkerIdCol = new TableColumn<>("WorkerId");
        WorkerIdCol.setCellValueFactory(new PropertyValueFactory<>("WorkerId"));

        // WorkerIdCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

       /* WorkerIdCol.setOnEditCommit((TableColumn.CellEditEvent<Payment, Integer> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setWorkerId(t.getNewValue()); //display only
            try {
                String updateStatement = "update Payment set WorkerId = '" + t.getNewValue() + " 'where PaymentId = "
                        + t.getRowValue().getPaymentId() + ";";

                Operations.executeStatement(updateStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/

        TableColumn<Payment, Integer> SupplierIdCol = new TableColumn<>("SupplierId");
        SupplierIdCol.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));

        // SupplierIdCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

       /* SupplierIdCol.setOnEditCommit((TableColumn.CellEditEvent<Payment, Integer> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setSupplierId(t.getNewValue()); //display only
            try {
                String updateStatement = "update Payment set SupplierId = '" + t.getNewValue() + " 'where PaymentId = "
                        + t.getRowValue().getPaymentId() + ";";

                Operations.executeStatement(updateStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/

        TableColumn<Payment, Integer> CustomerIdCol = new TableColumn<>("CustomerId");
        CustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));

        // CustomerIdCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

       /* CustomerIdCol.setOnEditCommit((TableColumn.CellEditEvent<Payment, Integer> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setCustomerId(t.getNewValue()); //display only
            try {
                String updateStatement = "update Payment set CustomerId = '" + t.getNewValue() + " 'where PaymentId = "
                        + t.getRowValue().getPaymentId() + ";";

                Operations.executeStatement(updateStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/


        myDataTable.setItems(dataList);

        myDataTable.getColumns().addAll(PaymentIdCol, PaymentAmountCol, PaymentDateCol, WorkerIdCol, SupplierIdCol, CustomerIdCol);
        borderPane.setCenter(myDataTable);
        stage.setScene(scene);

        //------------------------------------------------------
        final TextField addId = new TextField();
        addId.setPromptText("Id");
        //addId.setMaxWidth(rawMaterialIdCol.getPrefWidth());

        final TextField addAmount = new TextField();
        // addColor.setMaxWidth(colorCol.getPrefWidth());
        addAmount.setPromptText("Amount");

        final TextField addDate = new TextField();
        //addPrice.setMaxWidth(rawMaterialPriceCol.getPrefWidth());
        addDate.setPromptText("Date");


        ComboBox<String> payComboBox = new ComboBox<>();
        payComboBox.getItems().addAll("recive from Customer", " pay for Worker", "pay for supplier");
        //borderPane.setCenter(payComboBox);
        payComboBox.getSelectionModel().selectFirst();


        final TextField addSId = new TextField();
        //addPrice.setMaxWidth(rawMaterialPriceCol.getPrefWidth());
        addSId.setPromptText("ID");


        Label outputLabel = new Label("output");
        outputLabel.setFont(Font.font("Verdana", 20));
        outputLabel.setTextFill(Color.RED);
        HBox textFieldsHBox = new HBox(10);
        textFieldsHBox.getChildren().addAll(addId, addAmount, addDate, payComboBox, addSId);
        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {

            outputLabel.setText(null);

//help //
            if (!addId.getText().trim().matches("[0-9]+") && !addSId.getText().trim().matches("[0-9]+")
                    && !addAmount.getText().trim().matches("[0-9]+") && !addDate.getText().trim().matches("[0-9]+")) {
                outputLabel.setText("please enter a valid input!");

            } else {
                Payment payment = new Payment(Integer.parseInt(addId.getText()));

                if (!isExistPayment(payment)) {

                    try {

                        if (payComboBox.getSelectionModel().getSelectedIndex() == 0) {
                            payment.setCustomerId(Integer.parseInt(addSId.getText()));

                            if (isExistCustomer(payment)) {
                                payment.setPaymentDate(addDate.getText());
                                payment.setAmount(Integer.parseInt(addAmount.getText()));


                                String insertStatement = "Insert into Payment(PaymentId , Amount , PaymentDate , CustomerId) values( "
                                        + payment.getPaymentId() + ", "
                                        + Integer.parseInt(addAmount.getText()) + " , '"
                                        + addDate.getText() + " ', "
                                        + Integer.parseInt(addSId.getText()) +

                                        " );";

                                Operations.executeStatement(insertStatement);
                                dataList.add(payment);

                                myDataTable.refresh();

                            } else {
                                outputLabel.setText("this Customer id dosen't exist !");
                            }


                        } else if (payComboBox.getSelectionModel().getSelectedIndex() == 2) {
                            payment.setSupplierId(Integer.parseInt(addSId.getText()));

                            if (isExistSupplier(payment)) {
                                payment.setPaymentDate(addDate.getText());
                                payment.setAmount(Integer.parseInt(addAmount.getText()));


                                String insertStatement = "Insert into Payment (PaymentId , Amount , PaymentDate , supplierId) values( "
                                        + payment.getPaymentId() + ", "
                                        + Integer.parseInt(addAmount.getText()) + " , '"
                                        + addDate.getText() + " ', "
                                        + Integer.parseInt(addSId.getText())
                                        + " );";

                                Operations.executeStatement(insertStatement);
                                dataList.add(payment);

                                myDataTable.refresh();

                            } else {
                                outputLabel.setText("this Supplier id dosen't exist !");
                            }

                        } else {
                            payment.setWorkerId(Integer.parseInt(addSId.getText()));

                            if (isExistWorker(payment)) {
                                payment.setPaymentDate(addDate.getText());
                                payment.setAmount(Integer.parseInt(addAmount.getText()));


                                String insertStatement = "Insert into Payment (PaymentId , Amount , PaymentDate , workerId) values(  "
                                        + payment.getPaymentId() + " ,"
                                        + Integer.parseInt(addAmount.getText()) + " , "
                                        + addDate.getText() + " ', "
                                        + Integer.parseInt(addSId.getText())
                                        + " );";

                                Operations.executeStatement(insertStatement);
                                dataList.add(payment);

                                myDataTable.refresh();
                            } else {
                                outputLabel.setText("this worker id dosen't exist !");

                            }


                        }


                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                } else {

                    outputLabel.setText("Exists!, can't be inserted!");
                }
            }
            addId.clear();
            addAmount.clear();
            addDate.clear();
            addSId.clear();


        });
        final Button deleteButton = new Button("Delete");

        deleteButton.setOnAction((ActionEvent e) -> {

            ObservableList<Payment> selectedRows = myDataTable.getSelectionModel().getSelectedItems();
            ArrayList<Payment> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                myDataTable.getItems().remove(row);
                try {

                    String deleteStatement = "delete from  Payment where PaymentId = " + row.getPaymentId() + ";";
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

    private void confirmStage(TableView<Payment> table) {
        Stage stage = new Stage();


        Button yesButton = new Button("Confirm");
        yesButton.setOnAction(e -> {
            for (Payment row : dataList) {
                try {
                    String deleteStatement = "delete from  Payment where PaymentId = " + row.getPaymentId() + ";";

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

    private boolean isExistWorker(Payment payment) {

        String selectStat = "SELECT workerId from Worker where workerId = " + payment.getWorkerId() + "; ";

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


    private boolean isExistCustomer(Payment payment) {

        String selectStat = "SELECT CustomerId from Customer where CustomerId = " + payment.getCustomerId() + "; ";

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


    private boolean isExistSupplier(Payment payment) {

        String selectStat = "SELECT SupplierId from Supplier where SupplierId = " + payment.getSupplierId() + "; ";

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


    private boolean isExistPayment(Payment payment) {

        String selectStat = "SELECT PaymentId from Payment where PaymentId = " + payment.getPaymentId() + "; ";

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

}

