package WeatherAnalysisApp.Events.Main;

import WeatherAnalysisApp.Application.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Auto save check box action listener
 */
public class AutoSaveCheckBoxActionListener implements ActionListener {
    private final JCheckBox autoSaveCheckBox;

    public AutoSaveCheckBoxActionListener(JCheckBox autoSaveCheckBox) {
        this.autoSaveCheckBox = autoSaveCheckBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Data.APPLICATION_SETTINGS.setIsAutoSave(autoSaveCheckBox.isSelected());
        System.out.println(autoSaveCheckBox.isSelected());
    }
}
