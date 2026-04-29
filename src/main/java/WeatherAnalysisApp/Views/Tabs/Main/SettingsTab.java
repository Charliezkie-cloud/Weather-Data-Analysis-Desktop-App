package WeatherAnalysisApp.Views.Tabs.Main;

import WeatherAnalysisApp.Application.Data;

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


        // ========== Start of Components ==========

        ProjectInformationPanel projectInformationPanel = new ProjectInformationPanel();

        // ========== End of Components ==========

        add(projectInformationPanel, BorderLayout.NORTH);
    }

    /**
     * The project information panel
     */
    private class ProjectInformationPanel extends Panel {
        public ProjectInformationPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Project Information"),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)
            ));

            // ========== Start of Components ==========

            JLabel applicationNameLabel = new JLabel("Weather Analysis Desktop Application");
            applicationNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

            JLabel applicationVersionLabel = new JLabel(String.format("<html><b>Application version:</b> %s</html>", Data.APP_VERSION));
            JLabel developerLabel = new JLabel("<html><b>Developed by:</b> Charles Henry M. Tinoy Jr.</html>");
            JLabel organizationLabel = new JLabel("<html><b>School / Organization:</b> University of Cebu - BSIT</html>");
            JLabel openSourceLicenseLabel = new JLabel("<html><b>Open Source License:</b> MIT LICENSE</html>");

            // ========== End of Components ==========

            add(applicationNameLabel);
            add(Box.createVerticalStrut(10));
            add(applicationVersionLabel);
            add(Box.createVerticalStrut(10));
            add(developerLabel);
            add(Box.createVerticalStrut(10));
            add(organizationLabel);
            add(Box.createVerticalStrut(10));
            add(openSourceLicenseLabel);
        }
    }
}
