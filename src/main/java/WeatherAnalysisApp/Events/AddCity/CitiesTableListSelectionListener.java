package WeatherAnalysisApp.Events.AddCity;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The list selection event listener for <code>Available Cities</code> table
 */
public class CitiesTableListSelectionListener implements ListSelectionListener {
    private final JTable citiesTable;
    private final JTextField cityField;
    private final JTextField latitudeField;
    private final JTextField longitudeField;

    public CitiesTableListSelectionListener(
            JTable citiesTable,
            JTextField cityField,
            JTextField latitudeField,
            JTextField longitudeField
    ) {
        this.citiesTable = citiesTable;
        this.cityField = cityField;
        this.latitudeField = latitudeField;
        this.longitudeField = longitudeField;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int row = citiesTable.getSelectedRow();

            String cityName = citiesTable.getValueAt(row, 0).toString();

            double latitude = Double.parseDouble(citiesTable.getValueAt(row, 1).toString());
            double longitude = Double.parseDouble(citiesTable.getValueAt(row, 2).toString());

            cityField.setText(cityName);
            latitudeField.setText(String.format("%.4f", latitude));
            longitudeField.setText(String.format("%.4f", longitude));
        }
    }
}
