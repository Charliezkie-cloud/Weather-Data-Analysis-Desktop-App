package WeatherAnalysisApp.Views.Layouts.Main;

import javax.swing.*;
import java.awt.*;

/**
 * The top layout of the main content
 * Extends from JPanel class
 */
public class TopPanel extends JPanel {
    public static JButton fetchButton;
    public static JButton analyzeDataButton;
    public static JButton refreshButton;
    public static JButton clearButton;
    public static JButton addCityButton;

    public TopPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // ========== Start of Components ==========

        // Fetch button
        fetchButton = new JButton("Fetch");

        // Analyze data button
        analyzeDataButton = new JButton("Analyze Data");

        // Refresh button
        refreshButton = new JButton("Refresh");

        // Clear button
        clearButton = new JButton("Clear");

        // Add city button
        addCityButton = new JButton("Add City");

        // ========== End of Components ==========

        add(fetchButton);
        add(analyzeDataButton);
        add(refreshButton);
        add(clearButton);
        add(addCityButton);
    }
}
