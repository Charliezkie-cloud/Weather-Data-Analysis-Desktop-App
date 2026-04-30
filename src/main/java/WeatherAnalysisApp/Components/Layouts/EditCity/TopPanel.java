package WeatherAnalysisApp.Components.Layouts.EditCity;

import javax.swing.*;
import java.awt.*;

/**
 * The top panel for <code>EditCityView</code>
 * extends from <code>JPanel</code> class
 */
public class TopPanel extends JPanel {
    public static JTextField cityField;
    public static JTextField latitudeField;
    public static JTextField longitudeField;

    public TopPanel() {
        setLayout(new BorderLayout());

        // ========== Start of Components ==========

        // City panel
        JPanel cityPanel = new JPanel();
        cityPanel.setLayout(new BoxLayout(cityPanel, BoxLayout.Y_AXIS));
        cityPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel cityLabel = new JLabel("City");
        cityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        cityField = new JTextField();
        cityField.setAlignmentX(Component.LEFT_ALIGNMENT);

        cityPanel.add(cityLabel);
        cityPanel.add(cityField);

        // Latitude and longitude panel
        JPanel laLongPanel = new JPanel();
        laLongPanel.setLayout(new GridLayout(2, 2, 4, 0));
        laLongPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Latitude panel
        JPanel latitudePanel = new JPanel();
        latitudePanel.setLayout(new BoxLayout(latitudePanel, BoxLayout.Y_AXIS));

        JLabel latitudeLabel = new JLabel("Latitude");
        latitudeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        latitudeField = new JTextField();
        latitudeField.setAlignmentX(Component.LEFT_ALIGNMENT);

        latitudePanel.add(latitudeLabel);
        latitudePanel.add(latitudeField);

        // Longitude panel
        JPanel longitudePanel = new JPanel();
        longitudePanel.setLayout(new BoxLayout(longitudePanel, BoxLayout.Y_AXIS));

        JLabel longitudeLabel = new JLabel("Longitude");
        longitudeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        longitudeField = new JTextField();
        longitudeField.setAlignmentX(Component.LEFT_ALIGNMENT);

        longitudePanel.add(longitudeLabel);
        longitudePanel.add(longitudeField);

        // Hint label
        JLabel hintLabel = new JLabel("Hint: Use negative longitudes for South America");
        hintLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hintLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        laLongPanel.add(latitudePanel);
        laLongPanel.add(longitudePanel);
        laLongPanel.add(hintLabel);

        // ========== End of Components ==========

        add(cityPanel, BorderLayout.NORTH);
        add(Box.createVerticalStrut(10));
        add(laLongPanel, BorderLayout.SOUTH);
    }
}
