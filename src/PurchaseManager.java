import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseManager {

    // Map to store the purchase history for each user
    private Map<User, List<ShoppingCart>> userPurchaseHistory = new HashMap<>();

    // Constructor for the PurchaseManager class
    public PurchaseManager() {
        // Constructor body (currently empty)
    }

    // Method to add a purchase to the user's purchase history
    public void addPurchase(User user, ShoppingCart cart) {
        // If the user is not present in the map, add an empty list for the user
        userPurchaseHistory.putIfAbsent(user, new ArrayList<>());
        // Retrieve the user's purchase history and add the new shopping cart
        userPurchaseHistory.get(user).add(cart);
        // Debugging statement
        //System.out.println("Added purchase for user: " + user.getUsername());
        // Uncomment the line above if you want to print debugging information
        //printProducts();
    }

    // Method to check if a user has made any purchases before
    public boolean isFirstPurchase(User user) {
        // Retrieve the user's purchase history
        List<ShoppingCart> carts = userPurchaseHistory.get(user);
        // Check if the user's purchase history is null or empty
        boolean isFirstPurchase = carts == null || carts.isEmpty();
        // Return the result
        return isFirstPurchase;
    }

}



