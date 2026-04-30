package WeatherAnalysisApp.Components.Layouts.AddCity;

import javax.swing.*;
import java.awt.*;

/**
 * The left panel of the Add City view
 * Extends from <code>JPanel</code> class
 */
public class LeftPanel extends JPanel {
    public static JTextField cityField;
    public static JTextField latitudeField;
    public static JTextField longitudeField;
    public static JButton addButton;
    public static JButton resetButton;

    public LeftPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // ========== Start of Components ==========

        // ===== Form panel =====
        JPanel formPanel = new JPanel();

        // City panel
        JPanel cityPanel = new JPanel();

        // City label
        JLabel cityLabel = new JLabel("City");
        cityLabel.setAlignmentX(LEFT_ALIGNMENT);

        // City field
        cityField = new JTextField();
        cityField.setAlignmentX(LEFT_ALIGNMENT);
        cityField.setPreferredSize(new Dimension(406, 32));

        cityPanel.setLayout(new BoxLayout(cityPanel, BoxLayout.Y_AXIS));
        cityPanel.add(cityLabel);
        cityPanel.add(cityField);

        // Latitude and longitude group panel
        JPanel laLongGroupPanel = new JPanel();

        // Latitude panel
        JPanel latitudePanel = new JPanel();

        // Latitude label
        JLabel latitudeLabel = new JLabel("Latitude");
        latitudeLabel.setAlignmentX(LEFT_ALIGNMENT);

        // Latitude field
        latitudeField = new JTextField();
        latitudeField.setAlignmentX(LEFT_ALIGNMENT);
        latitudeField.setPreferredSize(new Dimension(200, 32));

        latitudePanel.setLayout(new BoxLayout(latitudePanel, BoxLayout.Y_AXIS));
        latitudePanel.add(latitudeLabel);
        latitudePanel.add(latitudeField);

        // Longitude panel
        JPanel longitudePanel = new JPanel();

        // Longitude label
        JLabel longitudeLabel = new JLabel("Longitude");
        longitudeLabel.setAlignmentX(LEFT_ALIGNMENT);

        // Longitude field
        longitudeField = new JTextField();
        longitudeField.setAlignmentX(LEFT_ALIGNMENT);
        longitudeField.setPreferredSize(new Dimension(200, 32));

        longitudePanel.setLayout(new BoxLayout(longitudePanel, BoxLayout.Y_AXIS));
        longitudePanel.add(longitudeLabel);
        longitudePanel.add(longitudeField);

        laLongGroupPanel.setLayout(new GridLayout(1, 2, 6, 0));
        laLongGroupPanel.add(latitudePanel);
        laLongGroupPanel.add(longitudePanel);

        // Longitude hint label
        JLabel longitudeHintLabel = new JLabel("Hint: Use negative longitudes for South America");
        longitudeHintLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        formPanel.setLayout(new GridLayout(3, 1, 0, 4));
        formPanel.add(cityPanel);
        formPanel.add(laLongGroupPanel);
        formPanel.add(longitudeHintLabel);

        // ===== Buttons panel =====
        JPanel buttonsPanel = new JPanel();

        // Reset button
        resetButton = new JButton("Reset");

        // Add button
        addButton = new JButton("Add");

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(resetButton);
        buttonsPanel.add(addButton);

        // ========== End of Components ==========

        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
