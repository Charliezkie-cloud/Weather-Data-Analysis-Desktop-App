package WeatherAnalysisApp.Controllers;

import WeatherAnalysisApp.Adapters.MainViewWindowAdapter;
import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Events.Main.*;
import WeatherAnalysisApp.Models.CityWeatherData;
import WeatherAnalysisApp.Threads.CheckInternet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * The main controller of the main view
 * Naa dri ang bridge fr ;D
 */
public class MainController {
    public MainController(
            // Main view frame
            JFrame mainView,

            // Main tab -> toppanel
            JButton fetchButton,
            JButton analyzeDataButton,
            JButton clearButton,
            JButton addCityButton,

            // Main tab -> center panel
            JComboBox<String> dataOptionBox,
            DefaultListModel<String> cityListModel,
            JList<String> cityList,
            DefaultTableModel cityDataTableModel,
            JTable cityDataTable,
            JMenuItem deleteCityMenuItem,

            // Main tab -> bottom panel
            JLabel internetStatusLabel,

            // Settings tab
            JCheckBox autoSaveCheckBox,
            JComboBox<String> themeBox,
            JButton checkInternetButton
    ) {
        // Load cities to the list
        for (CityWeatherData cityWeatherData : Data.CITIES_DATA)
            cityListModel.addElement(cityWeatherData.city.name);

        // Load the application settings
        autoSaveCheckBox.setSelected(Data.APPLICATION_SETTINGS.isAutoSave);
        themeBox.setSelectedItem(!Data.APPLICATION_SETTINGS.isDarkTheme ? "Light" : "Dark");

        // Load listeners
        mainView.addWindowListener(new MainViewWindowAdapter());
        cityList.addListSelectionListener(new CitiesListSelectionListener(cityList, dataOptionBox, cityDataTable, cityDataTableModel));
        addCityButton.addActionListener(new AddCityActionListener());
        fetchButton.addActionListener(new FetchButtonActionListener(cityList, cityListModel, cityDataTable, cityDataTableModel, dataOptionBox, fetchButton));
        dataOptionBox.addActionListener(new DataOptionBoxActionListener(cityList, dataOptionBox, cityDataTable, cityDataTableModel));
        clearButton.addActionListener(new ClearButtonActionListener(cityList, cityDataTable));
        analyzeDataButton.addActionListener(new AnalyzeDataButtonActionListener(cityList));
        deleteCityMenuItem.addActionListener(new DeleteCityDataActionListener(cityList, cityListModel, cityDataTable));
        autoSaveCheckBox.addActionListener(new AutoSaveCheckBoxActionListener(autoSaveCheckBox));
        themeBox.addActionListener(new ThemeBoxActionListener(themeBox));
        checkInternetButton.addActionListener(new CheckInternetButtonActionListener(checkInternetButton, internetStatusLabel));

        // Load threads
        Thread checkInternetThread = new Thread(new CheckInternet(internetStatusLabel));

        // Run threads
        checkInternetThread.start();
    }
}
