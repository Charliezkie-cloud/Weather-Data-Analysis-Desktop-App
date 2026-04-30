package WeatherAnalysisApp.Views;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Components.FontRenderer;
import WeatherAnalysisApp.Components.Layouts.EditCity.BottomPanel;
import WeatherAnalysisApp.Components.Layouts.EditCity.TopPanel;
import WeatherAnalysisApp.Components.Layouts.Main.CenterPanel;
import WeatherAnalysisApp.Controllers.UpdateCityController;

import javax.swing.*;
import java.awt.*;

/**
 * The view for city
 * Extends from <code>JFrame</code> class
 */
public class EditCityView extends JFrame {
    public EditCityView(int selectedCityIndex) {
        /*
         * Set the global font
         * Segoe UI, Plain, 14 size
         */
        FontRenderer.setGlobalFont(new Font("Segoe UI", Font.PLAIN, 14));

        setTitle("Update City");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(650, 300);
        setMinimumSize(new Dimension(650, 300));
        setLocationRelativeTo(null);
        setIconImage(Data.APP_LOGO.getImage());

        // ========== Start of Components ==========

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());
        mainContent.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        TopPanel topPanel = new TopPanel();
        BottomPanel bottomPanel = new BottomPanel();

        mainContent.add(topPanel, BorderLayout.NORTH);
        mainContent.add(bottomPanel, BorderLayout.SOUTH);

        new UpdateCityController(
                this,
                selectedCityIndex,

                TopPanel.cityField,
                TopPanel.latitudeField,
                TopPanel.longitudeField,

                BottomPanel.resetButton,
                BottomPanel.saveButton,

                // Main view components
                CenterPanel.cityList,
                CenterPanel.cityListModel,
                CenterPanel.cityDataTable,
                CenterPanel.cityDataTableModel,
                CenterPanel.dataOptionBox
        );

        // ========== End of Components ==========

        add(mainContent);
        pack();
    }
}
