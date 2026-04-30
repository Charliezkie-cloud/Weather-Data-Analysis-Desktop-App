package WeatherAnalysisApp.Events.Main;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Components.CustomJOptionPane;
import WeatherAnalysisApp.Controllers.MainController;
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
 * Updates the cities data based on a selected city from city list.
 */
public class FetchButtonActionListener implements ActionListener {
    private final JList<String> cityList;
    private final DefaultListModel<String> cityListModel;
    private final JTable cityDataTable;
    private final DefaultTableModel cityDataTableModel;
    private final JComboBox<String> dataOptionBox;
    private final JButton fetchButton;

    public FetchButtonActionListener(
            JList<String> cityList,
            DefaultListModel<String> cityListModel,
            JTable cityDataTable,
            DefaultTableModel cityDataTableModel,
            JComboBox<String> dataOptionBox,
            JButton fetchButton
    ) {
        this.cityList = cityList;
        this.cityListModel = cityListModel;
        this.cityDataTable = cityDataTable;
        this.cityDataTableModel = cityDataTableModel;
        this.dataOptionBox = dataOptionBox;
        this.fetchButton = fetchButton;
    }

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
                ListService.updateCityList(cityList, cityListModel, cityDataTable);
                TableService.populateCityDataTable(selectedIndex, dataOptionBox, cityDataTable, cityDataTableModel);
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
}
