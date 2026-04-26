package WeatherAnalysisApp.Controllers;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Models.CityWeatherData;
import WeatherAnalysisApp.Models.SubModels.DailyPoint;
import WeatherAnalysisApp.Models.SubModels.HourlyPoint;
import WeatherAnalysisApp.Models.WeatherResponse;
import WeatherAnalysisApp.Services.Api;
import WeatherAnalysisApp.Services.Helpers;
import WeatherAnalysisApp.Views.AddCityView;
import WeatherAnalysisApp.Views.Components.CustomJOptionPane;
import WeatherAnalysisApp.Views.Components.RowColorRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

public class MainController {
    // Date time and date formatter
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

    // Top panel components
    private final JButton fetchButton;
    private final JButton analyzeDataButton;
    private final JButton clearButton;
    private final JButton addCityButton;

    // Center panel components
    private final JComboBox<String> dataOptionBox;
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
            JButton clearButton,
            JButton addCityButton,

            JComboBox<String> dataOptionBox,
            DefaultListModel<String> cityListModel,
            JList<String> cityList,
            DefaultTableModel cityDataTableModel,
            JTable cityDataTable,

            JLabel statusLabel
    ) {
        // Load top panel components
        this.fetchButton = fetchButton;
        this.analyzeDataButton = analyzeDataButton;
        this.clearButton = clearButton;
        this.addCityButton = addCityButton;

        // Load center panel components
        this.dataOptionBox = dataOptionBox;
        this.cityListModel = cityListModel;
        this.cityList = cityList;
        this.cityDataTableModel = cityDataTableModel;
        this.cityDataTable = cityDataTable;

        // Load bottom panel components
        this.statusLabel = statusLabel;

        // Load cities to the list
        for (CityWeatherData cityWeatherData : Data.CITIES_DATA)
            cityListModel.addElement(cityWeatherData.city.name);

        // Load listeners
        cityList.addListSelectionListener(new CitiesListSelectionListener());
        addCityButton.addActionListener(new AddCityActionListener());
        fetchButton.addActionListener(new FetchButtonActionListener());
        dataOptionBox.addActionListener(new DataOptionBoxActionListener());

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
                int selectedIndex = cityList.getSelectedIndex();
                if (selectedIndex == -1) return;

                populateCityDataTable(selectedIndex);
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

    /**
     * Updates the cities data based on a selected city from city list.
     */
    private class FetchButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!Api.isInternetAvailable()) {
                CustomJOptionPane.showErrorDialog(null, "You have no internet connection.");
                return;
            }

            int selectedIndex = cityList.getSelectedIndex();
            if (selectedIndex == -1) {
                CustomJOptionPane.showErrorDialog(null, "Please select a city first.");
                return;
            }

            fetchButton.setText("Fetching.");

            Timer timer = new Timer(250, new FetchDataAnimation());
            timer.start();

            CityWeatherData selectedCityWeatherData = Data.CITIES_DATA.get(selectedIndex);

            CompletableFuture<WeatherResponse> res = Api.fetchCityByLatitudeLongitude(
                    selectedCityWeatherData.city.latitude,
                    selectedCityWeatherData.city.longitude
            );
            res.thenAccept(data -> {
                Data.CITIES_DATA.set(selectedIndex, Helpers.weatherResponseToCityWeatherData(
                        selectedCityWeatherData.city,
                        data
                ));

                SwingUtilities.invokeLater(() -> {
                    updateCityList();
                    populateCityDataTable(selectedIndex);
                });

                timer.stop();
                fetchButton.setText("Fetch");
                
                CustomJOptionPane.showSuccessDialog(null, String.format("%s latest weather data has successfully been updated!", selectedCityWeatherData.city.name));
            });
        }
    }

    private class DataOptionBoxActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = cityList.getSelectedIndex();
            if (selectedIndex == -1) return;

            populateCityDataTable(selectedIndex);
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

    // ========== TIMERS ANIMATIONS ==========
    private class FetchDataAnimation implements ActionListener {
        private int loadingLength = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (loadingLength > 3) {
                fetchButton.setText("Fetching.");
                loadingLength = 0;
            } else {
                fetchButton.setText(fetchButton.getText() + ".");
            }

            loadingLength++;
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

    private void populateCityDataTable(int selectedIndex) {
        String selectedDataOption = (String) dataOptionBox.getSelectedItem();
        if (selectedDataOption == null) return;

        switch (selectedDataOption) {
            case "Hourly":
                populateHourlyCityDataTable(selectedIndex);
                break;
            case "Daily":
                populateDailyCityDataTable(selectedIndex);
                break;
        }
    }

    /**
     * Populates the hourly city data table based on the selected index of the city list
     * @param selectedCityIndex The index of the selected city
     */
    private void populateHourlyCityDataTable(int selectedCityIndex) {
        clearCityDataTable();

        cityDataTableModel.setColumnCount(0);
        cityDataTableModel.addColumn("Date & Time");
        cityDataTableModel.addColumn("Temperature");
        cityDataTableModel.addColumn("Status");

        cityDataTable.setDefaultRenderer(Object.class, new RowColorRenderer());

        for (HourlyPoint hourlyPoint : Data.CITIES_DATA.get(selectedCityIndex).hourlyPoints) {
            LocalDateTime localDateTime = LocalDateTime.parse(hourlyPoint.time);

            String status;
            if (hourlyPoint.temperature <= 0) status = "Freezing";
            else if (hourlyPoint.temperature <= 10) status = "Cold";
            else if (hourlyPoint.temperature <= 20) status = "Cool";
            else if (hourlyPoint.temperature <= 30) status = "Warm";
            else if (hourlyPoint.temperature <= 35) status = "Hot";
            else status = "Very Hot";

            cityDataTableModel.addRow(new Object[]{
                    localDateTime.format(dateTimeFormatter),
                    String.format("%.1f°C", hourlyPoint.temperature),
                    status
            });
        }
    }

    /**
     * Populates the daily city data table based on the selected index of the city list
     * @param selectedCityIndex The index of the selected city
     */
    private void populateDailyCityDataTable(int selectedCityIndex) {
        clearCityDataTable();

        cityDataTableModel.setColumnCount(0);
        cityDataTableModel.addColumn("Date");
        cityDataTableModel.addColumn("Weather Code");
        cityDataTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());

        for (DailyPoint dailyPoint : Data.CITIES_DATA.get(selectedCityIndex).dailyPoints) {
            LocalDate localDate = LocalDate.parse(dailyPoint.time);

            cityDataTableModel.addRow(new Object[]{
                    localDate.format(dateFormatter),
                    dailyPoint.weather_code
            });
        }
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
            cityListModel.addElement(cityWeatherData.city.name);
    }
}
