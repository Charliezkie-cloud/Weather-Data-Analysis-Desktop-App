package WeatherAnalysisApp.Controllers;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Events.UpdateCity.ResetButtonActionListener;
import WeatherAnalysisApp.Events.UpdateCity.SaveButtonActionListener;
import WeatherAnalysisApp.Models.City;
import WeatherAnalysisApp.Models.CityWeatherData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * The controller for <code>EditCityView</code>
 */
public class UpdateCityController {
    public UpdateCityController(
            // Edit city view frame
            JFrame editCityView,

            // Selected city index
            int selectedCityIndex,

            // Top panel
            JTextField cityField,
            JTextField latitudeField,
            JTextField longitudeField,

            // Bottom panel
            JButton resetButton,
            JButton saveButton,

            // Main controller components
            JList<String> cityList,
            DefaultListModel<String> cityListModel,
            JTable cityDataTable,
            DefaultTableModel cityDataTableModel,
            JComboBox<String> dataOptionBox
    ) {
        // Get the city weather data
        CityWeatherData selectedCityWeatherData = Data.CITIES_DATA.get(selectedCityIndex);
        City selectedCity = selectedCityWeatherData.city;

        // Load city details to fields
        cityField.setText(selectedCity.name);
        latitudeField.setText(Double.toString(selectedCity.latitude));
        longitudeField.setText(Double.toString(selectedCity.longitude));

        // Load listeners
        resetButton.addActionListener(new ResetButtonActionListener(cityField, latitudeField, longitudeField));
        saveButton.addActionListener(new SaveButtonActionListener(editCityView, selectedCityIndex, cityField, latitudeField, longitudeField, saveButton, cityList, cityListModel, cityDataTable, cityDataTableModel, dataOptionBox));
    }
}
