package WeatherAnalysisApp.Controllers;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Models.CityWeatherData;
import WeatherAnalysisApp.Models.SubModels.DailyPoint;
import WeatherAnalysisApp.Models.SubModels.HourlyPoint;
import WeatherAnalysisApp.Models.WeatherResponse;
import WeatherAnalysisApp.Services.ApiService;
import WeatherAnalysisApp.Services.CachingService;
import WeatherAnalysisApp.Services.HelperService;
import WeatherAnalysisApp.Views.AddCityView;
import WeatherAnalysisApp.Views.AnalyzeDataView;
import WeatherAnalysisApp.Views.Components.CustomJOptionPane;
import WeatherAnalysisApp.Views.Components.RowColorRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class MainController {
    // Parent component
    private final JFrame mainViewFrame;

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
    private final JMenuItem deleteCityMenuItem;

    // Left panel components
    private final JLabel internetStatusLabel;

    /**
     * The constructor of the program
     * @param mainViewFrame Main view frame - <code>JFrame</code>
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
            JFrame mainViewFrame,

            JButton fetchButton,
            JButton analyzeDataButton,
            JButton clearButton,
            JButton addCityButton,

            JComboBox<String> dataOptionBox,
            DefaultListModel<String> cityListModel,
            JList<String> cityList,
            DefaultTableModel cityDataTableModel,
            JTable cityDataTable,
            JMenuItem deleteCityMenuItem,

            JLabel internetStatusLabel
    ) {
        // Load the parent component
        this.mainViewFrame = mainViewFrame;

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
        this.deleteCityMenuItem = deleteCityMenuItem;

        // Load bottom panel components
        this.internetStatusLabel = internetStatusLabel;

        // Load cities to the list
        for (CityWeatherData cityWeatherData : Data.CITIES_DATA)
            cityListModel.addElement(cityWeatherData.city.name);

        // Load listeners
        mainViewFrame.addWindowListener(new MainViewWindowAdapter());
        cityList.addListSelectionListener(new CitiesListSelectionListener());
        addCityButton.addActionListener(new AddCityActionListener());
        fetchButton.addActionListener(new FetchButtonActionListener());
        dataOptionBox.addActionListener(new DataOptionBoxActionListener());
        clearButton.addActionListener(new ClearButtonActionListener());
        analyzeDataButton.addActionListener(new AnalyzeDataButtonActionListener());
        deleteCityMenuItem.addActionListener(new deleteCityDataActionListener());

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
            if (!ApiService.isInternetAvailable()) {
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
            if (!ApiService.isInternetAvailable()) {
                CustomJOptionPane.showErrorDialog(null, "You have no internet connection.");
                return;
            }

            int selectedIndex = cityList.getSelectedIndex();
            if (selectedIndex == -1) {
                CustomJOptionPane.showErrorDialog(null, "Please select a city first.");
                return;
            }

            fetchButton.setText("Fetching.");
            fetchButton.setEnabled(false);

            Timer timer = new Timer(250, new FetchDataAnimation());
            timer.start();

            CityWeatherData selectedCityWeatherData = Data.CITIES_DATA.get(selectedIndex);
            CompletableFuture<WeatherResponse> res = ApiService.fetchCityByLatitudeLongitude(
                    selectedCityWeatherData.city.latitude,
                    selectedCityWeatherData.city.longitude
            );

            res.thenAccept(data -> {
                Data.CITIES_DATA.set(selectedIndex, HelperService.weatherResponseToCityWeatherData(
                        selectedCityWeatherData.city,
                        data
                ));

                SwingUtilities.invokeLater(() -> {
                    updateCityList();
                    populateCityDataTable(selectedIndex);
                });

                timer.stop();
                fetchButton.setText("Fetch");
                fetchButton.setEnabled(true);
                CustomJOptionPane.showSuccessDialog(
                        null,
                        String.format("%s latest weather data has successfully been updated!",
                        selectedCityWeatherData.city.name
                ));
            });
        }
    }

    /**
     * The action listener for the Data Option Box, the Daily or Hourly one
     */
    private class DataOptionBoxActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = cityList.getSelectedIndex();
            if (selectedIndex == -1) return;

            populateCityDataTable(selectedIndex);
        }
    }

    /**
     * Clears the application data
     */
    private class ClearButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int UserOption = CustomJOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to clear the current weather data? This action cannot be undone.",
                    "Confirmation",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

            if (UserOption != JOptionPane.OK_OPTION)
                return;

            Data.CITIES_DATA.clear();
            clearCityList();
            clearCityDataTable();
            CachingService.deleteWeatherData();
        }
    }

    /**
     * Opens the analysis data view and display the analyzed data.
     */
    private class AnalyzeDataButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = cityList.getSelectedIndex();

            if (selectedIndex == -1) {
                CustomJOptionPane.showErrorDialog(null, "Please select a city first.");
                return;
            }

            AnalyzeDataView analyzeDataView = new AnalyzeDataView(Data.CITIES_DATA.get(selectedIndex));
            analyzeDataView.setVisible(true);
        }
    }

    /**
     * Deletes the city weather data based on the selected city list index
     */
    private class deleteCityDataActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = cityList.getSelectedIndex();
            if (selectedIndex == -1) {
                CustomJOptionPane.showErrorDialog(null, "Please select a city.");
                return;
            }

            CityWeatherData selectedCityWeatherData = Data.CITIES_DATA.get(selectedIndex);
            int deletionConfirmation = CustomJOptionPane.showConfirmDialog(
                    null,
                    String.format("Are you sure you want to remove %s from the list and delete its weather data? This action cannot be undone.", selectedCityWeatherData.city.name),
                    "Deletion Confirmation",
                    CustomJOptionPane.OK_CANCEL_OPTION,
                    CustomJOptionPane.WARNING_MESSAGE
            );

            if (deletionConfirmation != CustomJOptionPane.OK_OPTION)
                return;

            Data.CITIES_DATA.remove(selectedCityWeatherData);
            updateCityList();

            CustomJOptionPane.showSuccessDialog(
                    null,
                    String.format("%s weather data has been successfully deleted.", selectedCityWeatherData.city.name)
            );
        }
    }

    /**
     * The adapter of the main window
     */
    private class MainViewWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            int confirmation = CustomJOptionPane.showConfirmDialog(
                    null,
                    "Do you want to save your current weather data?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (confirmation != JOptionPane.OK_OPTION)
                return;

            CachingService.writeApplicationData();

            super.windowClosing(e);
        }
    }

    // ========== THREADS ==========
    /**
     * Checks the internet and display it in the UI
     */
    private class CheckInternet implements Runnable {
        public void run() {
            boolean isInternetAvailable = ApiService.isInternetAvailable();

            SwingUtilities.invokeLater(() -> {
                if (isInternetAvailable)
                    internetStatusLabel.setText("Internet available");
                else
                    internetStatusLabel.setText("Internet not available");
            });
        }
    }

    // ========== TIMERS ANIMATIONS ==========
    /**
     * The fetching animation for fetch button
     */
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

    /**
     * Populates the city data table based on the selected city index
     * @param selectedIndex The index of the selected city
     */
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
    private void populateHourlyCityDataTable(int selectedCityIndex)  {
        clearCityDataTable();

        cityDataTableModel.setColumnCount(0);
        cityDataTableModel.addColumn("Date & Time");
        cityDataTableModel.addColumn("Temperature");
        cityDataTableModel.addColumn("Status");

        cityDataTable.setDefaultRenderer(Object.class, new RowColorRenderer());

        for (HourlyPoint hourlyPoint : Data.CITIES_DATA.get(selectedCityIndex).hourlyPoints) {
            LocalDateTime localDateTime = LocalDateTime.parse(hourlyPoint.time);
            String status = HelperService.getTemperatureStatus(hourlyPoint.temperature);

            cityDataTableModel.addRow(new Object[]{
                    localDateTime.format(HelperService.DATE_TIME_FORMATTER),
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
            String humanReadableCode = HelperService.getWeatherCodeString(dailyPoint.weather_code);

            cityDataTableModel.addRow(new Object[]{
                    localDate.format(HelperService.DATE_FORMATTER),
                    humanReadableCode
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
        clearCityDataTable();
        clearCityList();

        for (CityWeatherData cityWeatherData : Data.CITIES_DATA)
            cityListModel.addElement(cityWeatherData.city.name);
    }
}
