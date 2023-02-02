package project;


public class Payment {



	/* paymentId INT,
    amount REAL,
    paymentDate DATE,
    workerId INT,
    supplierId INT,
    customerId INT,*/

    private int paymentId;
    private double amount;
    private String paymentDate;
    private int workerId; // from workers table
    private int supplierId; // from suppliers table
    private int customerId; // from customers table

    //constructor
    public Payment(int paymentId, double amount, String paymentDate, int workerId, int supplierId, int customerId) {
        super();
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.workerId = workerId;
        this.supplierId = supplierId;
        this.customerId = customerId;
    }


    public Payment() {
        super();


    }


    // setters and getters

    public Payment(int id) {
        super();
        this.paymentId = id;


    }


    public int getPaymentId() {
        return paymentId;
    }


    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }


    public double getAmount() {
        return amount;
    }


    public void setAmount(double amount) {
        this.amount = amount;
    }


    public String getPaymentDate() {
        return paymentDate;
    }


    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }


    public int getWorkerId() {
        return workerId;
    }


    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }


    public int getSupplierId() {
        return supplierId;
    }


    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }


    public int getCustomerId() {
        return customerId;
    }


    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


}



