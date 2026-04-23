package View.MainLayout;

import Controllers.MainController;
import View.Components.RowColorRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * The center layout of the main content
 * Extends from JPanel class
 */
public class CenterLayout extends JPanel {
    public static DefaultListModel<String> cityListModel = new DefaultListModel<>();
    public static JList<String> cityList;
    public static DefaultTableModel cityDataTableModel;
    public static JTable cityDataTable;

    public CenterLayout() {
        setLayout(new GridLayout(1, 2));

        // Left panel
        LeftPanel leftPanel = new LeftPanel();

        // Right panel
        RightPanel rightPanel = new RightPanel();

        add(leftPanel);
        add(rightPanel);
    }

    /**
     * Left panel or the city panel ;D
     */
    private static class LeftPanel extends JPanel {
        public LeftPanel() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createTitledBorder("Cities"));

            // List
            cityList = new JList<>(cityListModel);
            JScrollPane listScrollPane = new JScrollPane(cityList);

            add(listScrollPane, BorderLayout.CENTER);
        }
    }

    /**
     * Right panel or the cities data panel ;D
     */
    private static class RightPanel extends JPanel {
        public RightPanel() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createTitledBorder("Data"));

            // Columns
            cityDataTableModel = new DefaultTableModel();
            cityDataTableModel.addColumn("Date & Time");
            cityDataTableModel.addColumn("Temperature");
            cityDataTableModel.addColumn("Status");

            // Table
            cityDataTable = new JTable(cityDataTableModel);
            cityDataTable.setDefaultRenderer(Object.class, new RowColorRenderer());
            JScrollPane scrollPane = new JScrollPane(cityDataTable);

            add(scrollPane, BorderLayout.CENTER);
        }
    }
}
