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

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class CustomerOrdersFX {


    private ArrayList<CustomerAndOrders> data = new ArrayList<>();
    private ObservableList<CustomerAndOrders> dataList;

    private TextField addCustomerId = new TextField();
    private TextField addPhoneNum = new TextField();
    private TextField addCustomerName = new TextField();
    private TextField addOrderId = new TextField();
    private TextField addPrice = new TextField();
    private TextField addDeliveryLocation = new TextField();
    private TextField addSize = new TextField();
    private TextField addRawMaterialId = new TextField();

    public CustomerOrdersFX() {
    }

    public Stage CustomerOrdersStage(Stage homeStage) throws Exception {

        Stage stage = new Stage();
        stage.setTitle("Customer makes Orders");

        Label welcomeLabel = new Label("Customer makes Orders Table!");
        welcomeLabel.setFont(Font.font("Verdana", 20));
        welcomeLabel.setTextFill(Color.BLUE);
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15));

        borderPane.setTop(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.TOP_CENTER);

        Scene scene = new Scene(borderPane, 930, 650);


        String selectStatement = "SELECT " +
                "c.customerId,phoneNum,customerName,orderId, price, orderDate, deliveryLocation, size,RawMaterialId FROM customer c , orders o" +
                " WHERE c.customerId = o.customerId;";

        ResultSet resultSet = Operations.executeQueryToGetData(selectStatement);


        //resultSet.getString(1);
        while (resultSet.next()) {
            //Integer cl9=  Integer.parseInt(resultSet.getString(9);
            //if (cl9==null)

            data.add(new CustomerAndOrders(
                    Integer.parseInt(resultSet.getString(1)),
                    resultSet.getString(2),
                    (resultSet.getString(3)),
                    Integer.parseInt(resultSet.getString(4)),
                    Double.parseDouble(resultSet.getString(5)),
                    resultSet.getString(6), // :6: date but as string!
                    resultSet.getString(7),
                    Double.parseDouble(resultSet.getString(8)),
                    Integer.parseInt(resultSet.getString(9))

            ));
        }
        resultSet.close();


        //---------------------------
        dataList = FXCollections.observableArrayList(data);
        TableView<CustomerAndOrders> myDataTable = new TableView<>();
        myDataTable.setEditable(true);
        // name of column for display
        TableColumn<CustomerAndOrders, Integer> customerIdCol = new TableColumn<>("customerId");
        customerIdCol.setMinWidth(50);

        // to get the data from specific column
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        TableColumn<CustomerAndOrders, Integer> orderIdCol = new TableColumn<>("orderId");
        orderIdCol.setMinWidth(50);

        // to get the data from specific column
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        TableColumn<CustomerAndOrders, Integer> rawMaterialIdCol = new TableColumn<>("rawMaterialId");
        rawMaterialIdCol.setMinWidth(50);

        // to get the data from specific column
        rawMaterialIdCol.setCellValueFactory(new PropertyValueFactory<>("rawMaterialId"));
        rawMaterialIdCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        rawMaterialIdCol.setOnEditCommit((TableColumn.CellEditEvent<CustomerAndOrders, Integer> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setRawMaterialId(t.getNewValue()); //display only
            try {
                String updateStatement = "update Orders set rawMaterialId = '" + t.getNewValue() +
                        "' where OrderId = " + t.getRowValue().getCustomerId() + ";";
                Operations.executeStatement(updateStatement);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        TableColumn<CustomerAndOrders, String> phoneNumCol = new TableColumn<>("phoneNum");
        phoneNumCol.setMinWidth(100);
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));


        phoneNumCol.setCellFactory(TextFieldTableCell.forTableColumn());

        phoneNumCol.setOnEditCommit((TableColumn.CellEditEvent<CustomerAndOrders, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setPhoneNum(t.getNewValue()); //display only
            try {
                String updateStatement = "update Customer set phoneNum = '" + t.getNewValue() +
                        "' where customerId = " + t.getRowValue().getCustomerId() + ";";
                Operations.executeStatement(updateStatement);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        TableColumn<CustomerAndOrders, String> customerNameCol = new TableColumn<>("customerName");
        customerNameCol.setMinWidth(50);
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        customerNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        customerNameCol.setOnEditCommit((TableColumn.CellEditEvent<CustomerAndOrders, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setCustomerName(t.getNewValue()); //display only
            try {
                String updateStatement = "update Customer set customerName = '" + t.getNewValue() +
                        "' where customerId = " + t.getRowValue().getCustomerId() + ";";

                Operations.executeStatement(updateStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        /////////////////////under is orders
        TableColumn<CustomerAndOrders, Double> priceCol = new TableColumn<>("price");
        priceCol.setMinWidth(50);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        priceCol.setOnEditCommit((TableColumn.CellEditEvent<CustomerAndOrders, Double> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setPrice(t.getNewValue()); //display only
            try {
                String updateStatement = "update orders set price = " + t.getNewValue() +
                        " where orderId = " + t.getRowValue().getOrderId() + ";";

                Operations.executeStatement(updateStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        TableColumn<CustomerAndOrders, String> orderDateCol = new TableColumn<>("orderDate");
        orderDateCol.setMinWidth(50);
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        orderDateCol.setCellFactory(TextFieldTableCell.forTableColumn());

        orderDateCol.setOnEditCommit((TableColumn.CellEditEvent<CustomerAndOrders, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setOrderDate(t.getNewValue()); //display only
            try {
                String updateStatement = "update orders set orderDate = '" + t.getNewValue() +
                        "' where orderId = " + t.getRowValue().getOrderId() + ";";

                Operations.executeStatement(updateStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        TableColumn<CustomerAndOrders, String> deliveryLocationCol = new TableColumn<>("deliveryLocation");
        deliveryLocationCol.setMinWidth(50);
        deliveryLocationCol.setCellValueFactory(new PropertyValueFactory<>("deliveryLocation"));

        deliveryLocationCol.setCellFactory(TextFieldTableCell.forTableColumn());

        deliveryLocationCol.setOnEditCommit((TableColumn.CellEditEvent<CustomerAndOrders, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setDeliveryLocation(t.getNewValue()); //display only
            try {
                String updateStatement = "update orders set deliveryLocation = '" + t.getNewValue() +
                        "' where orderId = " + t.getRowValue().getOrderId() + ";";

                Operations.executeStatement(updateStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        TableColumn<CustomerAndOrders, Double> sizeCol = new TableColumn<>("size");
        sizeCol.setMinWidth(50);
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));

        sizeCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        sizeCol.setOnEditCommit((TableColumn.CellEditEvent<CustomerAndOrders, Double> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setSize(t.getNewValue()); //display only
            try {
                String updateStatement = "update orders set size = " + t.getNewValue() +
                        " where orderId = " + t.getRowValue().getOrderId() + ";";

                Operations.executeStatement(updateStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        myDataTable.setItems(dataList);

        myDataTable.getColumns().addAll(customerIdCol, phoneNumCol, customerNameCol, orderIdCol,
                priceCol, orderDateCol, deliveryLocationCol, sizeCol, rawMaterialIdCol);
        borderPane.setCenter(myDataTable);
        stage.setScene(scene);

        //------------------------------------------------------
        addCustomerId.setPromptText("Customer Id");
        // addCustomerId.setMaxWidth(customerIdCol.getPrefWidth());

        // addPhoneNum.setMaxWidth(phoneNumCol.getPrefWidth());
        addPhoneNum.setPromptText("phone Number");

        //addCustomerName.setMaxWidth(sizeCol.getPrefWidth());
        addCustomerName.setPromptText("Customer Name");

        addOrderId.setMaxWidth(sizeCol.getPrefWidth());
        addOrderId.setPromptText("Order Id");

        //addPrice.setMaxWidth(sizeCol.getPrefWidth());
        addPrice.setPromptText("Price");
        // order date is set as the date of today! (current date!)


        // addDeliveryLocation.setMaxWidth(sizeCol.getPrefWidth());
        addDeliveryLocation.setPromptText("Delivery Location");

        // addSize.setMaxWidth(sizeCol.getPrefWidth());
        addSize.setPromptText("Size");
        addRawMaterialId.setPromptText("Raw material Id");

        Label outputLabel = new Label("output");
        outputLabel.setFont(Font.font("Verdana", 20));
        outputLabel.setTextFill(Color.RED);

        HBox textFieldsHBox = new HBox(10);
        textFieldsHBox.getChildren().addAll(addCustomerId, addPhoneNum, addCustomerName, addOrderId, addPrice
                , addDeliveryLocation, addSize, addRawMaterialId);

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {

            outputLabel.setText(null);

            //if (!isValid()) {
            //   outputLabel.setText("please enter a valid input!");

            // } else {
            Date date = new Date();
            String currentDate = date.getYear() + 1900 + "-" + (date.getMonth() + 1) + "-" + date.getDate();
            CustomerAndOrders cOrder = new CustomerAndOrders(
                    Integer.parseInt(addCustomerId.getText()),
                    addPhoneNum.getText(),
                    addCustomerName.getText(),
                    Integer.parseInt(addOrderId.getText()),
                    Double.parseDouble(addPrice.getText()),
                    currentDate,
                    addDeliveryLocation.getText(),
                    Double.parseDouble(addSize.getText()),
                    Integer.parseInt(addRawMaterialId.getText()));


            if (!isExistOrder(cOrder) && isExistRawId(cOrder)) {

                try {

                    if (!isExistCustomer(cOrder)) {
                        String insertStatCustomer = "insert into customer values("
                                + cOrder.getCustomerId() + ", '"
                                + cOrder.getPhoneNum() + "', '"
                                + cOrder.getCustomerName() + "'" + ")";
                        Operations.executeStatement(insertStatCustomer);
                    } else {
                        String selectStatC = "SELECT customerId,CustomerName,phoneNum from customer where customerId = "
                                + cOrder.getCustomerId() + "; ";

                        try {
                            ResultSet rsCust = Operations.executeQueryToGetData(selectStatC);

                            rsCust.next(); // to point on the first row!

                            cOrder.setCustomerName(rsCust.getString(2));
                            cOrder.setPhoneNum(rsCust.getString(3));
                            rsCust.close();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                    String insertStatOrder = "Insert into Orders (orderId,price,orderDate,deliveryLocation, size,customerID,RawMaterialId) values("
                            + cOrder.getOrderId() + ","
                            + cOrder.getPrice() + ",'"
                            + cOrder.getOrderDate() + "', '"
                            + cOrder.getDeliveryLocation() + "',"
                            + cOrder.getSize() + ", "
                            + cOrder.getCustomerId() + ","
                            + cOrder.getRawMaterialId()
                            + ");";

                    Operations.executeStatement(insertStatOrder);
                    dataList.add(cOrder);

                    myDataTable.refresh();


                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            } else {

                outputLabel.setText("Order Exists!,or Raw material id does not exists can't be inserted!");
            }
            // }
            addCustomerId.clear();
            addPhoneNum.clear();
            addCustomerName.clear();

            addSize.clear();
            addPrice.clear();
            addDeliveryLocation.clear();
            addOrderId.clear();
            addRawMaterialId.clear();
            //}
        });
        final Button deleteButton = new Button("Delete");

        deleteButton.setOnAction((ActionEvent e) -> {

            ObservableList<CustomerAndOrders> selectedRows = myDataTable.getSelectionModel().getSelectedItems();
            ArrayList<CustomerAndOrders> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                myDataTable.getItems().remove(row);
                try {

                    String deleteStatement = "delete from  Orders where orderId = " + row.getOrderId() + ";";
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


    private boolean isExistCustomer(CustomerAndOrders rc) {

        String selectStat = "SELECT customerId from customer where customerId = " + rc.getCustomerId() + "; ";

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

    private boolean isExistOrder(CustomerAndOrders rc) {


        String selectStat = "SELECT orderId from orders where orderId = " + rc.getOrderId() + "; ";

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

    private boolean isExistRawId(CustomerAndOrders rc) {


        String selectStat = "SELECT rawMaterialId from rawMaterial where rawMaterialId = " + rc.getRawMaterialId() + "; ";

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

    private void confirmStage(TableView<CustomerAndOrders> table) {
        Stage stage = new Stage();


        Button yesButton = new Button("Confirm");
        yesButton.setOnAction(e -> {
            for (CustomerAndOrders row : dataList) {
                try {
                    String deleteStatement = "delete from  Orders where orderId = " + row.getOrderId() + ";";

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

    private boolean isValid() {

        boolean valid = ((addCustomerId.getText().trim().matches("[0-9]+")) &&
                ((addPhoneNum.getText().trim().matches("[0-9]+")) && (addPhoneNum.getText().trim().matches(""))) &&
                ((addCustomerName.getText().trim().matches("[a-zA-Z]*")) && (addPhoneNum.getText().trim().matches(""))) &&
                (addOrderId.getText().trim().matches("[0-9]+")) &&
                ((addPrice.getText().trim().matches("[0-9]+")) || (addPrice.getText().trim().matches("[0-9]+.[0-9]+"))) &&
                (addDeliveryLocation.getText().trim().matches("[a-zA-Z]*")) &&
                ((addSize.getText().trim().matches("[0-9]+")) || (addSize.getText().trim().matches("[0-9]+.[0-9]+"))));
        return valid;
    }
}


