package project;


public class OrdersToWorkers {
    private  int orderId ; // from order table
    private int workerId ;  // from worker table

    // constructor
    public OrdersToWorkers(int orderId, int workerId) {
        super();
        this.orderId = orderId;
        this.workerId = workerId;
    }
    // setters and getters
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getWorkerId() {
        return workerId;
    }
    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }


    @Override
    public String toString() {
        return "OrdersToWorkers [orderId=" + orderId + ", workerId=" + workerId + "]";
    }


}

