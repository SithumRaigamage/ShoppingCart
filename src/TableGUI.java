import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.Map;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;


public class TableGUI extends JFrame {

    private User currentUser;
    private PurchaseManager purchaseManager;
    private Shopping shoppingManager;
    private TableGUI tableGUI;
    private JComboBox<String> productTypeComboBox;
    private JTextArea productDetailsTextArea;
    private JLabel ProductType;
    private static JTable productTable;

    private JButton addToShoppingCart;

    private JButton viewShoppingCartButton;

    private Map<String, Integer> availableItemsMap;
    private ShoppingCart shoppingCart;
    private ShoppingCartGUI shoppingCartGUI;

    public TableGUI(Shopping shoppingManager, Map<String, Integer> availableItemsMap, ShoppingCart shoppingCart, ShoppingCartGUI shoppingCartGUI, User currentUser, PurchaseManager purchaseManager){
        this.shoppingManager=shoppingManager;
        this.availableItemsMap = availableItemsMap;
        this.shoppingCart = shoppingCart;
        this.shoppingCartGUI=shoppingCartGUI;
        this.currentUser = currentUser;
        this.purchaseManager = purchaseManager;

        ProductType = new JLabel("Select Product Type:");
        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothes"});
        viewShoppingCartButton = new JButton("Shopping Cart");
        productDetailsTextArea = new JTextArea();
        addToShoppingCart = new JButton("Add to Shopping Cart");

        // Create a panel with FlowLayout
        JPanel flowPanel = new JPanel(new FlowLayout());

        // Create a panel with GridLayout
        JPanel gridPanel = new JPanel(new GridLayout(1, 6));

        // Add components to the gridPanel
        // Add five placeholder components to fill columns 3 to 5
        gridPanel.add(new JLabel()); // Placeholder for column 3
        gridPanel.add(new JLabel()); // Placeholder for column 4
        gridPanel.add(ProductType);
        gridPanel.add(productTypeComboBox);
        gridPanel.add(new JLabel()); // Placeholder for column 5
        gridPanel.add(new JLabel()); // Placeholder for column 6
        gridPanel.add(viewShoppingCartButton);

        // Add components to the flowPanel
        flowPanel.add(gridPanel);

        //System.out.println("Initializing Table with: " + shoppingManager.getDisplay());
        // Create a table model with the display data
        TableModel tableModel = new TableModel(shoppingManager.getDisplay());

        // Create JTable with custom cell renderer
        productTable = new JTable(tableModel);

        // Set the TableRowSorter to the JTable
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        productTable.setRowSorter(sorter);

        //sorter to aplhabetically
        sorter.setComparator(0, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2); // Alphabetical, case-insensitive sorting
            }
        });


        // Set the preferred size of the JTable
        productTable.setPreferredScrollableViewportSize(new Dimension(600, 500)); // Adjust width and height as needed
        // Hide the last column (AvailableItems)
        TableColumnModel tcm = productTable.getColumnModel();
        tcm.removeColumn(tcm.getColumn(5)); // This removes the column from view, but the data is still accessible in the model.



        // Create a JScrollPane and add the JTable to it
        JScrollPane scrollPane = new JScrollPane(productTable);

        // Optionally set other properties for the scroll pane, e.g., vertical scrollbar policy
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        // Set the preferred size of the JTextArea
        productDetailsTextArea.setPreferredSize(new Dimension(980, 200)); // Adjust width and height as needed

        // Create a panel for the south position
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(productDetailsTextArea, BorderLayout.CENTER);

        // Center the addToShoppingCart button within the southPanel
        southPanel.add(addToShoppingCart, BorderLayout.SOUTH);

        // Add the JTable to the content pane with a JScrollPane
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
        this.getContentPane().add(flowPanel, BorderLayout.NORTH);
        this.getContentPane().add(southPanel, BorderLayout.SOUTH);

        // Add ActionListener viewShoppingCart GUI
        viewShoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShoppingCartGUI();
            }
        });

        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add selection listener to the table
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    updateProductDetails();
                }
            }
        });

        // Add ActionListener to addToShoppingCart button
        addToShoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProductToShoppingCart();
            }
        });
        // ActionListener to productTypeComboBox
        productTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) productTypeComboBox.getSelectedItem();
                Class<?> filterClass = null;

                if ("Electronics".equals(selectedType)) {
                    filterClass = Electronic.class;
                } else if ("Clothes".equals(selectedType)) {
                    filterClass = Clothing.class;
                }
                // When "All" is selected, filterClass remains null, showing all products

                ((TableModel) productTable.getModel()).filterData(filterClass);
            }
        });

        // method for coloring the table red when availbale items<3
        productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int availableItems = (Integer) table.getModel().getValueAt(table.convertRowIndexToModel(row), 5); // Use convertRowIndexToModel for filtered/sorted tables

                if (!isSelected) {
                    if (availableItems < 3) {
                        c.setBackground(Color.RED); // Or any color you prefer for low stock
                        c.setForeground(Color.BLACK); // Set text color for better readability
                    } else {
                        c.setBackground(Color.WHITE); // Default background color
                        c.setForeground(Color.BLACK); // Default text color
                    }
                } else {
                    // If row is selected, use table's default selection colors
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                }

                return c;
            }
        });
    }
    //method to get data and updating data on JTextArea
    private void updateProductDetails() {
        int viewRow = productTable.getSelectedRow();
        if (viewRow < 0) {
            // If no row is selected or selection is out of range, clear the details area
            productDetailsTextArea.setText("");
        } else {
            int modelRow = productTable.convertRowIndexToModel(viewRow);
            StringBuilder details = new StringBuilder();
            for (int col = 0; col < productTable.getModel().getColumnCount(); col++) {
                // Append the column name and value from the cell
                details.append(productTable.getModel().getColumnName(col))
                        .append(": ")
                        .append(productTable.getModel().getValueAt(modelRow, col))
                        .append("\n");
            }
            // Set the text of the JTextArea to the details
            productDetailsTextArea.setText(details.toString());
        }
    }
    // Method to open ShoppingCartGuiModel
    private void openShoppingCartGUI() {

        // Create an instance of ShoppingCartGuiModel
        ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(shoppingCart, currentUser, purchaseManager);
        // Set properties for the shopping cart GUI (customize as needed)
        shoppingCartGUI.setSize(500, 500);
        shoppingCartGUI.setTitle("Shopping Cart");
        shoppingCartGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shoppingCartGUI.setLocationRelativeTo(null);
        // Make the shopping cart GUI visible
        shoppingCartGUI.setVisible(true);
    }

    // Method to handle adding product to shopping cart from the shoppingDisplay
    private void addProductToShoppingCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Get product details from the selected row
            String productId = (String) productTable.getValueAt(selectedRow, 0);


            String name = (String) productTable.getValueAt(selectedRow, 1);
            String category=(String) productTable.getValueAt(selectedRow,2);
            double price = (Double) productTable.getValueAt(selectedRow, 3);
            Integer availableItems = availableItemsMap.get(productId);
            String infor= (String) productTable.getValueAt(selectedRow,4);

            //debug
            //System.out.println("Selected Product ID: " + productId);


            // Ask user for quantity
            String quantityString = JOptionPane.showInputDialog(this, "Enter quantity for Product ID: " + productId, "1");
            try {
                int quantity = Integer.parseInt(quantityString);
                if (quantity > 0 && quantity <= availableItems) {
                    shoppingCart.addItem(productId, name, price, quantity, infor, category);

                    // Subtract the quantity from the product's available items
                    availableItemsMap.put(productId, availableItems - quantity);
                    Product selectedProduct = shoppingManager.getProductById(productId);
                    if (selectedProduct != null) {
                        // Update the product's available items
                        selectedProduct.setAvailableItems(availableItems - quantity);
                    } else {
                        System.out.println("Product not found for ID: " + productId); // Debugging statement
                    }
                    selectedProduct.setAvailableItems(availableItems - quantity);

                    // Refresh the table
                    ((TableModel) productTable.getModel()).updateProductData(shoppingManager.getDisplay());
                    // Refresh ShoppingCartGUI
                    shoppingCartGUI.updateDisplay();
                        //handling errors
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a number between 1 and " + availableItems);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product from the table.");
        }
    }

}