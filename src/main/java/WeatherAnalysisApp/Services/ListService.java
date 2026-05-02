package WeatherAnalysisApp.Services;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Models.CityWeatherData;

import javax.swing.*;

/**
 * The services for JList
 */
public class ListService {
    /**
     * Clears the city list
     */
    public static void clearCityList(JList<String> cityList) {
        DefaultListModel<String> model = (DefaultListModel<String>) cityList.getModel();
        model.clear();
    }

    /**
     * Updates the city list
     */
    public static void updateCityList(JList<String> cityList, DefaultListModel<String> cityListModel, JTable cityDataTable) {
        TableService.clearCityDataTable(cityDataTable);
        clearCityList(cityList);

        for (CityWeatherData cityWeatherData : Data.CITIES_DATA)
            if (cityWeatherData != null)
                cityListModel.addElement(cityWeatherData.city.name);
    }
}
