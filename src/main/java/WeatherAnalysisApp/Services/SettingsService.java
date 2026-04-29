package WeatherAnalysisApp.Services;

import WeatherAnalysisApp.Application.Data;
import WeatherAnalysisApp.Models.ApplicationSettings;

import java.io.*;

public class SettingsService {
    /**
     * Reads the application settings data
     * @return The application settings
     */
    public static ApplicationSettings readApplicationSettingsData() {
        if (!Data.APP_SETTINGS_DATA_FILEPATH.exists())
            return null;

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(Data.APP_SETTINGS_DATA_FILEPATH))) {
            Object object = objectInputStream.readObject();

            if (object instanceof ApplicationSettings)
                return (ApplicationSettings) object;

            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Something went wrong while reading the application settings data.");
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Writes the current application settings data into binary file.
     */
    public static void writeApplicationSettingsData() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(Data.APP_SETTINGS_DATA_FILEPATH))) {
            objectOutputStream.writeObject(Data.APPLICATION_SETTINGS);
        } catch (IOException e) {
            System.err.println("Something went wrong while writing the application settings data.");
            System.err.println("Error: " + e.getMessage());
        }
    }
}
