package WeatherAnalysisApp.Models;

import java.io.Serializable;

/**
 * The model for application settings
 */
public class ApplicationSettings implements Serializable {
    public boolean isAutoSave;
    public boolean isDarkTheme;

    public ApplicationSettings(boolean isAutoSave, boolean isDarkTheme) {
        this.isAutoSave = isAutoSave;
        this.isDarkTheme = isDarkTheme;
    }

    // Setters
    public void setIsAutoSave(boolean value) { isAutoSave = value; }
    public void setIsDarkTheme(boolean value) { isDarkTheme = value; }
}
