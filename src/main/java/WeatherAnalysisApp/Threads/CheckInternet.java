package WeatherAnalysisApp.Threads;

import WeatherAnalysisApp.Services.ApiService;

import javax.swing.*;
import java.awt.*;

/**
 * Checks the internet and display it in the UI
 */
public class CheckInternet implements Runnable {
    private final JLabel internetStatusLabel;

    public CheckInternet(JLabel internetStatusLabel) {
        this.internetStatusLabel = internetStatusLabel;
    }

    public void run() {
        boolean isInternetAvailable = ApiService.isInternetAvailable();

        SwingUtilities.invokeLater(() -> {
            if (isInternetAvailable) {
                internetStatusLabel.setText("Available");
                internetStatusLabel.setForeground(new Color(22, 163, 74));
                return;
            }

            internetStatusLabel.setText("Not available");
            internetStatusLabel.setForeground(new Color(220, 38, 38));
        });
    }
}