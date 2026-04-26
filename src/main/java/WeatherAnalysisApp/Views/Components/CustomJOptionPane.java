package WeatherAnalysisApp.Views.Components;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JOption Pane
 * extends from <code>JOptionPane</code> class
 */
public class CustomJOptionPane extends JOptionPane {
    /**
     * A custom method for showing error messages
     * @param parentComponent The parent component
     * @param message The error message
     */
    public static void showErrorDialog(Component parentComponent, String message) {
        showMessageDialog(parentComponent, message, "Error", ERROR_MESSAGE);
    }

    /**
     * A custom method for showing success messages
     * @param parentComponent The parent component
     * @param message The success message
     */
    public static void showSuccessDialog(Component parentComponent, String message) {
        showMessageDialog(parentComponent, message, "Success", INFORMATION_MESSAGE);
    }
}
