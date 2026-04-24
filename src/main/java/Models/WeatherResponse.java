package Models;

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

    public HourlyUnits hourly_units;
    public Hourly hourly;

    /**
     * A test for toString()
     * @return The test string of the properties
     */
    public String testToString() {
        return String.format("""
                Latitude: %s

                Longituide: %s

                Generation Time
                """);
    }
}