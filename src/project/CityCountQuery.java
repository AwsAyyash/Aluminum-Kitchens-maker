package project;


public class CityCountQuery {


    private String supplierLocation;
    private int count;

    public CityCountQuery(String supplierLocation, int count) {
        this.supplierLocation = supplierLocation;
        this.count = count;
    }

    public String getSupplierLocation() {
        return supplierLocation;
    }

    public void setSupplierLocation(String supplierLocation) {
        this.supplierLocation = supplierLocation;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}


