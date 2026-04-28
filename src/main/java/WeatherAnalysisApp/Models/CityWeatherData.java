package WeatherAnalysisApp.Models;

import WeatherAnalysisApp.Models.SubModels.DailyPoint;
import WeatherAnalysisApp.Models.SubModels.HourlyPoint;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The model for City Weather Data
 */
public class CityWeatherData implements Serializable {
    public City city;
    public ArrayList<HourlyPoint> hourlyPoints;
    public ArrayList<DailyPoint> dailyPoints;

    public CityWeatherData(City city, ArrayList<HourlyPoint> hourlyPoints, ArrayList<DailyPoint> dailyPoints) {
        this.city = city;

        hourlyPoints.sort((x, y) -> y.time.compareTo(x.time));
        this.hourlyPoints = hourlyPoints;

        dailyPoints.sort((x, y) -> y.time.compareTo(x.time));
        this.dailyPoints = dailyPoints;
    }
}
