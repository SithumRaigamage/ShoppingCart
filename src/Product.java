import java.io.Serializable;

public abstract class Product implements Serializable {

    //initialized variable called productId
    private String productId;
    //initialized variable called productName
    private String productName;
    //initialized variable called availableItems
    private int availableItems;
    //initialized variable called price
    private double price;

    //argumentative constructor passing variables productId, productName, availableItems,price
    public Product(String productId,String productName,int availableItems,double price){
        this.productId=productId;
        this.productName=productName;
        this.availableItems=availableItems;
        this.price=price;
    }

    //setter methods for ProductId
    public void setProductId(String productId) {
        this.productId = productId;
    }

    //setter methods for ProductName
    public void setProductName(String productName){
        this.productName=productName;
    }

    //setter methods for AvailableItems
    public void setAvailableItems(int availableItems){
        this.availableItems=availableItems;
    }

    //setter methods for Price
    public void setPrice(double price){
        this.price=price;
    }

    //getter for ProductId
    public String getProductId(){
        return productId;
    }

    //getter for ProductName
    public String getProductName(){
        return productName;
    }

    //getter for AvailableItems
    public int getAvailableItems() {
        return availableItems;
    }

    //getter for Price
    public double getPrice() {
        return price;
    }

    //toString Method
    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", availableItems=" + availableItems +
                ", price=" + price +
                '}';
    }


}

