package WeatherAnalysisApp.Services;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Components.RowColorRenderer;
import WeatherAnalysisApp.Models.SubModels.DailyPoint;
import WeatherAnalysisApp.Models.SubModels.HourlyPoint;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The class for table services
 */
public class TableService {
    /**
     * Clears the city weather data table
     * @param cityDataTable The <code>JTable</code> of the city data
     */
    public static void clearCityDataTable(JTable cityDataTable) {
        DefaultTableModel model = (DefaultTableModel) cityDataTable.getModel();
        model.setRowCount(0);
    }

    /**
     * Populates the city data table based on the selected city index
     * @param selectedIndex The index of the selected city
     * @param dataOptionBox The <code>JOption</code> box of the data
     * @param cityDataTable The <code>JTable</code> of the city data
     * @param cityDataTableModel The default tab model for city data
     */
    public static void populateCityDataTable(int selectedIndex, JComboBox<String> dataOptionBox, JTable cityDataTable, DefaultTableModel cityDataTableModel) {
        String selectedDataOption = (String) dataOptionBox.getSelectedItem();
        if (selectedDataOption == null) return;

        switch (selectedDataOption) {
            case "Hourly":
                populateHourlyCityDataTable(selectedIndex, cityDataTable, cityDataTableModel);
                break;
            case "Daily":
                populateDailyCityDataTable(selectedIndex, cityDataTable, cityDataTableModel);
                break;
        }
    }

    /**
     * Populates the hourly city data table based on the selected index of the city list
     * @param selectedCityIndex The index of the selected city
     * @param cityDataTable The <code>JTable</code> of the city data
     * @param cityDataTableModel The default tab model for city data
     */
    public static void populateHourlyCityDataTable(int selectedCityIndex, JTable cityDataTable, DefaultTableModel cityDataTableModel)  {
        clearCityDataTable(cityDataTable);

        cityDataTableModel.setColumnCount(0);
        cityDataTableModel.addColumn("Date & Time");
        cityDataTableModel.addColumn("Temperature");
        cityDataTableModel.addColumn("Status");

        cityDataTable.setDefaultRenderer(Object.class, new RowColorRenderer());

        for (HourlyPoint hourlyPoint : Data.CITIES_DATA.get(selectedCityIndex).hourlyPoints) {
            LocalDateTime localDateTime = LocalDateTime.parse(hourlyPoint.time);
            String status = HelperService.getTemperatureStatus(hourlyPoint.temperature);

            cityDataTableModel.addRow(new Object[]{
                    localDateTime.format(HelperService.DATE_TIME_FORMATTER),
                    String.format("%.1f°C", hourlyPoint.temperature),
                    status
            });
        }
    }

    /**
     * Populates the daily city data table based on the selected index of the city list
     * @param selectedCityIndex The index of the selected city
     * @param cityDataTable The <code>JTable</code> of the city data
     * @param cityDataTableModel The default tab model for city data
     */
    public static void populateDailyCityDataTable(int selectedCityIndex, JTable cityDataTable, DefaultTableModel cityDataTableModel) {
        clearCityDataTable(cityDataTable);

        cityDataTableModel.setColumnCount(0);
        cityDataTableModel.addColumn("Date");
        cityDataTableModel.addColumn("Weather Code");
        cityDataTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());

        for (DailyPoint dailyPoint : Data.CITIES_DATA.get(selectedCityIndex).dailyPoints) {
            LocalDate localDate = LocalDate.parse(dailyPoint.time);
            String humanReadableCode = HelperService.getWeatherCodeString(dailyPoint.weather_code);

            cityDataTableModel.addRow(new Object[]{
                    localDate.format(HelperService.DATE_FORMATTER),
                    humanReadableCode
            });
        }
    }
}
