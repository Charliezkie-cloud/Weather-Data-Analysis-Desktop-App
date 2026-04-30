package WeatherAnalysisApp.Adapters;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Components.CustomJOptionPane;
import WeatherAnalysisApp.Services.CachingService;
import WeatherAnalysisApp.Services.SettingsService;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The adapter of the main window
 */
public class MainViewWindowAdapter extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        if (Data.APPLICATION_SETTINGS.isAutoSave) {
            SettingsService.writeApplicationSettingsData();
            CachingService.writeApplicationData();
            return;
        }

        int confirmation = CustomJOptionPane.showConfirmDialog(
                null,
                "Do you want to save your current weather data?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
        );

        SettingsService.writeApplicationSettingsData();

        if (confirmation != JOptionPane.OK_OPTION)
            return;

        CachingService.writeApplicationData();

        super.windowClosing(e);
    }
}
