package WeatherAnalysisApp.Models.SubModels;

public class DailyPoint {
    public String time;
    public int weather_code;
    public double temperature_2m_max;
    public double temperature_2m_min;

    public DailyPoint(String time, int weather_code, double temperature_2m_max, double temperature_2m_min
    ) {
        this.time = time;
        this.weather_code = weather_code;
        this.temperature_2m_max = temperature_2m_max;
        this.temperature_2m_min = temperature_2m_min;
    }
}
