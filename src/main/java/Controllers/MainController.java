package Controllers;

import Application.Data;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainController {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a");

    private final DefaultListModel<String> cityListModel;
    private final JList<String> cityList;
    private final DefaultTableModel cityDataTableModel;
    private final JTable cityDataTable;

    /**
     * Constructor
     * @param cityList The list component of the city
     * @param cityDataTableModel The default table model of the city table
     * @param cityDataTable The city data table
     */
    public MainController(DefaultListModel<String> cityListModel, JList<String> cityList, DefaultTableModel cityDataTableModel, JTable cityDataTable) {
        this.cityListModel = cityListModel;
        this.cityList = cityList;
        this.cityDataTableModel = cityDataTableModel;
        this.cityDataTable = cityDataTable;

        // Load cities to the list
        for (String city : Data.CITIES)
            cityListModel.addElement(city);

        cityList.addListSelectionListener(new CitiesListSelectionListener());
    }

    // ========== EVENTS ==========

    /**
     * The list selection event listener for JList
     */
    public class CitiesListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                clearTable();

                int selectedIndex = cityList.getSelectedIndex();
                if (selectedIndex == -1) return;

                String[] dateTimes;
                double[] temperatures;

                try {
                    dateTimes = Data.CITIES_DATETIME.get(selectedIndex);
                    temperatures = Data.CITIES_TEMPERATURE.get(selectedIndex);
                } catch (IndexOutOfBoundsException ex) {
                    return;
                }

                for (int i = 0; i < dateTimes.length; i++) {
                    LocalDateTime localDateTime = LocalDateTime.parse(dateTimes[i]);

                    String status;
                    if (temperatures[i] <= 0) status = "Freezing";
                    else if (temperatures[i] <= 10) status = "Cold";
                    else if (temperatures[i] <= 20) status = "Cool";
                    else if (temperatures[i] <= 30) status = "Warm";
                    else if (temperatures[i] <= 35) status = "Hot";
                    else status = "Very Hot";

                    cityDataTableModel.addRow(new Object[]{
                            localDateTime.format(dateTimeFormatter),
                            String.format("%.1f°C", temperatures[i]),
                            status
                    });
                }
            }
        }
    }

    // ========== HELPERS ==========
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) cityDataTableModel;
        model.setRowCount(0);
    }
}
