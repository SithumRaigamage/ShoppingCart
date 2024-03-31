// Importing the ArrayList class from java.util package
import java.util.ArrayList;

// ShoppingManager interface definition
public interface Shopping {

    // Abstract method to add a product to the system
    public abstract void addProductToSystem(Product product);

    // Abstract method to print all products in the system
    public abstract void printProductsInSystem();

    // Abstract method to delete a product from the system based on its ID
    public abstract void deleteProductFromSystem(String productID);

    // Abstract method to save products to a file
    public abstract void saveProductsToFile(String fileName);

    // Abstract method to read products from a file and return them as an ArrayList
    public abstract ArrayList<Product> readProductsFromFile(String fileName);

    // Abstract method to get a display of products (returns an ArrayList)
    ArrayList<Product> getDisplay();

    // Abstract method to get a product by its ID
    Product getProductById(String productId);

    // Abstract method to run the menu for the shopping manager
    void runMenu();
}
