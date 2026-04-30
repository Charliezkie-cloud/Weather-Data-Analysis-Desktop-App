package WeatherAnalysisApp.Views;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Controllers.AnalyzeDataController;
import WeatherAnalysisApp.Models.CityWeatherData;
import WeatherAnalysisApp.Components.FontRenderer;
import WeatherAnalysisApp.Components.Layouts.AnalyzeData.BottomPanel;
import WeatherAnalysisApp.Components.Layouts.AnalyzeData.CenterPanel;

import javax.swing.*;
import java.awt.*;

/**
 * The Data Analysis view of the application
 * Extends from <code>JFrame</code> class
 */
public class AnalyzeDataView extends JFrame {
    public AnalyzeDataView(CityWeatherData selectedCityWeatherData) {
        /*
         * Set the global font
         * Segoe UI, Plain, 14 size
         */
        FontRenderer.setGlobalFont(new Font("Segoe UI", Font.PLAIN, 14));

        setTitle("Weather Analysis");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(Data.APP_LOGO.getImage());

        // ========== Start of Components ==========

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());
        mainContent.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        CenterPanel centerPanel = new CenterPanel();
        BottomPanel bottomPanel = new BottomPanel();

        mainContent.add(centerPanel, BorderLayout.CENTER);
        mainContent.add(bottomPanel, BorderLayout.SOUTH);

        // Analyze data controller
        new AnalyzeDataController(
                this,
                selectedCityWeatherData,

                CenterPanel.maximumTempData,
                CenterPanel.minimumTempData,

                BottomPanel.cityLabel,
                BottomPanel.periodLabel,
                BottomPanel.averageTemperatureLabel,
                BottomPanel.highestTemperatureLabel,
                BottomPanel.lowestTemperatureLabel,
                BottomPanel.trendLabel
        );

        // ========== End of Components ==========

        add(mainContent);
        pack();
    }
}
