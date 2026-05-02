package WeatherAnalysisApp.Events.AddCity;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Components.CustomJOptionPane;
import WeatherAnalysisApp.Models.City;
import WeatherAnalysisApp.Models.WeatherResponse;
import WeatherAnalysisApp.Services.ApiService;
import WeatherAnalysisApp.Services.HelperService;
import WeatherAnalysisApp.Services.ListService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

/**
 * The action listener for <code>Add</code> button
 */
public class AddButtonActionListener implements ActionListener {
    private final JFrame addCityView;

    private final JButton addButton;
    private final JTextField cityField;
    private final JTextField latitudeField;
    private final JTextField longitudeField;

    private final JList<String> cityList;
    private final DefaultListModel<String> cityListModel;
    private final JTable cityDataTable;

    public AddButtonActionListener(
            JFrame addCityView,

            JButton addButton,
            JTextField cityField,
            JTextField latitudeField,
            JTextField longitudeField,

            JList<String> cityList,
            DefaultListModel<String> cityListModel,
            JTable cityDataTable
    ) {
        this.addCityView = addCityView;
        this.addButton = addButton;
        this.cityField = cityField;
        this.latitudeField = latitudeField;
        this.longitudeField = longitudeField;
        this.cityList = cityList;
        this.cityListModel = cityListModel;
        this.cityDataTable = cityDataTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!ApiService.isInternetAvailable()) {
            CustomJOptionPane.showErrorDialog(null, "You have no internet connection.");
            return;
        }

        String validationMessage = validateForm();

        if (validationMessage != null) {
            CustomJOptionPane.showErrorDialog(null, validationMessage);
            return;
        }

        addButton.setText("Fetching.");
        addButton.setEnabled(false);

        Timer timer = new Timer(250, new AddDataAnimation());
        timer.start();

        String cityName = cityField.getText();
        double latitude = Double.parseDouble(latitudeField.getText());
        double longitude = Double.parseDouble(longitudeField.getText());
        CompletableFuture<WeatherResponse> res = ApiService.fetchCity(latitude, longitude);

        res.thenAccept(data -> {
            Data.CITIES_DATA.add(HelperService.weatherResponseToCityWeatherData(new City(cityName, latitude, longitude), data));

            SwingUtilities.invokeLater(() ->
                ListService.updateCityList(cityList, cityListModel, cityDataTable)
            );

            timer.stop();
            addButton.setText("Add");
            addButton.setEnabled(true);
            addCityView.dispose();

            CustomJOptionPane.showSuccessDialog(
                    null,
                    String.format("%s latest weather data has successfully been added!", cityName)
            );
        });
    }

    // ========== TIMERS ANIMATION ==========
    /**
     * Timer animation for fetching the data
     */
    private class AddDataAnimation implements ActionListener {
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
