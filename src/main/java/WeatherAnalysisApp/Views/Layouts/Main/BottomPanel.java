package WeatherAnalysisApp.Views.Layouts.Main;

import javax.swing.*;
import java.awt.*;

/**
 * Bottom layout or the analysis panel
 * Extends from <code>JPanel</code> class
 */
public class BottomPanel extends JPanel {
    public static JLabel internetStatusLabel;

    public BottomPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // ========== Start of Components ==========

        // Status label
        internetStatusLabel = new JLabel("None");

        // ========== End of Components ==========

        add(new JLabel("Internet Status:"));
        add(internetStatusLabel);
    }
}
