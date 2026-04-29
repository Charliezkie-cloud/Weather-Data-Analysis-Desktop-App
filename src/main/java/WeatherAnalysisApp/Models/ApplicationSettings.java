package WeatherAnalysisApp.Models;

import java.io.Serializable;

/**
 * The model for application settings
 */
public class ApplicationSettings implements Serializable {
    public boolean isAutoSave;

    public ApplicationSettings(boolean isAutoSave) {
        this.isAutoSave = isAutoSave;
    }

    // Setters
    public void setAutoSave(boolean value) { isAutoSave = value; }
}
