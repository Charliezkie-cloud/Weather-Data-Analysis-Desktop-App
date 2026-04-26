package WeatherAnalysisApp.Services;

import WeatherAnalysisApp.Models.City;
import WeatherAnalysisApp.Models.CityWeatherData;
import WeatherAnalysisApp.Models.SubModels.DailyPoint;
import WeatherAnalysisApp.Models.SubModels.HourlyPoint;
import WeatherAnalysisApp.Models.WeatherResponse;

import java.util.ArrayList;

public class Helpers {
    /**
     * Converts weather response to city weather data
     * @param city The <code>City</code> object of the city
     * @param weatherResponse The response data of the weather
     * @return The <code>CityWeatherData</code> object
     */
    public static CityWeatherData weatherResponseToCityWeatherData(City city, WeatherResponse weatherResponse) {
        ArrayList<HourlyPoint> hourlyPoints = new ArrayList<>();
        ArrayList<DailyPoint> dailyPoints = new ArrayList<>();

        for (int i = 0; i < weatherResponse.hourly.time.length; i++)
            hourlyPoints.add(new HourlyPoint(
                    weatherResponse.hourly.time[i],
                    weatherResponse.hourly.temperature_2m[i]
            ));

        for (int i = 0; i < weatherResponse.daily.time.length; i++)
            dailyPoints.add(new DailyPoint(
                    weatherResponse.daily.time[i],
                    weatherResponse.daily.weather_code[i]
            ));

        return new CityWeatherData(city, hourlyPoints, dailyPoints);
    }

    /**
     * Builds the City Weather Data for ya ;D
     * @param city The <code>City</code> model
     * @param time The <code>Time</code> array
     * @param temperature The <code>Temperature</code> array
     * @return The fully built <code>CityWeatherData</code> object ;D
     */

    /**
     * Builds the City Weather Data for ya ;D
     * @param city The <code>City</code> model
     * @param hourlyTimes The <code>Hourly Time</code> array
     * @param hourlyTemperatures The <code>Hourly Temperatures</code> array
     * @param dailyTimes The <code>Daily Times</code> array
     * @param dailyWeatherCodes The <code>Daily Weather Codes</code> array
     * @return The fully built <code>CityWeatherData</code> object ;D
     */
    public static CityWeatherData buildCityWeatherData(City city, String[] hourlyTimes, double[] hourlyTemperatures, String[] dailyTimes, int[] dailyWeatherCodes) {
        ArrayList<HourlyPoint> hourlyPoints = new ArrayList<>();
        ArrayList<DailyPoint> dailyPoints = new ArrayList<>();

        for (int i = 0; i < hourlyTimes.length; i++)
            hourlyPoints.add(new HourlyPoint(hourlyTimes[i], hourlyTemperatures[i]));

        for (int i = 0; i < dailyTimes.length; i++)
            dailyPoints.add(new DailyPoint(dailyTimes[i], dailyWeatherCodes[i]));

        return new CityWeatherData(city, hourlyPoints, dailyPoints);
    }
}
