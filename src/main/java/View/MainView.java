package View;

import Controllers.MainController;
import View.MainLayout.BottomLayout;
import View.MainLayout.CenterLayout;
import View.MainLayout.TopLayout;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

/**
 * The main view of the application
 * Extends from JFrame class
 */
public class MainView extends JFrame {
    public MainView() {
        /*
         * Set the global font
         * Segoe UI, Plain, 14 size
         */
        setGlobalFont(new Font("Segoe UI", Font.PLAIN, 14));

        setTitle("Weather Data Analysis Desktop App by Charles Henry M. Tinoy Jr.");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1050, 700);
        setMinimumSize(new Dimension(800, 580));
        setLocationRelativeTo(null);
        pack();

        // ========== Start of Components ==========

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        TopLayout topLayout = new TopLayout();
        CenterLayout centerLayout = new CenterLayout();
        BottomLayout bottomLayout = new BottomLayout();

        mainContent.add(topLayout);
        mainContent.add(centerLayout);
        mainContent.add(bottomLayout);

        new MainController(
                CenterLayout.cityListModel,
                CenterLayout.cityList,
                CenterLayout.cityDataTableModel,
                CenterLayout.cityDataTable
        );

        // ========== End of Components ==========

        add(mainContent);
    }

    /**
     * Sets a font for the entire application
     * @param font The font of the application
     */
    private void setGlobalFont(Font font) {
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
