package WeatherAnalysisApp.Models.SubModels;

/**
 * The model for Weather Point
 */
public class HourlyPoint {
    public String time;
    public double temperature;

    public HourlyPoint(String time, double temperature) {
        this.time = time;
        this.temperature = temperature;
    }
}
