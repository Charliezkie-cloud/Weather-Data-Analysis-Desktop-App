package WeatherAnalysisApp.Views.Layouts.Main;

import javax.swing.*;
import java.awt.*;

/**
 * Bottom layout or the analysis panel
 * Extends from <code>JPanel</code> class
 */
public class BottomPanel extends JPanel {
    public static JLabel statusLabel;

    public BottomPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // ========== Start of Components ==========

        // Status label
        statusLabel = new JLabel("None");

        // ========== End of Components ==========

        add(new JLabel("Status:"));
        add(statusLabel);
    }
}
