package project;


public class Supplier {


    private int supplierId;
    private String supplierLocation;
    private int supplierPhoneNum;


    public Supplier() {
    }

    public Supplier(int supplierId, String supplierLocation, int supplierPhoneNum) {
        this.supplierId = supplierId;
        this.supplierLocation = supplierLocation;
        this.supplierPhoneNum = supplierPhoneNum;

    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierLocation() {
        return supplierLocation;
    }

    public void setSupplierLocation(String supplierLocation) {
        this.supplierLocation = supplierLocation;
    }

    public int getSupplierPhoneNum() {
        return supplierPhoneNum;
    }

    public void setSupplierPhoneNum(int supplierPhoneNum) {
        this.supplierPhoneNum = supplierPhoneNum;
    }


    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId=" + supplierId +
                ", supplierLocation='" + supplierLocation + '\'' +
                ", supplierPhoneNum=" + supplierPhoneNum +
                '}';
    }
}

