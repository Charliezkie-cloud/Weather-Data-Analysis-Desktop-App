package WeatherAnalysisApp.Events.Main;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Components.CustomJOptionPane;
import WeatherAnalysisApp.Services.CachingService;
import WeatherAnalysisApp.Services.ListService;
import WeatherAnalysisApp.Services.TableService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clears the application data
 */
public class ClearButtonActionListener implements ActionListener {
    private final JList<String> cityList;
    private final JTable cityDataTable;

    public ClearButtonActionListener(JList<String> cityList, JTable cityDataTable) {
        this.cityList = cityList;
        this.cityDataTable = cityDataTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int UserOption = CustomJOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to clear the current weather data? This action cannot be undone.",
                "Confirmation",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (UserOption != JOptionPane.OK_OPTION)
            return;

        Data.CITIES_DATA.clear();
        ListService.clearCityList(cityList);
        TableService.clearCityDataTable(cityDataTable);
        CachingService.deleteWeatherData();
    }
}
