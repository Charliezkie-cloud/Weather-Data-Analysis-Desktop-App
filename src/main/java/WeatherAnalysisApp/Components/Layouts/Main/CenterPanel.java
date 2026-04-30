package WeatherAnalysisApp.Components.Layouts.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * The center layout of the main content
 * Extends from <code>JPanel</code> class
 */
public class CenterPanel extends JPanel {
    public static JComboBox<String> dataOptionBox;
    public static DefaultListModel<String> cityListModel = new DefaultListModel<>();
    public static JList<String> cityList;
    public static DefaultTableModel cityDataTableModel;
    public static JTable cityDataTable;
    public static JMenuItem deleteCityMenuItem;

    public CenterPanel() {
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

            // Context menu
            JPopupMenu cityListPopupMenu = new JPopupMenu();

            // Menu items
            deleteCityMenuItem = new JMenuItem("Delete");

            cityListPopupMenu.add(deleteCityMenuItem);

            cityList.setComponentPopupMenu(cityListPopupMenu);

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

            String[] dataOptions = {"Hourly", "Daily"};

            // Combo box
            dataOptionBox = new JComboBox<>(dataOptions);

            // Columns
            cityDataTableModel = new DefaultTableModel();
            cityDataTableModel.addColumn("Date & Time");
            cityDataTableModel.addColumn("Temperature");
            cityDataTableModel.addColumn("Status");

            // Table
            cityDataTable = new JTable(cityDataTableModel);
            JScrollPane scrollPane = new JScrollPane(cityDataTable);

            add(dataOptionBox, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
        }
    }
}
