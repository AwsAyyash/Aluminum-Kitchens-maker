package project;


public class RawMaterial {


    private int rawMaterialId;
    private String color;
    private double rawMaterialPrice;
    private int supplierId;

    public RawMaterial() {
    }

    public RawMaterial(int rawMaterialId, String color, double rawMaterialPrice, int supplierId) {
        this.rawMaterialId = rawMaterialId;
        this.color = color;
        this.rawMaterialPrice = rawMaterialPrice;
        this.supplierId = supplierId;
    }

    public int getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(int rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getRawMaterialPrice() {
        return rawMaterialPrice;
    }

    public void setRawMaterialPrice(double rawMaterialPrice) {
        this.rawMaterialPrice = rawMaterialPrice;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "RawMaterial{" +
                "rawMaterialId=" + rawMaterialId +
                ", color='" + color + '\'' +
                ", rawMaterialPrice=" + rawMaterialPrice +
                ", supplierId=" + supplierId +
                '}';
    }
}

