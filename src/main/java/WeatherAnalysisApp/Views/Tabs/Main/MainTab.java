package WeatherAnalysisApp.Views.Tabs.Main;

import WeatherAnalysisApp.Views.Layouts.Main.BottomPanel;
import WeatherAnalysisApp.Views.Layouts.Main.CenterPanel;
import WeatherAnalysisApp.Views.Layouts.Main.TopPanel;

import javax.swing.*;
import java.awt.*;

/**
 * The main tab of the main view
 * Extends from <code>JPanel</code> class
 */
public class MainTab extends JPanel {
    public MainTab() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        TopPanel topPanel = new TopPanel();
        CenterPanel centerPanel = new CenterPanel();
        BottomPanel bottomPanel = new BottomPanel();

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
