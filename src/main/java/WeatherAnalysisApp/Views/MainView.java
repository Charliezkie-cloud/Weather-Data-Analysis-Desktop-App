package WeatherAnalysisApp.Views;

import WeatherAnalysisApp.Controllers.MainController;
import WeatherAnalysisApp.Views.Components.FontRenderer;
import WeatherAnalysisApp.Views.Layouts.Main.BottomPanel;
import WeatherAnalysisApp.Views.Layouts.Main.CenterPanel;
import WeatherAnalysisApp.Views.Layouts.Main.TopPanel;
import WeatherAnalysisApp.Views.Tabs.Main.MainTab;
import WeatherAnalysisApp.Views.Tabs.Main.SettingsTab;

import javax.swing.*;
import java.awt.*;

/**
 * The main view of the application
 * Extends from <code>JFrame</code> class
 */
public class MainView extends JFrame {
    public MainView() {
        /*
         * Set the global font
         * Segoe UI, Plain, 14 size
         */
        FontRenderer.setGlobalFont(new Font("Segoe UI", Font.PLAIN, 14));

        setTitle("Weather Data Analysis Desktop App by Charles Henry M. Tinoy Jr.");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1050, 700);
        setMinimumSize(new Dimension(800, 580));
        setLocationRelativeTo(null);

        // ========== Start of Components ==========

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Main tab pane
        JTabbedPane mainTabbedPane = new JTabbedPane();

        // Tabs
        mainTabbedPane.add("Dashboard", new MainTab());
        mainTabbedPane.add("Settings", new SettingsTab());

        mainContent.add(mainTabbedPane);

        // Main controller initialization
        new MainController(
                this,
                TopPanel.fetchButton,
                TopPanel.analyzeDataButton,
                TopPanel.clearButton,
                TopPanel.addCityButton,

                CenterPanel.dataOptionBox,
                CenterPanel.cityListModel,
                CenterPanel.cityList,
                CenterPanel.cityDataTableModel,
                CenterPanel.cityDataTable,

                BottomPanel.statusLabel
        );

        // ========== End of Components ==========

        add(mainContent);
        pack();
    }
}
