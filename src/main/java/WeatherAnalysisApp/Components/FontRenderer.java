package WeatherAnalysisApp.Components;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class FontRenderer {
    /**
     * Sets a font for the entire application
     * @param font The font of the application
     */
    public static void setGlobalFont(Font font) {
        FontUIResource fontUIResource = new FontUIResource(font);
        Enumeration<Object> keys = UIManager.getDefaults().keys();

        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);

            if (value instanceof FontUIResource)
                UIManager.put(key, fontUIResource);
        }
    }
}
