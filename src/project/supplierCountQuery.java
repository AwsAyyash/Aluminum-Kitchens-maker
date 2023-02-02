package project;


public class supplierCountQuery {

    private int supplierId;
    private int count;

    public supplierCountQuery(int supplierId, int count) {
        this.supplierId = supplierId;
        this.count = count;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}


