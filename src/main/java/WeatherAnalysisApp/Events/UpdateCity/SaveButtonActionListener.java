package WeatherAnalysisApp.Events.UpdateCity;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Components.CustomJOptionPane;
import WeatherAnalysisApp.Models.City;
import WeatherAnalysisApp.Models.CityWeatherData;
import WeatherAnalysisApp.Models.WeatherResponse;
import WeatherAnalysisApp.Services.ApiService;
import WeatherAnalysisApp.Services.HelperService;
import WeatherAnalysisApp.Services.ListService;
import WeatherAnalysisApp.Services.TableService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

/**
 * The action listener for saving and fetching the updated city details
 */
public class SaveButtonActionListener implements ActionListener {
    private final JFrame addCityView;
    private final int selectedCityIndex;
    private final JTextField cityField;
    private final JTextField latitudeField;
    private final JTextField longitudeField;
    private final JButton saveButton;

    // Main view components
    private final JList<String> cityList;
    private final DefaultListModel<String> cityListModel;
    private final JTable cityDataTable;
    private final DefaultTableModel cityDataTableModel;
    private final JComboBox<String> dataOptionBox;

    public SaveButtonActionListener(
            JFrame addCityView,
            int selectedCityIndex,
            JTextField cityField,
            JTextField latitudeField,
            JTextField longitudeField,
            JButton saveButton,

            JList<String> cityList,
            DefaultListModel<String> cityListModel,
            JTable cityDataTable,
            DefaultTableModel cityDataTableModel,
            JComboBox<String> dataOptionBox
    ) {
        this.addCityView = addCityView;
        this.selectedCityIndex = selectedCityIndex;
        this.cityField = cityField;
        this.latitudeField = latitudeField;
        this.longitudeField = longitudeField;
        this.saveButton = saveButton;

        // Load main view components
        this.cityList = cityList;
        this.cityListModel = cityListModel;
        this.cityDataTable = cityDataTable;
        this.cityDataTableModel = cityDataTableModel;
        this.dataOptionBox = dataOptionBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!ApiService.isInternetAvailable()) {
            CustomJOptionPane.showErrorDialog(null, "You have no internet connection to fetch online data.");
            return;
        }

        String validationResult = validateForm();

        if (validationResult != null) {
            CustomJOptionPane.showErrorDialog(null, validationResult);
            return;
        }

        saveButton.setText("Fetching.");
        saveButton.setEnabled(false);

        Timer timer = new Timer(250, new FetchDataAnimation());
        timer.start();

        String cityName = cityField.getText();
        double latitude = Double.parseDouble(latitudeField.getText());
        double longitude = Double.parseDouble(longitudeField.getText());
        City newCityData = new City(cityName, latitude, longitude);

        CompletableFuture<WeatherResponse> response = ApiService.fetchCity(newCityData);

        response.thenAccept(data -> {
            CityWeatherData updatedCityWeatherData = HelperService.buildCityWeatherData(
                    newCityData,
                    data.hourly.time,
                    data.hourly.temperature_2m,
                    data.daily.time,
                    data.daily.weather_code,
                    data.daily.temperature_2m_max,
                    data.daily.temperature_2m_min
            );

            Data.CITIES_DATA.set(selectedCityIndex, updatedCityWeatherData);

            timer.stop();
            saveButton.setText("Save & Fetch Data");
            saveButton.setEnabled(false);
            addCityView.dispose();

            ListService.updateCityList(cityList, cityListModel, cityDataTable);

            SwingUtilities.invokeLater(() -> {
                cityList.setSelectedIndex(selectedCityIndex);
                TableService.populateCityDataTable(
                        cityList.getSelectedIndex(),
                        dataOptionBox,
                        cityDataTable,
                        cityDataTableModel
                );
            });

            CustomJOptionPane.showSuccessDialog(
                    null,
                    String.format("%s weather data has been successfully updated.", updatedCityWeatherData.city.name)
            );
        });
    }

    // ========== TIMERS ANIMATION ==========
    /**
     * The fetching animation for fetch button
     */
    private class FetchDataAnimation implements ActionListener {
        private int loadingLength = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (loadingLength > 3) {
                saveButton.setText("Fetching.");
                loadingLength = 0;
            } else {
                saveButton.setText(saveButton.getText() + ".");
            }

            loadingLength++;
        }
    }

    // ========== HELPERS ==========

    /**
     * Validates the form
     * @return The message of the validation
     */
    private String validateForm() {
        if (cityField.getText().trim().isEmpty())
            return "City is required.";
        if (latitudeField.getText().isEmpty())
            return "Latitude is required.";
        if (longitudeField.getText().isEmpty())
            return "Longitude is required.";

        try {
            Double.parseDouble(latitudeField.getText());
        } catch (NumberFormatException e) {
            return "Invalid latitude value.";
        }

        try {
            Double.parseDouble(longitudeField.getText());
        } catch (NumberFormatException e) {
            return "Invalid longitude value.";
        }

        return null;
    }
}
