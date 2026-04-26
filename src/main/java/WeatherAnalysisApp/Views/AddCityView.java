package WeatherAnalysisApp.Views;

import WeatherAnalysisApp.Controllers.AddCityController;
import WeatherAnalysisApp.Controllers.MainController;
import WeatherAnalysisApp.Views.Components.FontRenderer;
import WeatherAnalysisApp.Views.Layouts.AddCity.RightPanel;
import WeatherAnalysisApp.Views.Layouts.AddCity.LeftPanel;

import javax.swing.*;
import java.awt.*;

/**
 * The Add City view of the application
 * Extends from <code>JFrame</code> class
 */
public class AddCityView extends JFrame {
    public AddCityView(MainController mainController) {
        /*
         * Set the global font
         * Segoe UI, Plain, 14 size
         */
        FontRenderer.setGlobalFont(new Font("Segoe UI", Font.PLAIN, 14));

        setTitle("Add City");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // ========== Start of Components ==========

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());
        mainContent.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        LeftPanel leftPanel = new LeftPanel();
        RightPanel rightPanel = new RightPanel();

        mainContent.add(leftPanel, BorderLayout.WEST);
        mainContent.add(rightPanel, BorderLayout.EAST);

        new AddCityController(
                this,
                mainController,

                RightPanel.citiesTableModel,
                RightPanel.citiesTable,

                LeftPanel.cityField,
                LeftPanel.latitudeField,
                LeftPanel.longitudeField,
                LeftPanel.addButton,
                LeftPanel.resetButton
        );

        // ========== End of Components ==========

        add(mainContent);
        pack();
    }
}
