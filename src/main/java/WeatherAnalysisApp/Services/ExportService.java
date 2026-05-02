package WeatherAnalysisApp.Services;

import WeatherAnalysisApp.Components.CustomJOptionPane;
import WeatherAnalysisApp.Models.CityWeatherData;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The service class for exporting the data
 */
public class ExportService {
    /**
     * Shows the save file dialog for exporting the city weather data to excel file
     */
    public static void showSaveCityToExcel(CityWeatherData selectedWeatherData) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Save City Weather Data");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Excel Workbook (*.xlsx)", "xlsx"));

        int fileChooserOption = jFileChooser.showSaveDialog(null);

        if (fileChooserOption != JFileChooser.APPROVE_OPTION)
            return;

        File fileToSave = jFileChooser.getSelectedFile();
        writeDataToExcel(selectedWeatherData, fileToSave.getAbsolutePath());
    }

    /**
     * Writes the data to excel file
     * @param selectedCityWeatherData The index of the selected city
     * @param targetPath The path where to save the file
     */
    public static void writeDataToExcel(CityWeatherData selectedCityWeatherData, String targetPath) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            // Hourly sheet
            XSSFSheet hourlySheet = workbook.createSheet(String.format("%s - Hourly Weather Data", selectedCityWeatherData.city.name));

            XSSFRow hourlyHeaderRow = hourlySheet.createRow(0);
            hourlyHeaderRow.createCell(0).setCellValue("Date & Time");
            hourlyHeaderRow.createCell(1).setCellValue("Temperature (Celsius)");
            hourlyHeaderRow.createCell(2).setCellValue("Status");

            for (int i = 0; i < selectedCityWeatherData.hourlyPoints.size(); i++) {
                String dateTime = selectedCityWeatherData.hourlyPoints.get(i).time;
                double temperature = selectedCityWeatherData.hourlyPoints.get(i).temperature;
                LocalDateTime localDateTime = LocalDateTime.parse(dateTime);

                XSSFRow row = hourlySheet.createRow(i + 1);
                row.createCell(0).setCellValue(localDateTime.format(HelperService.DATE_TIME_FORMATTER));
                row.createCell(1).setCellValue(String.format("%.2f", temperature));
                row.createCell(2).setCellValue(HelperService.getTemperatureStatus(temperature));
            }

            // Daily sheet
            XSSFSheet dailySheet = workbook.createSheet(String.format("%s - Daily Weather Data", selectedCityWeatherData.city.name));

            XSSFRow dailyHeaderRow = dailySheet.createRow(0);
            dailyHeaderRow.createCell(0).setCellValue("Date");
            dailyHeaderRow.createCell(1).setCellValue("Minimum Temperature (Celsius)");
            dailyHeaderRow.createCell(2).setCellValue("Maximum Temperature (Celsius)");
            dailyHeaderRow.createCell(3).setCellValue("Average Temperature (Celsius)");
            dailyHeaderRow.createCell(4).setCellValue("Weather Code");

            for (int i = 0; i < selectedCityWeatherData.dailyPoints.size(); i++) {
                String dateTime = selectedCityWeatherData.dailyPoints.get(i).time;
                double minTemperature = selectedCityWeatherData.dailyPoints.get(i).temperature_2m_min;
                double maxTemperature = selectedCityWeatherData.dailyPoints.get(i).temperature_2m_max;
                double averageTemperature = (maxTemperature + minTemperature) / 2;
                String weatherCode = HelperService.getWeatherCodeString(selectedCityWeatherData.dailyPoints.get(i).weather_code);
                LocalDate localDate = LocalDate.parse(dateTime);

                XSSFRow row = dailySheet.createRow(i + 1);
                row.createCell(0).setCellValue(localDate.format(HelperService.DATE_FORMATTER));
                row.createCell(1).setCellValue(String.format("%.2f", minTemperature));
                row.createCell(2).setCellValue(String.format("%.2f", maxTemperature));
                row.createCell(3).setCellValue(String.format("%.2f", averageTemperature));
                row.createCell(4).setCellValue(weatherCode);
            }

            try (FileOutputStream fileOutputStream = new FileOutputStream(targetPath + ".xlsx")) {
                workbook.write(fileOutputStream);
            }

            CustomJOptionPane.showSuccessDialog(null, String.format(
                    "%s weather data has successfully been exported to excel file.",
                    selectedCityWeatherData.city.name
            ));
        } catch (IOException e) {
            System.err.println("Something went wrong while exporting the data into excel workbook.");
            System.err.println("Error: " + e.getMessage());
        }
    }
}
