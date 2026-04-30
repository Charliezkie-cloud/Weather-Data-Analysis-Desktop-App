package WeatherAnalysisApp.Events.Main;

import WeatherAnalysisApp.Services.TableService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * The list selection event listener for <code>Cities</code> list
 */
public class CitiesListSelectionListener implements ListSelectionListener {
    private final JList<String> cityList;
    private final JComboBox<String> dataOptionBox;
    private final JTable cityDataTable;
    private final DefaultTableModel cityDataTableModel;

    public CitiesListSelectionListener(JList<String> cityList, JComboBox<String> dataOptionBox, JTable cityDataTable, DefaultTableModel cityDataTableModel) {
        this.cityList = cityList;
        this.dataOptionBox = dataOptionBox;
        this.cityDataTable = cityDataTable;
        this.cityDataTableModel = cityDataTableModel;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedIndex = cityList.getSelectedIndex();
            if (selectedIndex == -1) return;

            TableService.populateCityDataTable(selectedIndex, dataOptionBox, cityDataTable, cityDataTableModel);
        }
    }
}
