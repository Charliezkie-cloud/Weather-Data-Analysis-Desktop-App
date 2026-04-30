package WeatherAnalysisApp.Models;

import WeatherAnalysisApp.Enums.Timezone;

import java.io.Serializable;

/**
 * The model for application settings
 */
public class ApplicationSettings implements Serializable {
    public boolean isAutoSave;
    public boolean isDarkTheme;
    public Timezone timezone;

    public ApplicationSettings(boolean isAutoSave, boolean isDarkTheme, Timezone timezone) {
        this.isAutoSave = isAutoSave;
        this.isDarkTheme = isDarkTheme;
        this.timezone = timezone;
    }

    // Setters
    public void setIsAutoSave(boolean value) { isAutoSave = value; }
    public void setIsDarkTheme(boolean value) { isDarkTheme = value; }
    public void setTimezone(Timezone value) { timezone = value; }
}
