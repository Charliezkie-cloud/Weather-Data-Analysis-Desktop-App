package WeatherAnalysisApp.Views.Components;

import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import java.awt.*;

/**
 * A chart renderer static class for JFreeCharts
 */
public class ChartRenderer {
    /**
     * A custom renderer for XY Line Chart
     * @return The Custom XY Line Chart Renderer
     */
    public static XYLineAndShapeRenderer CustomXYLineChartRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setDefaultToolTipGenerator((ds, s, i) -> {
            String label = ds.getSeriesKey(s).toString();
            double x = ds.getXValue(s, i);
            double y = ds.getYValue(s, i);
            return label + " - Day " + (int)x + ": " + y + "°C";
        });

        return renderer;
    }
}
