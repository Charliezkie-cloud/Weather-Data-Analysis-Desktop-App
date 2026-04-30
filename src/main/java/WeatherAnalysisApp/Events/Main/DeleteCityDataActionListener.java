package WeatherAnalysisApp.Events.Main;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Components.CustomJOptionPane;
import WeatherAnalysisApp.Models.CityWeatherData;
import WeatherAnalysisApp.Services.ListService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Deletes the city weather data based on the selected city list index
 */
public class DeleteCityDataActionListener implements ActionListener {
    private final JList<String> cityList;
    private final DefaultListModel<String> cityListModel;
    private final JTable cityDataTable;

    public DeleteCityDataActionListener(JList<String> cityList, DefaultListModel<String> cityListModel, JTable cityDataTable) {
        this.cityList = cityList;
        this.cityListModel = cityListModel;
        this.cityDataTable = cityDataTable;
    }

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
        ListService.updateCityList(cityList, cityListModel, cityDataTable);

        CustomJOptionPane.showSuccessDialog(
                null,
                String.format("%s weather data has been successfully deleted.", selectedCityWeatherData.city.name)
        );
    }
}
