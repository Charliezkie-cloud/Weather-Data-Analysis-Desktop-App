package WeatherAnalysisApp.Components;

import WeatherAnalysisApp.Services.HelperService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * A custom renderer for each row of the table
 * The color of the row depends on what the temperature is
 */
public class RowColorRenderer extends DefaultTableCellRenderer {
    /**
     * An overridden method of "getTableCellRendererComponent"
     * Adding custom color for each row depending on what the temperature value os.
     * @param table  the <code>JTable</code>
     * @param value  the value to assign to the cell at
     *                  <code>[row, column]</code>
     * @param isSelected true if cell is selected
     * @param hasFocus true if cell has focus
     * @param row  the row of the cell to render
     * @param column the column of the cell to render
     * @return The component of the row
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        double temperature = Double.parseDouble(table.getValueAt(row, 1).toString().replaceAll("[^0-9.-]", ""));
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


        if (!isSelected) {
            component.setForeground(Color.BLACK);
            component.setBackground(HelperService.getColor(temperature));
        }

        return component;
    }
}
