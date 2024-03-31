// CartItem class representing an item in a shopping cart
public class CartItem {

    // Fields representing properties of a cart item
    private String productId;
    private String name;
    private double price;
    private int quantity;
    private double totalPrice;
    private String info;
    private String category;

    // Constructor to initialize a CartItem object
    public CartItem(String productId, String name, double price, int quantity, String info, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
        this.info = info;
        this.category = category;
    }

    // Getter methods for retrieving the values of fields
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getInfo() {
        return info;
    }

    public String getCategory() {
        return category;
    }

    // Setter methods for updating the values of fields
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        // Update the total price when the quantity changes
        this.totalPrice = this.price * this.quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Method to update the quantity and recalculate the total price
    public void updateQuantity(int additionalQuantity) {
        this.quantity += additionalQuantity;
        this.totalPrice = this.price * this.quantity;
    }
}
