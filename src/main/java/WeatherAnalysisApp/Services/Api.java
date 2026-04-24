package WeatherAnalysisApp.Services;

import WeatherAnalysisApp.Models.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * The class for API services
 */
public class Api {
    // ========== START OF TESTS ==========
    /**
     * Runs the test http request
     */
    public static void runTestRequest() {
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=10.3167&longitude=123.8907&hourly=temperature_2m&past_days=0&forecast_days=7";

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            System.out.println("========== TEST NI CHARLES ==========");
            System.out.println("Source: Api.runTestRequest()");
            System.out.println(response);
            testParse(response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("========== TEST ERROR ==========");
            System.err.println("Error source: Api.runTestRequest()");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Runs the test parse from JSON string to object
     * @param data The data string of the request response
     */
    private static void testParse(String data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            WeatherResponse weatherResponse = objectMapper.readValue(data, WeatherResponse.class);

            System.out.println("========== TEST NI CHARLES ==========");
            System.out.println("Source: Api.testParse()");
            System.out.println("Weather response: " + weatherResponse.timezone);
        } catch (JsonProcessingException e) {
            System.err.println("========== TEST ERROR ==========");
            System.err.println("Error from: Api.testParse()");
            System.err.println(e.getMessage());
        }
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
                + "&past_days=0"
                + "&forecast_days=7";
    }
}
