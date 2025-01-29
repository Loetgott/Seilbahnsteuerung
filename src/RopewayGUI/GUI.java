package RopewayGUI;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;

public class GUI {
    public GUI() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Seilbahnsteuerung");
        frame.setSize(new Dimension(1500, 800));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Datei");
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }
}
