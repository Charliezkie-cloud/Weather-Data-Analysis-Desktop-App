package WeatherAnalysisApp.Models.SubModels;

public class DailyPoint {
    public String time;
    public int weather_code;

    public DailyPoint(String time, int weather_code) {
        this.time = time;
        this.weather_code = weather_code;
    }
}
