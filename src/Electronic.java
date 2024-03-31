import java.io.Serializable;

public class Electronic extends Product implements Serializable {

    //initialized variable called brand
    private String brand;
    //initialized variable called warrantyPeriod
    private int warrantyPeriod;


    //argumentative constructor passing variables productId, productName, availableItems,price as 'super' and brand and warrantyPeriod
    public Electronic(String productId, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productId, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    //getter for Brand
    public String getBrand() {
        return brand;
    }

    //getter for WarrantyPeriod
    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    //setter methods for Brand
    public void setBrand(String brand) {
        this.brand = brand;
    }

    //setter for WarrantyPeriod
    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    //toString Method
    @Override
    public String toString() {
        return super.getProductId() + ", " +
                super.getProductName() + ", " +
                super.getAvailableItems() + ", " +
                super.getPrice() + ", " +
                brand + ", " +
                warrantyPeriod;
    }
}

