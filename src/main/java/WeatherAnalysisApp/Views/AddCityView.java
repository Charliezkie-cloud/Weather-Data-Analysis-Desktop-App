package WeatherAnalysisApp.Views;

import WeatherAnalysisApp.Views.Components.FontRenderer;

import javax.swing.*;
import java.awt.*;

public class AddCityView extends JFrame {
    public AddCityView() {
        /*
         * Set the global font
         * Segoe UI, Plain, 14 size
         */
        FontRenderer.setGlobalFont(new Font("Segoe UI", Font.PLAIN, 14));

        setTitle("Add City");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1050, 700);
        setMinimumSize(new Dimension(800, 580));
        setLocationRelativeTo(null);
        pack();
    }
}
