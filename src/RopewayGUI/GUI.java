package RopewayGUI;

import RopewayControl.Main;
import com.fazecast.jSerialComm.SerialPort;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    private final JFrame frame;
    private final JMenu portMenu;

    public GUI() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        UIManager.put("Menu.selectionBackground", new Color(83, 83, 83));

        frame = new JFrame("Seilbahnsteuerung");
        frame.setSize(1500, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        portMenu = new JMenu("Port");
        updateComPorts();

        portMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                updateComPorts();
            }
        });

        menuBar.add(portMenu);
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    public static void setButtonsEnabled(boolean b) {

    }

    private void updateComPorts() {
        portMenu.removeAll(); // Alte Menüeinträge löschen
        SerialPort[] ports = Main.getPorts();

        for (SerialPort port : ports) {
            String portName = port.getDescriptivePortName().split("\\(")[0].trim();

            if (!portName.equals("Standardmäßgige Seriell-über-Bluetooth-Verbindung")) {
                JMenuItem portMenuItem = new JMenuItem(port.getDescriptivePortName());

                portMenuItem.addActionListener(e -> Main.setOutputPort(port.getDescriptivePortName()));

                portMenu.add(portMenuItem);
            }
        }
        portMenu.revalidate();
        portMenu.repaint();
    }
}
