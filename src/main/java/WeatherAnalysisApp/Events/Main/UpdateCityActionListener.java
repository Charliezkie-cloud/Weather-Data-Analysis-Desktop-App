package WeatherAnalysisApp.Events.Main;

import WeatherAnalysisApp.Components.CustomJOptionPane;
import WeatherAnalysisApp.Services.ApiService;
import WeatherAnalysisApp.Views.EditCityView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action listener for updating the city details
 */
public class UpdateCityActionListener implements ActionListener {
    private final JList<String> cityList;

    public UpdateCityActionListener(JList<String> cityList) {
        this.cityList = cityList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!ApiService.isInternetAvailable()) {
            CustomJOptionPane.showErrorDialog(null, "You have no internet connection.");
            return;
        }

        int selectedIndex = cityList.getSelectedIndex();
        if (selectedIndex == -1) return;

        EditCityView editCityView = new EditCityView(selectedIndex);
        editCityView.setVisible(true);
    }
}
