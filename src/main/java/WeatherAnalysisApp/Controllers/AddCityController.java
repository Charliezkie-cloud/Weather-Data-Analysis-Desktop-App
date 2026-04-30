package WeatherAnalysisApp.Controllers;

import WeatherAnalysisApp.Events.AddCity.AddButtonActionListener;
import WeatherAnalysisApp.Events.AddCity.CitiesTableListSelectionListener;
import WeatherAnalysisApp.Events.AddCity.ResetButtonActionListener;

import javax.swing.*;

public class AddCityController {
    public AddCityController(
            // Add city view frame
            JFrame addCityView,

            // Right panel
            JTable citiesTable,

            // Left panel
            JTextField cityField,
            JTextField latitudeField,
            JTextField longitudeField,
            JButton addButton,
            JButton resetButton,

            // Main center panel
            JList<String> cityList,
            DefaultListModel<String> cityListModel,
            JTable cityDataTable
    ) {
        // Load listeners
        citiesTable.getSelectionModel().addListSelectionListener(new CitiesTableListSelectionListener(citiesTable, cityField, latitudeField, longitudeField));
        resetButton.addActionListener(new ResetButtonActionListener(cityField, latitudeField, longitudeField));
        addButton.addActionListener(new AddButtonActionListener(addCityView, addButton, cityField, latitudeField, longitudeField, cityList, cityListModel, cityDataTable));
    }
}
