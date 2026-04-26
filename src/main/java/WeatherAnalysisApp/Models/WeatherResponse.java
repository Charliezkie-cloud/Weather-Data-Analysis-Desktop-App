package WeatherAnalysisApp.Models;

import WeatherAnalysisApp.Models.SubModels.Daily;
import WeatherAnalysisApp.Models.SubModels.DailyUnits;
import WeatherAnalysisApp.Models.SubModels.Hourly;
import WeatherAnalysisApp.Models.SubModels.HourlyUnits;

/**
 * The model for weather response
 */
public class WeatherResponse {
    public double latitude;
    public double longitude;
    public double generationtime_ms;
    public int utc_offset_seconds;
    public String timezone;
    public String timezone_abbreviation;
    public int elevation;

    // Hourly units
    public HourlyUnits hourly_units;
    public Hourly hourly;

    // Daily units
    public DailyUnits daily_units;
    public Daily daily;
}