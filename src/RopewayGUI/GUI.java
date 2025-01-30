package RopewayGUI;

import RopewayControl.Main;
import com.fazecast.jSerialComm.SerialPort;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI {
    public GUI() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            UIManager.put("Menu.selectionBackground", new Color(83, 83, 83));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Seilbahnsteuerung");
        frame.setSize(new Dimension(1500, 800));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu portMenu = new JMenu("Port");
        updateComPorts(portMenu);
        menuBar.add(portMenu);
        JMenu updateMenu = new JMenu("Update");
        updateMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateComPorts(portMenu);
            }
        });
        menuBar.add(updateMenu);

        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    public static void updateComPorts(JMenu portMenu){
        portMenu.removeAll();
        SerialPort[] ports = Main.getPorts();
        for(int i = 0; i < ports.length; i++){
            if (!ports[i].getDescriptivePortName().split("\\(")[0].trim().equals("Standardmäßgige Seriell-über-Bluetooth-Verbindung")) {
                JMenuItem portMenuItem = new JMenuItem(ports[i].getDescriptivePortName());
                portMenuItem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        Main.setOutputPort(portMenuItem.getText());
                    }
                });
                portMenu.add(portMenuItem);
            }
        }
    }

    public static void setButtonsEnabled(boolean enabled){

    }
}
