package WeatherAnalysisApp.Events.Main;

import WeatherAnalysisApp.Components.CustomJOptionPane;
import WeatherAnalysisApp.Services.ApiService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action listener for checking the internet connection
 */
public class CheckInternetButtonActionListener implements ActionListener {
    private final JButton checkInternetButton;
    private final JLabel internetStatusLabel;

    public CheckInternetButtonActionListener(JButton checkInternetButton, JLabel internetStatusLabel) {
        this.checkInternetButton = checkInternetButton;
        this.internetStatusLabel = internetStatusLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkInternetButton.setText("Checking.");
        checkInternetButton.setEnabled(false);
        Timer timer = new Timer(250, new CheckingInternetAnimation());
        timer.start();

        boolean isInternetAvailable = ApiService.isInternetAvailable();

        if (isInternetAvailable) {
            timer.stop();
            checkInternetButton.setText("Check Internet Connection");
            checkInternetButton.setEnabled(true);
            internetStatusLabel.setText("Available");
            internetStatusLabel.setForeground(new Color(22, 163, 74));
            CustomJOptionPane.showSuccessDialog(null, "Internet available!");
            return;
        }

        timer.stop();
        checkInternetButton.setText("Check Internet Connection");
        checkInternetButton.setEnabled(true);
        internetStatusLabel.setText("Not available");
        internetStatusLabel.setForeground(new Color(220, 38, 38));
        CustomJOptionPane.showErrorDialog(null, "Internet not available!");
    }

    /**
     * The checking animation for check internet button
     */
    private class CheckingInternetAnimation implements ActionListener {
        private int loadingLength = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (loadingLength > 3) {
                checkInternetButton.setText("Checking.");
                loadingLength = 0;
            } else {
                checkInternetButton.setText(checkInternetButton.getText() + ".");
            }

            loadingLength++;
        }
    }
}
