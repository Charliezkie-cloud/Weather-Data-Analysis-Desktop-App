package WeatherAnalysisApp.Controllers;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Services.Helpers;
import WeatherAnalysisApp.Models.City;
import WeatherAnalysisApp.Models.WeatherResponse;
import WeatherAnalysisApp.Services.Api;
import WeatherAnalysisApp.Views.Components.CustomJOptionPane;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public class AddCityController {
    // Add City view
    private final JFrame addCityView;

    // Main controller
    private final MainController mainController;

    // Left panel components
    private final JTextField cityField;
    private final JTextField latitudeField;
    private final JTextField longitudeField;
    private final JButton addButton;
    private final JButton resetButton;

    // Right panel components
    private final DefaultTableModel citiesTableModel;
    private final JTable citiesTable;

    public AddCityController(
            JFrame addCityView,
            MainController mainController,

            DefaultTableModel citiesTableModel,
            JTable citiesTable,

            JTextField cityField,
            JTextField latitudeField,
            JTextField longitudeField,
            JButton addButton,
            JButton resetButton
    ) {
        // Load add city view frame
        this.addCityView = addCityView;

        // Load main controller
        this.mainController = mainController;

        // Load right panel
        this.citiesTableModel = citiesTableModel;
        this.citiesTable = citiesTable;

        // Load left panel
        this.cityField = cityField;
        this.latitudeField = latitudeField;
        this.longitudeField = longitudeField;
        this.addButton = addButton;
        this.resetButton = resetButton;

        // Load listeners
        citiesTable.getSelectionModel().addListSelectionListener(new CitiesTableListSelectionListener());
        resetButton.addActionListener(new ResetButtonActionListener());
        addButton.addActionListener(new AddButtonActionListener());
    }

    // ========== EVENTS ==========
    /**
     * The list selection event listener for <code>Available Cities</code> table
     */
    private class CitiesTableListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int row = citiesTable.getSelectedRow();

                String cityName = citiesTable.getValueAt(row, 0).toString();
                double latitude = Double.parseDouble(citiesTable.getValueAt(row, 1).toString());
                double longitude = Double.parseDouble(citiesTable.getValueAt(row, 2).toString());

                cityField.setText(cityName);
                latitudeField.setText(String.format("%.4f", latitude));
                longitudeField.setText(String.format("%.4f", longitude));
            }
        }
    }

    /**
     * The action listener for <code>Reset</code> button
     */
    private class ResetButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cityField.setText("");
            latitudeField.setText("");
            longitudeField.setText("");
        }
    }

    /**
     * The action listener for <code>Add</code> button
     */
    private class AddButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!Api.isInternetAvailable()) {
                CustomJOptionPane.showErrorDialog(null, "You have no internet connection.");
                return;
            }

            String validationMessage = validateForm();

            if (validationMessage != null) {
                CustomJOptionPane.showErrorDialog(null, validationMessage);
                return;
            }

            addButton.setText("Fetching.");

            Timer timer = new Timer(250, new FetchingDataAnimation());
            timer.start();

            String cityName = cityField.getText();
            double latitude = Double.parseDouble(latitudeField.getText());
            double longitude = Double.parseDouble(longitudeField.getText());

            CompletableFuture<WeatherResponse> res = Api.fetchCityByLatitudeLongitude(latitude, longitude);

            res.thenAccept(data -> {
                SwingUtilities.invokeLater(() -> {
                    Data.CITIES_DATA.add(Helpers.weatherResponseToCityWeatherData(new City(cityName, latitude, longitude), data));

                    mainController.updateCityList();
                    timer.stop();
                    addButton.setText("Add");

                    addCityView.dispose();
                    CustomJOptionPane.showSuccessDialog(
                            null,
                            String.format("%s latest weather data has successfully been added!", cityName)
                    );
                });
            });
        }
    }

    // ========== TIMERS ANIMATION ==========
    private class FetchingDataAnimation implements ActionListener {
        private int loadingLength = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (loadingLength > 3) {
                addButton.setText("Fetching.");
                loadingLength = 0;
            } else {
                addButton.setText(addButton.getText() + ".");
            }

            loadingLength++;
        }
    }

    // ========== HELPERS ==========
    /**
     * Validates the add city form
     * @return String if error otherwise null
     */
    private String validateForm() {
        if (cityField.getText().trim().isEmpty())
            return "City is required.";

        try {
            double longitude = Double.parseDouble(longitudeField.getText());
        } catch (NumberFormatException e) {
            return "Invalid latitude value.";
        }

        try {
            double latitude = Double.parseDouble(latitudeField.getText());
        } catch (NumberFormatException e) {
            return "Invalid longitude value.";
        }

        return null;
    }
}
