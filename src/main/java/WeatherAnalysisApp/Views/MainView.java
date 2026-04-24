package WeatherAnalysisApp.Views;

import WeatherAnalysisApp.Controllers.MainController;
import WeatherAnalysisApp.Views.Components.FontRenderer;
import WeatherAnalysisApp.Views.Layouts.Main.BottomPanel;
import WeatherAnalysisApp.Views.Layouts.Main.CenterPanel;
import WeatherAnalysisApp.Views.Layouts.Main.TopPanel;

import javax.swing.*;
import java.awt.*;

/**
 * The main view of the application
 * Extends from JFrame class
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
        pack();

        // ========== Start of Components ==========

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        TopPanel topPanel = new TopPanel();
        CenterPanel centerPanel = new CenterPanel();
        BottomPanel bottomPanel = new BottomPanel();

        mainContent.add(topPanel);
        mainContent.add(centerPanel);
        mainContent.add(bottomPanel);

        new MainController(
                TopPanel.fetchButton,
                TopPanel.analyzeDataButton,
                TopPanel.refreshButton,
                TopPanel.clearButton,
                TopPanel.addCityButton,

                CenterPanel.cityListModel,
                CenterPanel.cityList,
                CenterPanel.cityDataTableModel,
                CenterPanel.cityDataTable
        );

        // ========== End of Components ==========

        add(mainContent);
    }
}
