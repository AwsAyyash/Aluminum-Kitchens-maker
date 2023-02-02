package project;


import java.util.Date;

public class CustomerAndOrders {

    private int customerId;
    private String phoneNum;
    private String customerName;
    // the above are for customer!

    private int orderId;
    private double price;
    private String orderDate;
    private String deliveryLocation;
    private double size;

    public int getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(int rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    private int rawMaterialId ;





    public CustomerAndOrders() {

    }

    public CustomerAndOrders(int customerId, String phoneNum, String customerName, int orderId, double price,
                             String orderDate, String deliveryLocation, double size, int rawMaterialId) {
        this.customerId = customerId;
        this.phoneNum = phoneNum;
        this.customerName = customerName;
        this.orderId = orderId;
        this.price = price;
        this.orderDate = orderDate;
        this.deliveryLocation = deliveryLocation;
        this.size = size;
        this.rawMaterialId =rawMaterialId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    /*
    orderId int PK
price double
orderDate date
deliveryLocation varchar(32)
size double
customerId int
paymentId
    * */


}


