package WeatherAnalysisApp.Events.UpdateCity;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action listener for resetting the fields in update city view
 */
public class ResetButtonActionListener implements ActionListener {
    private final JTextField cityField;
    private final JTextField latitudeField;
    private final JTextField longitudeField;

    public ResetButtonActionListener(JTextField cityField, JTextField latitudeField, JTextField longitudeField
    ) {
        this.cityField = cityField;
        this.latitudeField = latitudeField;
        this.longitudeField = longitudeField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cityField.setText("");
        latitudeField.setText("");
        longitudeField.setText("");
    }
}
