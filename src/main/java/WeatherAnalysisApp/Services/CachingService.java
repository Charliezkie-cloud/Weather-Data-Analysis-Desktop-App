package WeatherAnalysisApp.Services;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Models.CityWeatherData;
import WeatherAnalysisApp.Views.Components.CustomJOptionPane;

import java.io.*;
import java.util.ArrayList;

/**
 * The class for Caching services
 */
public class CachingService {
    /**
     * Reads the application data
     * @return The data of the city weathers
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<CityWeatherData> readApplicationData() {
        if (!Data.APP_CITY_DATA_FILEPATH.exists())
            return null;

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(Data.APP_CITY_DATA_FILEPATH))) {
            Object object = objectInputStream.readObject();

            if (object instanceof ArrayList<?>)
                return (ArrayList<CityWeatherData>) object;

            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Something went wrong while reading the application data.");
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Writes the application data
     */
    public static void writeApplicationData() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(Data.APP_CITY_DATA_FILEPATH))) {
            objectOutputStream.writeObject(Data.CITIES_DATA);
        } catch (IOException e) {
            System.err.println("Something went wrong while writing the application data.");
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes the weather application data
     */
    public static void deleteWeatherData() {
        if (!Data.APP_CITY_DATA_FILEPATH.exists())
            return;

        Data.APP_CITY_DATA_FILEPATH.delete();
        CustomJOptionPane.showSuccessDialog(null, "The weather data has been deleted.");
    }
}
