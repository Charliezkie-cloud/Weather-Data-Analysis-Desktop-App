package WeatherAnalysisApp.Events.Main;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Components.CustomJOptionPane;
import WeatherAnalysisApp.Views.AnalyzeDataView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Opens the analysis data view and display the analyzed data.
 */
public class AnalyzeDataButtonActionListener implements ActionListener {
    private final JList<String> cityList;

    public AnalyzeDataButtonActionListener(JList<String> cityList) {
        this.cityList = cityList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = cityList.getSelectedIndex();

        if (selectedIndex == -1) {
            CustomJOptionPane.showErrorDialog(null, "Please select a city first.");
            return;
        }

        AnalyzeDataView analyzeDataView = new AnalyzeDataView(Data.CITIES_DATA.get(selectedIndex));
        analyzeDataView.setVisible(true);
    }
}
