package WeatherAnalysisApp.Views.Layouts.AddCity;

import WeatherAnalysisApp.Models.City;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * The right panel of the Add City view
 * Extends from <code>JPanel</code> class
 */
public class RightPanel extends JPanel {
    private final ArrayList<City> SAMPLE_CITIES = new ArrayList<>();

    public static DefaultTableModel citiesTableModel;
    public static JTable citiesTable;

    public RightPanel() {
        loadSampleCities();

        setBorder(BorderFactory.createTitledBorder("Sample Cities"));

        // ========== Start of Components ==========

        // Cities default table model
        citiesTableModel = new DefaultTableModel();
        citiesTableModel.addColumn("City");
        citiesTableModel.addColumn("Longitude");
        citiesTableModel.addColumn("Latitude");

        for (City city : SAMPLE_CITIES)
            citiesTableModel.addRow(new Object[]{
                    city.name,
                    city.latitude,
                    city.longitude
            });

        // Cities table
        citiesTable = new JTable(citiesTableModel);

        // Cities table scroll pane
        JScrollPane citiesTableScrollPane = new JScrollPane(citiesTable);

        // ========== End of Components ==========

        add(citiesTableScrollPane);
    }

    private void loadSampleCities() {
        SAMPLE_CITIES.add(new City("Quezon City", 14.6488, 121.0509));
        SAMPLE_CITIES.add(new City("Manila City", 14.6042, 120.9822));
        SAMPLE_CITIES.add(new City("Davao City", 7.0731, 125.6128));
        SAMPLE_CITIES.add(new City("Caloocan City", 14.6495, 120.9679));
        SAMPLE_CITIES.add(new City("Cebu City", 10.3167, 123.8907));
        SAMPLE_CITIES.add(new City("Zamboanga City", 6.9103, 122.0739));
        SAMPLE_CITIES.add(new City("Taguig City", 14.5243, 121.0792));
        SAMPLE_CITIES.add(new City("Antipolo City", 14.6258, 121.1225));
        SAMPLE_CITIES.add(new City("Pasig City", 14.5869, 121.0614));
    }
}
