package WeatherAnalysisApp.Events.Main;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Components.CustomJOptionPane;
import WeatherAnalysisApp.Models.CityWeatherData;
import WeatherAnalysisApp.Services.ExportService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action listener for exporting city weather data into excel workbook
 */
public class ExportCityActionListener implements ActionListener {
    private final JList<String> cityList;

    public ExportCityActionListener(JList<String> cityList) {
        this.cityList = cityList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = cityList.getSelectedIndex();

        if (selectedIndex == -1) {
            CustomJOptionPane.showErrorDialog(null, "Please select a city.");
            return;
        }

        CityWeatherData selectedCityWeatherData = Data.CITIES_DATA.get(selectedIndex);
        ExportService.showSaveCityToExcel(selectedCityWeatherData);
    }
}
