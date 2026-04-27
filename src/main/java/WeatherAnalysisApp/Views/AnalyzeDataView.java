package WeatherAnalysisApp.Views;

import WeatherAnalysisApp.Models.CityWeatherData;
import WeatherAnalysisApp.Models.SubModels.DailyPoint;
import WeatherAnalysisApp.Views.Components.FontRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AnalyzeDataView extends JFrame {
    // Date formatter
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

    public AnalyzeDataView(CityWeatherData selectedCityWeatherData) {

        /*
         * Set the global font
         * Segoe UI, Plain, 14 size
         */
        FontRenderer.setGlobalFont(new Font("Segoe UI", Font.PLAIN, 14));

        setTitle("Analyze Data");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // ========== Start of Components ==========

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());
        mainContent.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // ========== CHART TEST ==========

        int day = 1;

        // Chart dataset for maximum temperature
        XYSeries maximumTempData = new XYSeries("Maximum Temperature");
        for (DailyPoint dailyPoint : selectedCityWeatherData.dailyPoints) {
            maximumTempData.add(day, dailyPoint.temperature_2m_max);
            day++;
        }

        // Chart dataset for minimum temperature
        XYSeries minimumTempData = new XYSeries("Minimum Temperature");
        day = 0;
        for (DailyPoint dailyPoint : selectedCityWeatherData.dailyPoints) {
            minimumTempData.add(day, dailyPoint.temperature_2m_min);
            day++;
        }

        // Insert the datasets into the collection
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(maximumTempData);
        dataset.addSeries(minimumTempData);

        // Create the main XY Line Chart
        JFreeChart mainChart = ChartFactory.createXYLineChart("Temperatures for the last 7 days.", "Day", "°C", dataset);

        // The plot of the main chart
        XYPlot mainChartPlot = mainChart.getXYPlot();

        // The renderer of the plot and enable the dots and set the colors
        XYLineAndShapeRenderer mainChartRenderer = new XYLineAndShapeRenderer();

        // Set the series lines visible
        mainChartRenderer.setSeriesLinesVisible(0, true);
        mainChartRenderer.setSeriesLinesVisible(1, true);

        // Set the series line color
        mainChartRenderer.setSeriesPaint(0, Color.RED);
        mainChartRenderer.setSeriesPaint(1, Color.BLUE);

        // Popover of the series line
        mainChartRenderer.setDefaultToolTipGenerator((ds, s, i) -> {
            String label = ds.getSeriesKey(s).toString();
            double x = ds.getXValue(s, i);
            double y = ds.getYValue(s, i);
            return label + " - Day " + (int)x + ": " + y + "°C";
        });

        mainChartPlot.setRenderer(mainChartRenderer);

        // Display days as integer instead of double
        NumberAxis xAxis = (NumberAxis) mainChartPlot.getDomainAxis();
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        ChartPanel chartPanel = new ChartPanel(mainChart);

        // ========== CHART TEST ==========

        mainContent.add(chartPanel);

        // ========== End of Components ==========

        add(mainContent);
        pack();
    }
}
