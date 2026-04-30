package WeatherAnalysisApp.Events.Main;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Enums.Timezone;
import WeatherAnalysisApp.Services.HelperService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class TimezoneBoxActionListener implements ActionListener {
    private final JComboBox<String> timezoneBox;

    public TimezoneBoxActionListener(JComboBox<String> timezoneBox) {
        this.timezoneBox = timezoneBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedTimezone = (String) timezoneBox.getSelectedItem();
        assert selectedTimezone != null;

        Timezone selectedTimezoneEnum = HelperService.timezoneStringToEnum(selectedTimezone);
        if (selectedTimezoneEnum == null) return;

        Data.APPLICATION_SETTINGS.setTimezone(selectedTimezoneEnum);
    }
}
