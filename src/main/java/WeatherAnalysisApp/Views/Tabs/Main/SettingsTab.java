package WeatherAnalysisApp.Views.Tabs.Main;

import javax.swing.*;

/**
 * The settings tab of the main view
 *
 */
public class SettingsTab extends JPanel {
    public SettingsTab() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        add(new JLabel("Hello world ;D"));
    }
}
