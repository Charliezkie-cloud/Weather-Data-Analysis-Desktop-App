package WeatherAnalysisApp.Controllers;

import WeatherAnalysisApp.Models.City;
import WeatherAnalysisApp.Views.Components.CustomJOptionPane;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCityController {
    // Main controller
    private final MainController mainController;

    // Left panel components
    private final JTextField cityField;
    private final JTextField latitudeField;
    private final JTextField longitudeField;
    private final JButton addButton;
    private final JButton resetButton;

    // Right panel components
    private final DefaultTableModel citiesTableModel;
    private final JTable citiesTable;

    public AddCityController(
            MainController mainController,

            DefaultTableModel citiesTableModel,
            JTable citiesTable,

            JTextField cityField,
            JTextField latitudeField,
            JTextField longitudeField,
            JButton addButton,
            JButton resetButton
    ) {
        // Load main controller
        this.mainController = mainController;

        // Load right panel
        this.citiesTableModel = citiesTableModel;
        this.citiesTable = citiesTable;

        // Load left panel
        this.cityField = cityField;
        this.latitudeField = latitudeField;
        this.longitudeField = longitudeField;
        this.addButton = addButton;
        this.resetButton = resetButton;

        // Load listeners
        citiesTable.getSelectionModel().addListSelectionListener(new CitiesTableListSelectionListener());
        resetButton.addActionListener(new ResetButtonActionListener());
        addButton.addActionListener(new AddButtonActionListener());
    }

    // ========== EVENTS ==========
    /**
     * The list selection event listener for <code>Available Cities</code> table
     */
    private class CitiesTableListSelectionListener implements ListSelectionListener {
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

    /**
     * The action listener for <code>Reset</code> button
     */
    private class ResetButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cityField.setText("");
            latitudeField.setText("");
            longitudeField.setText("");
        }
    }

    /**
     * The action listener for <code>Add</code> button
     */
    private class AddButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String validationMessage = validateForm();

            if (validationMessage != null)
                CustomJOptionPane.showErrorDialog(null, validationMessage);
        }
    }

    // ========== HELPERS ==========
    private String validateForm() {
        try {
            double longitude = Double.parseDouble(longitudeField.getText());
        } catch (NumberFormatException e) {
            return "Invalid latitude, please try again.";
        }

        try {
            double latitude = Double.parseDouble(latitudeField.getText());
        } catch (NumberFormatException e) {
            return "Invalid longitude, please try again.";
        }

        return null;
    }
}
