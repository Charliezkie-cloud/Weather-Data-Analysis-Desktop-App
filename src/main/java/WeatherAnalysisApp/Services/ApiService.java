package WeatherAnalysisApp.Services;

import WeatherAnalysisApp.Models.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * The class for API services
 */
public class ApiService {
    // ========== API REQUESTS ==========
    /**
     * Fetches the weather API <code>open-meteo API</code>
     * @param latitude The latitude of the city
     * @param longitude The longitude of the city
     * @return The <code>WeatherResponse</code> object
     */
    public static CompletableFuture<WeatherResponse> fetchCityByLatitudeLongitude(double latitude, double longitude) {
        String url = apiUrlBuilder(latitude, longitude);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(res -> {
                    if (res.statusCode() == 200)
                        return parseWeatherData(res.body());
                    return null;
                }).exceptionally(e -> {
                    System.err.println("Something went wrong while fetching the API.");
                    System.err.println("Error: " + e.getMessage());
                    return null;
                });
    }

    // ========== HELPERS ==========
    /**
     * Checks if the internet is available or not
     * @return True of internet is available otherwise false.
     */
    public static boolean isInternetAvailable() {
        try {
            String url = "https://www.google.com/generate_204";
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 204;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    /**
     * Builds the API url for you ;D
     * @param latitude The latitude of the city
     * @param longitude The longitude of the city
     * @return The complete URL of the API for the request
     */
    public static String apiUrlBuilder(double latitude, double longitude) {
        return "https://api.open-meteo.com/v1/forecast"
                + "?latitude=" + String.format("%.4f", latitude)
                + "&longitude=" + String.format("%.4f", longitude)
                + "&hourly=temperature_2m"
                + "&daily=weather_code,temperature_2m_max,temperature_2m_min"
                + "&timezone=Asia%2FSingapore"
                + "&past_days=7"
                + "&forecast_days=0";
    }
    
    /**
     * Parse the API weather data into a <code>WeatherResponse</code> object
     * @param weatherData The string data of the weather response
     * @return The <code>WeatherResponse</code> object
     */
    public static WeatherResponse parseWeatherData(String weatherData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(weatherData, WeatherResponse.class);
        } catch (JsonProcessingException e) {
            System.err.println("Something went wrong while parsing the weather data.");
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
}
