import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
// MultiLineCellRenderer class to handle new lines  in the Jtable implemented in the shoppingCartGUI
public class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {

    //constructor
    public MultiLineCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true); // Make background non-transparent
    }


    //method to handle new lines in Jtable
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());

        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
        if (table.getRowHeight(row) < getPreferredSize().height) {
            table.setRowHeight(row, getPreferredSize().height);
        }

        return this;
    }
}
