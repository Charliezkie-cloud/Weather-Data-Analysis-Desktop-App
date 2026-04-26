package WeatherAnalysisApp.Controllers;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Models.CityWeatherData;
import WeatherAnalysisApp.Services.Api;
import WeatherAnalysisApp.Views.AddCityView;
import WeatherAnalysisApp.Views.Components.CustomJOptionPane;

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

    // Left panel components
    private final JLabel statusLabel;

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
            JTable cityDataTable,

            JLabel statusLabel
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

        // Load bottom panel components
        this.statusLabel = statusLabel;

        // Load cities to the list
        for (CityWeatherData cityWeatherData : Data.CITIES_DATA)
            cityListModel.addElement(cityWeatherData.name);

        // Load listeners
        cityList.addListSelectionListener(new CitiesListSelectionListener());
        addCityButton.addActionListener(new AddCityActionListener());

        // Load threads
        Thread checkInternetThread = new Thread(new CheckInternet());

        // Run threads
        checkInternetThread.start();
    }

    // ========== EVENTS ==========
    /**
     * The list selection event listener for <code>Cities</code> list
     */
    private class CitiesListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                clearCityDataTable();

                int selectedIndex = cityList.getSelectedIndex();
                if (selectedIndex == -1) return;

                String[] dateTimes;
                double[] temperatures;

                try {
                    dateTimes = Data.CITIES_DATA.get(selectedIndex).time;
                    temperatures = Data.CITIES_DATA.get(selectedIndex).temperature;
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
            if (!Api.isInternetAvailable()) {
                CustomJOptionPane.showErrorDialog(null, "You have no internet connection.");
                return;
            }

            AddCityView addCityView = new AddCityView(MainController.this);
            addCityView.setVisible(true);
        }
    }

    // ========== THREADS ==========
    /**
     * Checks the internet and display it in the UI
     */
    private class CheckInternet implements Runnable {
        public void run() {
            boolean isInternetAvailable = Api.isInternetAvailable();

            SwingUtilities.invokeLater(() -> {
                if (isInternetAvailable)
                    statusLabel.setText("Internet available");
                else
                    statusLabel.setText("Internet not available");
            });
        }
    }

    // ========== HELPERS ==========
    /**
     * Clears the city weather data table
     */
    private void clearCityDataTable() {
        DefaultTableModel model = (DefaultTableModel) cityDataTable.getModel();
        model.setRowCount(0);
    }

    /**
     * Clears the city list
     */
    private void clearCityList() {
        DefaultListModel<String> model = (DefaultListModel<String>) cityList.getModel();
        model.clear();
    }

    /**
     * Updates the city list
     */
    public void updateCityList() {
        clearCityList();

        for (CityWeatherData cityWeatherData : Data.CITIES_DATA)
            cityListModel.addElement(cityWeatherData.name);
    }
}
