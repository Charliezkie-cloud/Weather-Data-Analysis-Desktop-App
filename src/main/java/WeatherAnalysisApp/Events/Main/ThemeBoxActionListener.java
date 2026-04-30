package WeatherAnalysisApp.Events.Main;

import WeatherAnalysisApp.Application.Data;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action listener for changing themes
 */
public class ThemeBoxActionListener implements ActionListener {
    private final JComboBox<String> themeBox;

    public ThemeBoxActionListener(JComboBox<String> themeBox) {
        this.themeBox = themeBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedItem = (String) themeBox.getSelectedItem();

        try {
            assert selectedItem != null;
            boolean isDarkTheme = selectedItem.equalsIgnoreCase("Dark");

            if (isDarkTheme)
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
            else
                UIManager.setLookAndFeel(new FlatMacLightLaf());

            FlatLaf.updateUILater();
            Data.APPLICATION_SETTINGS.setIsDarkTheme(isDarkTheme);
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Something went wrong while changing the theme.");
            System.err.println("Error: " + ex.getMessage());
        }
    }
}
