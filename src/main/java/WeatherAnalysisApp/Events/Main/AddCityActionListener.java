package WeatherAnalysisApp.Events.Main;

import WeatherAnalysisApp.Components.CustomJOptionPane;
import WeatherAnalysisApp.Services.ApiService;
import WeatherAnalysisApp.Views.AddCityView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The action event listener for <code>Add City</code> button
 */
public class AddCityActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!ApiService.isInternetAvailable()) {
            CustomJOptionPane.showErrorDialog(null, "You have no internet connection.");
            return;
        }

        AddCityView addCityView = new AddCityView();
        addCityView.setVisible(true);
    }
}
