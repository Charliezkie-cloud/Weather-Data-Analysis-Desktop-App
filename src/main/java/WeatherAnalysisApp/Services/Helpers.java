package WeatherAnalysisApp.Services;

import WeatherAnalysisApp.Models.City;
import WeatherAnalysisApp.Models.CityWeatherData;
import WeatherAnalysisApp.Models.SubModels.DailyPoint;
import WeatherAnalysisApp.Models.SubModels.HourlyPoint;
import WeatherAnalysisApp.Models.WeatherResponse;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Helpers {
    // Date time and date formatter
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

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
                    weatherResponse.daily.weather_code[i],
                    weatherResponse.daily.temperature_2m_max[i],
                    weatherResponse.daily.temperature_2m_min[i]
            ));

        return new CityWeatherData(city, hourlyPoints, dailyPoints);
    }

    /**
     * Builds the City Weather Data for ya ;D
     * @param city The <code>City</code> model
     * @param hourlyTimes The <code>Hourly Time</code> array
     * @param hourlyTemperatures The <code>Hourly Temperatures</code> array
     * @param dailyTimes The <code>Daily Times</code> array
     * @param dailyWeatherCodes The <code>Daily Weather Codes</code> array
     * @return The fully built <code>CityWeatherData</code> object ;D
     */
    public static CityWeatherData buildCityWeatherData(City city, String[] hourlyTimes, double[] hourlyTemperatures, String[] dailyTimes, int[] dailyWeatherCodes, double[] temperature2mMax, double[] temperature2mMin) {
        ArrayList<HourlyPoint> hourlyPoints = new ArrayList<>();
        ArrayList<DailyPoint> dailyPoints = new ArrayList<>();

        for (int i = 0; i < hourlyTimes.length; i++)
            hourlyPoints.add(new HourlyPoint(hourlyTimes[i], hourlyTemperatures[i]));

        for (int i = 0; i < dailyTimes.length; i++)
            dailyPoints.add(new DailyPoint(
                    dailyTimes[i],
                    dailyWeatherCodes[i],
                    temperature2mMax[i],
                    temperature2mMin[i]
            ));

        return new CityWeatherData(city, hourlyPoints, dailyPoints);
    }

    /**
     * Converts the temperature into a string status
     * @param temperature The temperature of the city
     * @return The temperature status in string
     */
    public static String getTemperatureStatus(double temperature) {
        if (temperature <= 0) return "Freezing";
        else if (temperature <= 10) return "Cold";
        else if (temperature <= 20) return "Cool";
        else if (temperature <= 30) return "Warm";
        else if (temperature <= 35) return "Hot";
        return "Very Hot";
    }

    /**
     * Converts weather code into a human-readable string
     * WMO CODES: https://www.nodc.noaa.gov/archive/arc0021/0002199/1.1/data/0-data/HTML/WMO-CODE/WMO4677.HTM
     * @param weatherCode The weather code of the city
     * @return The weather in string
     */
    public static String getWeatherCodeString(int weatherCode) {
        return switch (weatherCode) {
            case 0 -> "Cloud development not observed or not observable";
            case 1 -> "Clouds generally dissolving or becoming less developed";
            case 2 -> "State of sky on the whole unchanged";
            case 3 -> "Clouds generally forming or developing";
            case 4 -> "Visibility reduced by smoke (e.g. forest fires, industrial smoke, volcanic ash)";
            case 5 -> "Haze";
            case 6 -> "Widespread dust in suspension in the air (not raised by wind)";
            case 7 -> "Dust or sand raised by wind (no dust/sand whirls or storms)";
            case 8 -> "Well developed dust or sand whirls seen near the station";
            case 9 -> "Duststorm or sandstorm within sight or at the station";
            case 10 -> "Mist";
            case 11 -> "Patches of shallow fog or ice fog";
            case 12 -> "More or less continuous shallow fog or ice fog";
            case 13 -> "Lightning visible, no thunder heard";
            case 14 -> "Precipitation within sight, not reaching the ground";
            case 15 -> "Precipitation within sight, distant (more than 5 km from station)";
            case 16 -> "Precipitation within sight, near to but not at the station";
            case 17 -> "Thunderstorm, but no precipitation at the time of observation";
            case 18 -> "Squalls at or within sight of the station";
            case 19 -> "Funnel cloud(s) (tornado or waterspout)";
            case 20 -> "Drizzle or snow grains (not falling as showers) during the preceding hour";
            case 21 -> "Rain (not freezing) during the preceding hour";
            case 22 -> "Snow during the preceding hour";
            case 23 -> "Rain and snow or ice pellets during the preceding hour";
            case 24 -> "Freezing drizzle or freezing rain during the preceding hour";
            case 25 -> "Shower(s) of rain during the preceding hour";
            case 26 -> "Shower(s) of snow, or rain and snow during the preceding hour";
            case 27 -> "Shower(s) of hail or rain and hail during the preceding hour";
            case 28 -> "Fog or ice fog during the preceding hour";
            case 29 -> "Thunderstorm (with or without precipitation) during the preceding hour";
            case 30 -> "Slight or moderate duststorm or sandstorm - decreasing";
            case 31 -> "Slight or moderate duststorm or sandstorm - no change";
            case 32 -> "Slight or moderate duststorm or sandstorm - increasing";
            case 33 -> "Severe duststorm or sandstorm - decreasing";
            case 34 -> "Severe duststorm or sandstorm - no change";
            case 35 -> "Severe duststorm or sandstorm - increasing";
            case 36 -> "Slight or moderate blowing snow (low level)";
            case 37 -> "Heavy drifting snow (low level)";
            case 38 -> "Slight or moderate blowing snow (high level)";
            case 39 -> "Heavy drifting snow (high level)";
            case 40 -> "Fog or ice fog at a distance (above observer level)";
            case 41 -> "Fog or ice fog in patches";
            case 42 -> "Fog or ice fog, sky visible (becoming thinner)";
            case 43 -> "Fog or ice fog, sky invisible (becoming thinner)";
            case 44 -> "Fog or ice fog, sky visible (no change)";
            case 45 -> "Fog or ice fog, sky invisible (no change)";
            case 46 -> "Fog or ice fog, sky visible (becoming thicker)";
            case 47 -> "Fog or ice fog, sky invisible (becoming thicker)";
            case 48 -> "Fog depositing rime, sky visible";
            case 49 -> "Fog depositing rime, sky invisible";
            case 50 -> "Slight intermittent drizzle (not freezing)";
            case 51 -> "Slight continuous drizzle (not freezing)";
            case 52 -> "Moderate intermittent drizzle (not freezing)";
            case 53 -> "Moderate continuous drizzle (not freezing)";
            case 54 -> "Heavy intermittent drizzle (dense)";
            case 55 -> "Heavy continuous drizzle (dense)";
            case 56 -> "Slight freezing drizzle";
            case 57 -> "Moderate or heavy freezing drizzle";
            case 58 -> "Slight drizzle and rain";
            case 59 -> "Moderate or heavy drizzle and rain";
            case 60 -> "Slight intermittent rain (not freezing)";
            case 61 -> "Slight continuous rain (not freezing)";
            case 62 -> "Moderate intermittent rain (not freezing)";
            case 63 -> "Moderate continuous rain (not freezing)";
            case 64 -> "Heavy intermittent rain (not freezing)";
            case 65 -> "Heavy continuous rain (not freezing)";
            case 66 -> "Slight freezing rain";
            case 67 -> "Moderate or heavy freezing rain";
            case 68 -> "Slight rain or drizzle and snow";
            case 69 -> "Moderate or heavy rain or drizzle and snow";
            case 70 -> "Slight intermittent fall of snowflakes";
            case 71 -> "Slight continuous fall of snowflakes";
            case 72 -> "Moderate intermittent fall of snowflakes";
            case 73 -> "Moderate continuous fall of snowflakes";
            case 74 -> "Heavy intermittent fall of snowflakes";
            case 75 -> "Heavy continuous fall of snowflakes";
            case 76 -> "Diamond dust (ice crystals)";
            case 77 -> "Snow grains";
            case 78 -> "Isolated star-like snow crystals";
            case 79 -> "Ice pellets";
            case 80 -> "Slight rain shower(s)";
            case 81 -> "Moderate or heavy rain shower(s)";
            case 82 -> "Violent rain shower(s)";
            case 83 -> "Slight shower(s) of rain and snow mixed";
            case 84 -> "Moderate or heavy shower(s) of rain and snow mixed";
            case 85 -> "Slight snow shower(s)";
            case 86 -> "Moderate or heavy snow shower(s)";
            case 87 -> "Slight shower(s) of snow pellets or small hail";
            case 88 -> "Moderate or heavy shower(s) of snow pellets or small hail";
            case 89 -> "Slight shower(s) of hail (no thunder)";
            case 90 -> "Moderate or heavy shower(s) of hail (no thunder)";
            case 91 -> "Slight rain with recent thunderstorm";
            case 92 -> "Moderate or heavy rain with recent thunderstorm";
            case 93 -> "Slight snow or hail with recent thunderstorm";
            case 94 -> "Moderate or heavy snow or hail with recent thunderstorm";
            case 95 -> "Slight or moderate thunderstorm with rain and/or snow";
            case 96 -> "Slight or moderate thunderstorm with hail";
            case 97 -> "Heavy thunderstorm with rain and/or snow";
            case 98 -> "Thunderstorm combined with duststorm or sandstorm";
            case 99 -> "Heavy thunderstorm with hail";
            default -> "Unknown Weather Code";
        };
    }
}
