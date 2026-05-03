package WeatherAnalysisApp.Components.Layouts.AnalyzeData;

import WeatherAnalysisApp.Components.ChartRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * The center panel for <code>AnalyzeData</code> view
 * extends from <code>JPanel</code> class
 */
public class CenterPanel extends JPanel {
    public static XYSeries maximumTempData;
    public static XYSeries minimumTempData;
    public static DefaultTableModel cityDataTableModel;

    public CenterPanel() {
        setLayout(new BorderLayout());

        // ========== Start of Components ==========

        LeftPanel leftpanel = new LeftPanel();
        RightPanel rightPanel = new RightPanel();

        // ========== End of Components ==========

        add(leftpanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    /**
     * The left panel for the center panel of analyze data view
     */
    @SuppressWarnings("all")
    private class LeftPanel extends JPanel {
        public LeftPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createTitledBorder("Temperature Chart"));

            // ========== Start of Components ==========

            // Datasets
            maximumTempData = new XYSeries("Maximum Temperature");
            minimumTempData = new XYSeries("Minimum Temperature");

            // Dataset collection
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(maximumTempData);
            dataset.addSeries(minimumTempData);

            // XY Line chart
            JFreeChart mainLineChart = ChartFactory.createXYLineChart("Temperature Trends Over the Last 7 Days", "Day", "°C", dataset);

            // Chart plot
            XYPlot mainLineChartXYPlot = mainLineChart.getXYPlot();
            NumberAxis mainChartXAxis = (NumberAxis) mainLineChartXYPlot.getDomainAxis();
            mainChartXAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            // Chart renderer
            XYLineAndShapeRenderer mainChartRenderer = ChartRenderer.CustomXYLineChartRenderer();
            mainLineChartXYPlot.setRenderer(mainChartRenderer);

            // Chart panel
            ChartPanel mainChartPanel = new ChartPanel(mainLineChart);
            mainChartPanel.setPreferredSize(new Dimension(700, 500));

            // ========== End of Components ==========

            add(mainChartPanel);
        }
    }

    /**
     * The right panel for the center panel of analyze data view
     */
    @SuppressWarnings("all")
    private class RightPanel extends JPanel {
        public RightPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createTitledBorder("Weather Codes Table"));

            // ========== Start of Components ==========

            cityDataTableModel = new DefaultTableModel();
            cityDataTableModel.addColumn("Date");
            cityDataTableModel.addColumn("Weather Code");

            // Main table
            JTable cityDataTable = new JTable(cityDataTableModel);
            JScrollPane scrollPane = new JScrollPane(cityDataTable);

            // ========== End of Components ==========

            add(scrollPane);
        }
    }
}
