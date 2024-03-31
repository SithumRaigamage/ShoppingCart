import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TableModel extends AbstractTableModel {
    //column names for the table to display
    private final String[] columnNames = {"ProductId","Name","Category","Price(£)","Info","AvailableItems"};
    //initiating data arraylist to put products
    private ArrayList<Product> data;
    // This will store all products for to filter based on combo box selection
    private ArrayList<Product> originalData;

    //Constructor
    public TableModel(ArrayList<Product> data) {
        // Make a copy of the data for filtering.
        this.originalData = new ArrayList<>(data);
        // Initialize the data.
        this.data = new ArrayList<>(data);
        fireTableDataChanged();
    }
    //getter rowCount
    @Override
    public int getRowCount() {
        return data.size();
    }
    //getter columnCount
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    //getValueAt method to get details for it to show on the Jtable
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object temp = null;
        Product product = data.get(rowIndex);

        switch (columnIndex) {
            case 0:
                temp = product.getProductId();
                break;
            case 1:
                temp = product.getProductName();
                break;
            case 2:
                if (product instanceof Electronic) {
                    temp = "Electronic";
                } else if (product instanceof Clothing) {
                    temp = "Clothing";
                } else {
                    temp = "Unknown";
                }
                break;
            case 3:
                temp = product.getPrice();
                break;
            case 4:
                if (product instanceof Electronic) {
                    Electronic electronicProduct = (Electronic) product;
                    temp = "Brand: " + electronicProduct.getBrand() + ", Warranty: " + electronicProduct.getWarrantyPeriod() + " months";
                } else if (product instanceof Clothing) {
                    Clothing clothingProduct = (Clothing) product;
                    temp = "Size: " + clothingProduct.getSize() + ", Color: " + clothingProduct.getColor();
                } else {
                    temp = "N/A";
                }
                break;
            case 5:
                temp=product.getAvailableItems();
                break;
        }
        return temp;
    }

    // needed to show column names in JTable
    public String getColumnName(int col) {
        return columnNames[col];
    }
    public void updateProductData(ArrayList<Product> newData) {
        this.data = newData;
        fireTableDataChanged();
    }
    //filtering method based on the jComboBox
    public void filterData(Class<?> filterClass) {
        ArrayList<Product> filteredData = new ArrayList<>();
        for (Product product : originalData) { // Use originalData here.
            if (filterClass == null || filterClass.isInstance(product)) {
                filteredData.add(product);
            }
        }
        this.data = filteredData;
        fireTableDataChanged();
    }
}