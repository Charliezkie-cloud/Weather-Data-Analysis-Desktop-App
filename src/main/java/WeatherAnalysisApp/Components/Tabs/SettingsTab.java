package WeatherAnalysisApp.Components.Tabs;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Enums.Timezone;
import WeatherAnalysisApp.Services.HelperService;

import javax.swing.*;
import java.awt.*;

/**
 * The settings tab of the main view
 *
 */
public class SettingsTab extends JPanel {
    public static JCheckBox autoSaveCheckBox;
    public static JComboBox<String> timezoneBox;
    public static JComboBox<String> themeBox;
    public static JButton checkInternetButton;

    public SettingsTab() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // ========== Start of Components ==========

        ApplicationSettingsPanel applicationSettingsPanel = new ApplicationSettingsPanel();
        ProjectInformationPanel projectInformationPanel = new ProjectInformationPanel();

        // ========== End of Components ==========

        add(applicationSettingsPanel, BorderLayout.NORTH);
        add(projectInformationPanel, BorderLayout.CENTER);
    }

    /**
     * The application settings panel
     */
    private static class ApplicationSettingsPanel extends JPanel {
        public ApplicationSettingsPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Application Settings"),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)
            ));

            // ========== Start of Components ==========

            // Auto save checkbox
            autoSaveCheckBox = new JCheckBox("Auto Save Data on Exit");
            autoSaveCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Timezone panel
            JPanel timezonePanel = new JPanel();
            timezonePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            timezonePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Load timezones
            String[] timezones = new String[Timezone.values().length];
            int timezoneIndex = 0;
            for (Timezone item : Timezone.values()) {
                timezones[timezoneIndex] = HelperService.timezoneEnumToString(item);
                timezoneIndex++;
            }

            timezoneBox = new JComboBox<>(timezones);

            timezonePanel.add(new JLabel("Timezone:"));
            timezonePanel.add(timezoneBox);

            // Theme panel
            JPanel themePanel = new JPanel();
            themePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            themePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            themeBox = new JComboBox<>(new String[] { "Light", "Dark" });

            themePanel.add(new JLabel("Theme:"));
            themePanel.add(themeBox);

            // Check internet button
            checkInternetButton = new JButton("Check Internet Connection");
            checkInternetButton.setAlignmentX(Component.LEFT_ALIGNMENT);

            // ========== End of Components ==========

            add(autoSaveCheckBox);
            add(Box.createVerticalStrut(5));
            add(timezonePanel);
            add(Box.createVerticalStrut(5));
            add(themePanel);
            add(Box.createVerticalStrut(5));
            add(checkInternetButton);
        }
    }

    /**
     * The project information panel
     */
    private static class ProjectInformationPanel extends JPanel {
        public ProjectInformationPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Project Information"),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)
            ));

            // ========== Start of Components ==========

            JLabel applicationNameLabel = new JLabel("Weather Analysis Desktop Application");
            applicationNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

            JLabel applicationVersionLabel = new JLabel(String.format("<html><b>Version:</b> %s</html>", Data.APP_VERSION));
            JLabel developerLabel = new JLabel(String.format("<html><b>Developed by:</b> %s</html>", Data.APP_AUTHOR));
            JLabel organizationLabel = new JLabel(String.format("<html><b>School / Organization:</b> %s</html>", Data.APP_ORGANIZATION));
            JLabel openSourceLicenseLabel = new JLabel(String.format("<html><b>Open source license:</b> %s</html>", Data.OPEN_SOURCE_LICENSE));
            JLabel javaVersionLabel = new JLabel(String.format("<html><b>Java runtime:</b> Java %d</html>", Data.APP_JAVA_VERSION));
            JLabel externalLibrariesLabel = new JLabel(String.format("<html><b>External libraries:</b> %s</html>", Data.APP_EXTERNAL_LIBRARIES));

            // ========== End of Components ==========

            add(applicationNameLabel);
            add(Box.createVerticalStrut(10));
            add(applicationVersionLabel);
            add(Box.createVerticalStrut(5));
            add(developerLabel);
            add(Box.createVerticalStrut(5));
            add(organizationLabel);
            add(Box.createVerticalStrut(5));
            add(openSourceLicenseLabel);
            add(Box.createVerticalStrut(5));
            add(javaVersionLabel);
            add(Box.createVerticalStrut(5));
            add(externalLibrariesLabel);
        }
    }
}
