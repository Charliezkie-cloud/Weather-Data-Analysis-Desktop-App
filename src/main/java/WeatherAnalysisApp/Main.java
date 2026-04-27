package WeatherAnalysisApp;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Views.MainView;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Data.initializeSampleCityData();

        SwingUtilities.invokeLater(() -> {
            FlatMacLightLaf.setup();
            MainView mainView = new MainView();
            mainView.setVisible(true);
        });

        // Api.runTestRequest();
    }
}
