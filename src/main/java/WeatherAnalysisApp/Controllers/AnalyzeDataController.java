package WeatherAnalysisApp.Controllers;

import WeatherAnalysisApp.Models.CityWeatherData;
import WeatherAnalysisApp.Models.SubModels.DailyPoint;
import WeatherAnalysisApp.Services.Helpers;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;

import javax.swing.*;
import java.time.LocalDate;

/**
 * The contorller for <code>AnalyzeDataView</code>
 */
public class AnalyzeDataController {
    public AnalyzeDataController(
            // Selected city weather data
            CityWeatherData selectedCityWeatherData,

            // Center panel components
            XYSeries maximumTempData,
            XYSeries minimumTempData,
            JFreeChart mainLineChart,

            // Bottom panel components
            JLabel cityLabel,
            JLabel periodLabel,
            JLabel averageTemperatureLabel,
            JLabel highestTemperatureLabel,
            JLabel lowestTemperatureLabel,
            JLabel trendLabel
    ) {
        // System.out.println("========== TEST ==========");
        // System.out.println(selectedCityWeatherData.dailyPoints.size());
        // System.out.println("========== TEST ==========");

        // Load datasets into the XY series
        int days = 1;
        int size = selectedCityWeatherData.dailyPoints.size();

        double averageMaxTemperature = 0, highestMaxTemperature = Double.MIN_VALUE;
        double averageMinTemperature = 0, lowestMinTemperature = Double.MAX_VALUE;

        for (DailyPoint dailyPoint : selectedCityWeatherData.dailyPoints) {
            double maxTemp = dailyPoint.temperature_2m_max;
            double minTemp = dailyPoint.temperature_2m_min;

            maximumTempData.add(days, maxTemp);
            minimumTempData.add(days, minTemp);

            averageMaxTemperature += maxTemp;
            averageMinTemperature += minTemp;

            if (maxTemp > highestMaxTemperature)
                highestMaxTemperature = maxTemp;
            if (minTemp < lowestMinTemperature)
                lowestMinTemperature = minTemp;

            days++;
        }

        averageMaxTemperature /= size;
        averageMinTemperature /= size;
        DailyPoint firstDay = selectedCityWeatherData.dailyPoints.getFirst();
        DailyPoint lastDay = selectedCityWeatherData.dailyPoints.getLast();

        double averageTemperature = (averageMaxTemperature + averageMinTemperature) / 2;
        double firstDayTemperatureAverage = (firstDay.temperature_2m_max + firstDay.temperature_2m_min) / 2;
        double lastDayTemperatureAverage = (lastDay.temperature_2m_max + lastDay.temperature_2m_min) / 2;

        String trend;

        if (lastDayTemperatureAverage > firstDayTemperatureAverage)
            trend = "Increasing ↑";
        else if (lastDayTemperatureAverage < firstDayTemperatureAverage)
            trend = "Decreasing ↓";
        else
            trend = "Stable →";

        // System.out.println("========== TEST ==========");
        // System.out.println("Max: " + averageMaxTemperature);
        // System.out.println("Min: " + averageMinTemperature);
        // System.out.println("========== TEST ==========");

        // ========== Load the summary panel details ==========

        // Format the start and end date
        LocalDate startDate = LocalDate.parse(selectedCityWeatherData.dailyPoints.getFirst().time);
        LocalDate endDate = LocalDate.parse(selectedCityWeatherData.dailyPoints.getLast().time);

        // Calculate the average temperature
        double averageDate = 0;

        // Set the summary details
        cityLabel.setText(selectedCityWeatherData.city.name);
        periodLabel.setText(String.format(
                "%s to %s",
                startDate.format(Helpers.DATE_FORMATTER),
                endDate.format(Helpers.DATE_FORMATTER)
        ));
        averageTemperatureLabel.setText(String.format("%.2f °C", averageTemperature));
        highestTemperatureLabel.setText(String.format("%.2f °C", highestMaxTemperature));
        lowestTemperatureLabel.setText(String.format("%.2f °C", lowestMinTemperature));
        trendLabel.setText(trend);
    }
}
