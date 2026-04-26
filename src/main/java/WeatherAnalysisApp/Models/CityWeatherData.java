package WeatherAnalysisApp.Models;

/**
 * The model for City Weather Data
 */
public class CityWeatherData {
    public String name;
    public String[] time;
    public double[] temperature;

    public CityWeatherData(String name, String[] time, double[] temperature) {
        this.name = name;
        this.time = time;
        this.temperature = temperature;
    }
}
