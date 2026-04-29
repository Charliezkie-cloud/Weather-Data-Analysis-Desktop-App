package WeatherAnalysisApp.Views.Layouts.Main;

import WeatherAnalysisApp.Services.HelperService;

import javax.swing.*;
import java.awt.*;

/**
 * The top layout of the main content
 * Extends from <code>JPanel</code> class
 */
public class TopPanel extends JPanel {
    public static JButton fetchButton;
    public static JButton analyzeDataButton;
    public static JButton clearButton;
    public static JButton addCityButton;

    public TopPanel() {
        setLayout(new GridLayout(0, 2));

        // ========== Start of Components ==========

        LeftPanel leftPanel = new LeftPanel();
        RightPanel rightPanel = new RightPanel();

        // ========== End of Components ==========

        add(leftPanel);
        add(rightPanel);
    }

    /**
     * The left panel of the top panel
     */
    private class LeftPanel extends JPanel {
        public LeftPanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT));

            // ========== Start of Components ==========

            // Fetch button
            fetchButton = new JButton("Fetch");

            // Analyze data button
            analyzeDataButton = new JButton("Analyze Data");

            // Clear button
            clearButton = new JButton("Clear Data");

            // Add city button
            addCityButton = new JButton("Add City");

            // ========== End of Components ==========

            add(fetchButton);
            add(analyzeDataButton);
            add(clearButton);
            add(addCityButton);
        }
    }

    /**
     * The right panel of the top panel
     */
    private class RightPanel extends JPanel {
        public RightPanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
            setBorder(BorderFactory.createTitledBorder("Temperature Legend"));

            // ========== Start of Components ==========

            // Freezing panel
            JPanel freezingPanel = new JPanel();
            freezingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            JPanel freezingShape = new TemperatureLegendShape(HelperService.getColor(0));
            freezingPanel.add(new JLabel("Freezing"));
            freezingPanel.add(freezingShape);

            // Cold panel
            JPanel coldPanel = new JPanel();
            coldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            JPanel coldShape = new TemperatureLegendShape(HelperService.getColor(10));
            coldPanel.add(new JLabel("Cold"));
            coldPanel.add(coldShape);

            // Cool panel
            JPanel coolPanel = new JPanel();
            coolPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            JPanel coolShape = new TemperatureLegendShape(HelperService.getColor(20));
            coolPanel.add(new JLabel("Cool"));
            coolPanel.add(coolShape);

            // Warm panel
            JPanel warmPanel = new JPanel();
            warmPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            JPanel warmShape = new TemperatureLegendShape(HelperService.getColor(30));
            warmPanel.add(new JLabel("Warm"));
            warmPanel.add(warmShape);

            // Hot panel
            JPanel hotPanel = new JPanel();
            hotPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            JPanel hotShape = new TemperatureLegendShape(HelperService.getColor(35));
            hotPanel.add(new JLabel("Hot"));
            hotPanel.add(hotShape);

            // Very hot panel
            JPanel veryHotPanel = new JPanel();
            veryHotPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            JPanel veryHotShape = new TemperatureLegendShape(HelperService.getColor(40));
            veryHotPanel.add(new JLabel("Very Hot"));
            veryHotPanel.add(veryHotShape);

            // ========== End of Components ==========

            add(freezingPanel);
            add(coldPanel);
            add(coolPanel);
            add(warmPanel);
            add(hotPanel);
            add(veryHotPanel);
        }
    }

    // ========== SHAPES ==========
    private class TemperatureLegendShape extends JPanel {
        private final Color shapeColor;

        public TemperatureLegendShape(Color shapeColor) {
            this.shapeColor = shapeColor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D graphics2D = (Graphics2D) g.create();

            graphics2D.setColor(shapeColor);
            graphics2D.fillRect(0, 0, 100, 100);

            g.dispose();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(16, 16);
        }
    }
}
