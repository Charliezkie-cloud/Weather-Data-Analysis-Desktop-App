package WeatherAnalysisApp.Events.Main;

import WeatherAnalysisApp.Services.TableService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The action listener for the Data Option Box, the Daily or Hourly one
 */
public class DataOptionBoxActionListener implements ActionListener {
    private final JList<String> cityList;
    private final JComboBox<String> dataOptionBox;
    private final JTable cityDataTable;
    private final DefaultTableModel cityDataTableModel;

    public DataOptionBoxActionListener(
            JList<String> cityList,
            JComboBox<String> dataOptionBox,
            JTable cityDataTable,
            DefaultTableModel cityDataTableModel
        ) {
        this.cityList = cityList;
        this.dataOptionBox = dataOptionBox;
        this.cityDataTable = cityDataTable;
        this.cityDataTableModel = cityDataTableModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = cityList.getSelectedIndex();
        if (selectedIndex == -1) return;

        TableService.populateCityDataTable(selectedIndex, dataOptionBox, cityDataTable, cityDataTableModel);
    }
}
