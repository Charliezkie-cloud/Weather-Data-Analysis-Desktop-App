package WeatherAnalysisApp;

import WeatherAnalysisApp.Services.ApplicationService;
import WeatherAnalysisApp.Views.MainView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ApplicationService.initialize();

        SwingUtilities.invokeLater(() -> { 
            MainView mainView = new MainView();
            mainView.setVisible(true);
        });
    }
}
