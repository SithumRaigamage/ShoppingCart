import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingCartGUI extends JFrame {

    // Fields
    private ShoppingCart shoppingCart;
    private JTable cartTable;
    private User currentUser; // Field to store the current user
    private PurchaseManager purchaseManager; // Field for the purchase manager

    private JButton removeButton;
    private JButton checkoutButton;

    // Labels for display
    private JLabel totalLabel = new JLabel("Total :");
    private JLabel firstPurchaseDiscountLabel = new JLabel("First Purchase Discount (10%) :");
    private JLabel sameCategoryDiscountLabel = new JLabel("Three items in the same Category Discount (20%) :");
    private JLabel finalTotalLabel = new JLabel("Final Total :");

    // Labels for displaying calculated values
    private JLabel TotalLabelValue = new JLabel("$0.00");
    private JLabel firstPurchaseDiscountValue = new JLabel("$0.00");
    private JLabel sameCategoryDiscountValue = new JLabel("$0.00");
    private JLabel finalTotalValue = new JLabel("$0.00");

    //Constructor
    public ShoppingCartGUI(ShoppingCart shoppingCart,User currentUser, PurchaseManager purchaseManager) {
        this.shoppingCart = shoppingCart;
        this.currentUser = currentUser;
        this.purchaseManager = purchaseManager;
        initializeComponents();
        // Calling this to update the display initially
        updateDisplay();
    }




    // Method to initialize GUI components
    private void initializeComponents() {
        setLayout(new BorderLayout());

        // Create and configure JTable
        cartTable = new JTable(shoppingCart);
        cartTable.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
        add(new JScrollPane(cartTable), BorderLayout.CENTER);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize buttons
        removeButton = new JButton("Remove Item");
        checkoutButton = new JButton("Checkout");

        // Create info panel with GridLayout
        JPanel infoPanel = new JPanel(new GridLayout(4, 2));

        // Add labels and corresponding value labels to info panel
        infoPanel.add(totalLabel);
        infoPanel.add(TotalLabelValue);

        infoPanel.add(firstPurchaseDiscountLabel);
        infoPanel.add(firstPurchaseDiscountValue);

        infoPanel.add(sameCategoryDiscountLabel);
        infoPanel.add(sameCategoryDiscountValue);

        infoPanel.add(finalTotalLabel);
        infoPanel.add(finalTotalValue);

        // Create bottom panel to hold buttons and info panel
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Panel for buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(removeButton);
        buttonsPanel.add(checkoutButton);

        // Add buttons panel and info panel to bottom panel
        bottomPanel.add(buttonsPanel, BorderLayout.NORTH);
        bottomPanel.add(infoPanel, BorderLayout.SOUTH);

        // Add bottom panel to the main frame
        add(bottomPanel, BorderLayout.SOUTH);

        // Event listeners for buttons
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCheckout();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedItem();
            }
        });
    }

    // Method to remove the selected item from the shopping cart
    private void removeSelectedItem() {
        int viewRow = cartTable.getSelectedRow();
        if (viewRow != -1) {
            int modelRow = cartTable.convertRowIndexToModel(viewRow);
            if (modelRow >= 0 && modelRow < shoppingCart.getRowCount()) {
                shoppingCart.removeItem(modelRow);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.");
        }
    }

    // Method to update the display with calculated values
    public void updateDisplay() {
        double total = shoppingCart.calculateTotalCartValue();
        double firstPurchaseDiscount = shoppingCart.calculateFirstPurchaseDiscount(currentUser, purchaseManager);
        double sameCategoryDiscount = shoppingCart.calculateSameCategoryDiscount();
        double finalTotal = total - (firstPurchaseDiscount + sameCategoryDiscount);

        TotalLabelValue.setText(String.format("$%.2f", total));
        firstPurchaseDiscountValue.setText(String.format("-$%.2f", firstPurchaseDiscount));
        sameCategoryDiscountValue.setText(String.format("-$%.2f", sameCategoryDiscount));
        finalTotalValue.setText(String.format("$%.2f", finalTotal));
    }
    //method to handle checkout process
    private void onCheckout() {
        // Update display (which calculates and shows discounts)
        updateDisplay();

        // Then add purchase to history
        PurchaseHistory();
    }

    //method to add to purchaseHistory arraylist to validate
    private void PurchaseHistory() {
        purchaseManager.addPurchase(currentUser, shoppingCart);
    }

}