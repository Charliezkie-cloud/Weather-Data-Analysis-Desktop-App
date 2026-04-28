package WeatherAnalysisApp.Models.SubModels;

import java.io.Serializable;

/**
 * The model for Weather Point
 */
public class HourlyPoint implements Serializable {
    public String time;
    public double temperature;

    public HourlyPoint(String time, double temperature) {
        this.time = time;
        this.temperature = temperature;
    }
}
