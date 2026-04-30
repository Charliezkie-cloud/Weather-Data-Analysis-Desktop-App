package WeatherAnalysisApp.Application;

import WeatherAnalysisApp.Enums.Timezone;
import WeatherAnalysisApp.Models.ApplicationSettings;
import WeatherAnalysisApp.Models.CityWeatherData;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Data {
    // ========== APPLICATION DATA ==========
    public static ArrayList<CityWeatherData> CITIES_DATA = new ArrayList<>();
    public static ApplicationSettings APPLICATION_SETTINGS = new ApplicationSettings(
            false,
            false,
            Timezone.ASIA_BANGKOK
    );

    // ========== APPLICATION INFORMATION'S ==========
    public static final String APP_AUTHOR = "Charles Henry M. Tinoy Jr.";
    public static final String APP_VERSION = "v1.0.0 - Stable Release";
    public static final String APP_ORGANIZATION = "University of Cebu - Bachelor of Science in Information Technology";
    public static final String OPEN_SOURCE_LICENSE = "MIT License";
    public static final int APP_JAVA_VERSION = 25;
    public static final String APP_EXTERNAL_LIBRARIES = "Flatlaf, Jackson Databind, JFreeChart";

    // ========== APPLICATION APP DATA ==========
    public static final String APP_NAME = "WeatherDataAnalysisDesktopApp";
    public static final File APP_DIR = new File(System.getenv("APPDATA"), APP_NAME);
    public static final File APP_CITY_DATA_FILEPATH = new File(APP_DIR, "Data");
    public static final File APP_SETTINGS_DATA_FILEPATH = new File(APP_DIR, "Settings");
    public static final ImageIcon APP_LOGO = new ImageIcon(Objects.requireNonNull(Data.class.getResource("/uc-logo.png")));
}
