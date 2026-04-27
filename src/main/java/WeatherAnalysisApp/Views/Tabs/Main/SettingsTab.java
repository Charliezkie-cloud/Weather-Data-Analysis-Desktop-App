package WeatherAnalysisApp.Views.Tabs.Main;

import javax.swing.*;
import java.awt.*;

/**
 * The settings tab of the main view
 *
 */
public class SettingsTab extends JPanel {
    public SettingsTab() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Charles message unswa?
        JLabel messageDawNiCharles = new JLabel("\"Wala pani na homan dawg\" - Charles");
        messageDawNiCharles.setFont(new Font("Segoe UI", Font.ITALIC, 14));

        add(messageDawNiCharles, BorderLayout.NORTH);
        add(new JLabel("Version: 0.1.0-beta"), BorderLayout.SOUTH);
    }
}
