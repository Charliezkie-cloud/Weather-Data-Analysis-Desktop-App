package WeatherAnalysisApp.Models;

/**
 * The model for City Weather Data
 */
public class CityWeatherData {
    public City city;
    public String[] time;
    public double[] temperature;

    public CityWeatherData(City city, String[] time, double[] temperature) {
        this.city = city;
        this.time = time;
        this.temperature = temperature;
    }
}
