package WeatherAnalysisApp.Views.Layouts.AnalyzeData;


import javax.swing.*;
import java.awt.*;

/**
 * The bottom panel for <code>AnalyizeData</code> view
 * extends from <code>JPanel</code> class
 */
public class BottomPanel extends JPanel {
    public static JLabel cityLabel;
    public static JLabel periodLabel;
    public static JLabel averageTemperatureLabel;
    public static JLabel highestTemperatureLabel;
    public static JLabel lowestTemperatureLabel;
    public static JLabel trendLabel;

    public BottomPanel() {
        setLayout(new GridLayout(3, 2));
        setBorder(BorderFactory.createTitledBorder("Weather Summary"));

        // ========== Start of Components ==========

        // City panel
        JPanel cityPanel = new JPanel();
        cityPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // City label
        cityLabel = new JLabel("None");

        cityPanel.add(new JLabel("<html><b>City:</b></html>"));
        cityPanel.add(cityLabel);

        // Period panel
        JPanel periodPanel = new JPanel();
        periodPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Period label
        periodLabel = new JLabel("I don't know ;D");

        periodPanel.add(new JLabel("<html><b>Period:</b></html>"));
        periodPanel.add(periodLabel);

        // Average temperature panel
        JPanel averageTemperaturePanel = new JPanel();
        averageTemperaturePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Average temperature label
        averageTemperatureLabel = new JLabel("None");

        averageTemperaturePanel.add(new JLabel("<html><b>Average Temperature:</b></html>"));
        averageTemperaturePanel.add(averageTemperatureLabel);

        // Highest temperature panel
        JPanel highestTemperaturePanel = new JPanel();
        highestTemperaturePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Highest temperature label
        highestTemperatureLabel = new JLabel("Wala ko kahibalo bruh...");

        highestTemperaturePanel.add(new JLabel("<html><b>Highest Temperature:</b></html>"));
        highestTemperaturePanel.add(highestTemperatureLabel);

        // Lowest temperature panel
        JPanel lowestTemperaturePanel = new JPanel();
        lowestTemperaturePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Lowest temperature label
        lowestTemperatureLabel = new JLabel("Ambut lang...");

        lowestTemperaturePanel.add(new JLabel("<html><b>Lowest Temperature:</b></html>"));
        lowestTemperaturePanel.add(lowestTemperatureLabel);

        // Trend panel
        JPanel trendPanel = new JPanel();
        trendPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Trend label
        trendLabel = new JLabel("None");

        trendPanel.add(new JLabel("<html><b>Trend:</b></html>"));
        trendPanel.add(trendLabel);

        // ========== End of Components ==========

        add(cityPanel);
        add(periodPanel);
        add(averageTemperaturePanel);
        add(highestTemperaturePanel);
        add(lowestTemperaturePanel);
        add(trendPanel);
    }
}
