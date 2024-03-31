import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart extends AbstractTableModel {
    //Arraylist items
    private List<CartItem> items;

    //columnNames need for the ShoppingCart Jtable
    private final String[] columnNames = {"Product","Quantity","Price"};

    //constructor
    public ShoppingCart() {
        //initiating the arraylist
        items = new ArrayList<>();
    }
    //add method to arraylist(items) to store items and to display in the gui
    public void addItem(String productId, String name, double price, int quantity, String info, String category) {
        //if the same item is added the quantity is added
        CartItem existingItem = findItem(productId);
        if (existingItem != null) {
            existingItem.updateQuantity(quantity);

        } else {
            //adding items to the arraylist with parameters
            items.add(new CartItem(productId, name, price, quantity,info,category));
        }

    }


//method to find item by productId
    private CartItem findItem(String productId) {
        for (CartItem item : items) {
            if (item.getProductId().equals(productId)) {
                return item;
            }
        }
        return null;
    }
    //calculating the finalCartValue for the GUI to display
    public double  calculateTotalCartValue() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;

    }
    //getter method for items arraylist
    public List<CartItem> getItems() {
        return items;
    }

    //getter rowCount
    @Override
    public int getRowCount() {
        return items.size();
    }

    //getter columnCount
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    //method to set values for the columns in the table
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CartItem item = items.get(rowIndex);
        switch (columnIndex) {
            case 0: return item.getProductId() + "\n" + item.getName() + "\n" + item.getInfo();
            case 1: return item.getQuantity() ;
            case 2: return item.getPrice();
            default: return null;
        }
    }
    //getter for columnNames
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    //method to calculate firstPurchaseDiscount
    public double calculateFirstPurchaseDiscount(User user, PurchaseManager purchaseManager) {
        if (purchaseManager.isFirstPurchase(user)) {
            double total = calculateTotalCartValue();
            double discount = total * 0.1; // 10% discount
            //debugging
            //System.out.println("Calculated first purchase discount: " + discount);
            return discount;
        }
        return 0;
    }
    //method to calculate sameCategoryDiscount (3 items should be in the cart of the same category) to be applicable
    public double calculateSameCategoryDiscount() {
        Map<String, Integer> categoryCount = new HashMap<>();
        for (CartItem item : items) {
            String category = item.getCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }

        for (int count : categoryCount.values()) {
            if (count >= 3) {
                return calculateTotalCartValue() * 0.2;  // 20% discount
            }
        }
        return 0;
    }

    //removing products from the Jtable method
    public void removeItem(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < items.size()) {
            // Notify the table model that a row is about to be deleted
            fireTableRowsDeleted(rowIndex, rowIndex);

            // Now remove the item
            items.remove(rowIndex);
        }
        else {
            System.out.println("Please select an item to remove." + "Selection Error");
        }
    }



}