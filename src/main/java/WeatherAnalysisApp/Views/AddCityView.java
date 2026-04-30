package WeatherAnalysisApp.Views;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Components.Layouts.Main.CenterPanel;
import WeatherAnalysisApp.Controllers.AddCityController;
import WeatherAnalysisApp.Components.FontRenderer;
import WeatherAnalysisApp.Components.Layouts.AddCity.RightPanel;
import WeatherAnalysisApp.Components.Layouts.AddCity.LeftPanel;

import javax.swing.*;
import java.awt.*;

/**
 * The Add City view of the application
 * Extends from <code>JFrame</code> class
 */
public class AddCityView extends JFrame {
    public AddCityView() {
        /*
         * Set the global font
         * Segoe UI, Plain, 14 size
         */
        FontRenderer.setGlobalFont(new Font("Segoe UI", Font.PLAIN, 14));

        setTitle("Add City");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(Data.APP_LOGO.getImage());

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

                RightPanel.citiesTable,

                LeftPanel.cityField,
                LeftPanel.latitudeField,
                LeftPanel.longitudeField,
                LeftPanel.addButton,
                LeftPanel.resetButton,

                CenterPanel.cityList,
                CenterPanel.cityListModel,
                CenterPanel.cityDataTable
        );

        // ========== End of Components ==========

        add(mainContent);
        pack();
    }
}
