package WeatherAnalysisApp.Components.Layouts.EditCity;

import javax.swing.*;
import java.awt.*;

/**
 * Bottom panel for <code>EditCityView</code>
 */
public class BottomPanel extends JPanel {
    public static JButton resetButton;
    public static JButton saveButton;

    public BottomPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 4, 0));

        // ========== Start of Components ==========

        // Reset button
        resetButton = new JButton("Reset");

        // Save button
        saveButton = new JButton("Save & Fetch Data");

        // ========== End of Components ==========

        add(resetButton);
        add(saveButton);
    }
}
