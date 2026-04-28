package WeatherAnalysisApp.Models;

import java.io.Serializable;

/**
 * The model for City
 */
public class City implements Serializable {
    public String name;
    public double latitude;
    public double longitude;

    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
