package WeatherAnalysisApp.Controllers;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Views.AddCityView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainController {
    // Date time formatter
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a");

    // Top panel components
    private final JButton fetchButton;
    private final JButton analyzeDataButton;
    private final JButton refreshButton;
    private final JButton clearButton;
    private final JButton addCityButton;

    // Center panel components
    private final DefaultListModel<String> cityListModel;
    private final JList<String> cityList;
    private final DefaultTableModel cityDataTableModel;
    private final JTable cityDataTable;

    /**
     * The constructor of the program
     * @param fetchButton Fetch button - <code>Top component</code>
     * @param analyzeDataButton Analyze button - <code>Top component</code>
     * @param refreshButton Refresh button - <code>Top component</code>
     * @param clearButton Clear button - <code>Top component</code>
     * @param addCityButton Add city button - <code>Top component</code>
     * @param cityListModel City list model - <code>Center component</code>
     * @param cityList City list JList - <code>Center component</code>
     * @param cityDataTableModel City data table model - <code>Center component</code>
     * @param cityDataTable City data jtable - <code>Center component</code>
     */
    public MainController(
            JButton fetchButton,
            JButton analyzeDataButton,
            JButton refreshButton,
            JButton clearButton,
            JButton addCityButton,

            DefaultListModel<String> cityListModel,
            JList<String> cityList,
            DefaultTableModel cityDataTableModel,
            JTable cityDataTable
    ) {
        // Load top panel components
        this.fetchButton = fetchButton;
        this.analyzeDataButton = analyzeDataButton;
        this.refreshButton = refreshButton;
        this.clearButton = clearButton;
        this.addCityButton = addCityButton;

        // Load center panel components
        this.cityListModel = cityListModel;
        this.cityList = cityList;
        this.cityDataTableModel = cityDataTableModel;
        this.cityDataTable = cityDataTable;

        // Load cities to the list
        for (String city : Data.CITIES)
            cityListModel.addElement(city);

        // Load listeners
        cityList.addListSelectionListener(new CitiesListSelectionListener());
        addCityButton.addActionListener(new AddCityActionListener());
    }

    // ========== EVENTS ==========

    /**
     * The list selection event listener for <code>Cities</code> list
     */
    private class CitiesListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                clearTable();

                int selectedIndex = cityList.getSelectedIndex();
                if (selectedIndex == -1) return;

                String[] dateTimes;
                double[] temperatures;

                try {
                    dateTimes = Data.CITIES_DATETIME.get(selectedIndex);
                    temperatures = Data.CITIES_TEMPERATURE.get(selectedIndex);
                } catch (IndexOutOfBoundsException ex) {
                    return;
                }

                for (int i = 0; i < dateTimes.length; i++) {
                    LocalDateTime localDateTime = LocalDateTime.parse(dateTimes[i]);

                    String status;
                    if (temperatures[i] <= 0) status = "Freezing";
                    else if (temperatures[i] <= 10) status = "Cold";
                    else if (temperatures[i] <= 20) status = "Cool";
                    else if (temperatures[i] <= 30) status = "Warm";
                    else if (temperatures[i] <= 35) status = "Hot";
                    else status = "Very Hot";

                    cityDataTableModel.addRow(new Object[]{
                            localDateTime.format(dateTimeFormatter),
                            String.format("%.1f°C", temperatures[i]),
                            status
                    });
                }
            }
        }
    }

    /**
     * The action event listener for <code>Add City</code> button
     */
    private class AddCityActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddCityView addCityView = new AddCityView(MainController.this);

            addCityView.setVisible(true);
        }
    }

    // ========== HELPERS ==========
    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) cityDataTableModel;
        model.setRowCount(0);
    }
}
