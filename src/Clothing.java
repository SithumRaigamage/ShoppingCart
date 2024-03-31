import java.io.Serializable;

public class Clothing extends Product implements Serializable {

    //initialized variable called size
    private String size;
    //initialized variable called color
    private String color;

    //argumentative constructor passing variables productId, productName, availableItems,price as 'super' and size and color
    public Clothing(String productId, String productName, int availableItems, double price, String size, String color) {
        super(productId, productName, availableItems, price);
        this.size = size;
        this.color = color;
    }

    //getter for Size
    public String getSize() {
        return size;
    }
    //getter for Color
    public String getColor() {
        return color;
    }
    //setter for Size
    public void setSize(String size) {
        this.size = size;
    }
    //setter for Color
    public void setColor(String color) {
        this.color = color;
    }

    //toString Method
    @Override
    public String toString() {
        return super.getProductId() + ", " +
                super.getProductName() + ", " +
                super.getAvailableItems() + ", " +
                super.getPrice() + ", " +
                size + ", " +
                color;
    }
}


