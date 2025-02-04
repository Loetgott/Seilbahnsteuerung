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

        JPanel panel = new JPanel();
        panel.setBackground(new Color(10, 10, 10));
        RoundButton roundButton1 = new RoundButton("test", Color.WHITE);
        roundButton1.setLedEnabled(false);

        RoundButton roundButton2 = new RoundButton("test", Color.WHITE);
        roundButton2.setLedEnabled(false);
        roundButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(roundButton1.isBlinking()){
                    roundButton1.turnBlinkOff();
                }else{
                    roundButton1.turnBlinkOn(300);
                }
            }
        });

        panel.add(roundButton1);
        panel.add(roundButton2);

        frame.add(panel);

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
