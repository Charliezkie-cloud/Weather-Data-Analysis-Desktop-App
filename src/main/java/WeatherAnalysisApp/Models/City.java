package WeatherAnalysisApp.Models;

public class City {
    public String name;
    public double latitude;
    public double longitude;

    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
