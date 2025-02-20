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

        JPanel buttonPanel = new JPanel(new BorderLayout());

        JPanel leftButtonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        RoundButton powerButton = new RoundButton("Anlage ein/ aus", Color.WHITE, 43);
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftButtonsPanel.add(powerButton, gbc);
        RoundButton dummyButton1 = new RoundButton("",  Color.WHITE , 0);
        gbc.gridx = 1;
        leftButtonsPanel.add(dummyButton1, gbc);
        RoundButton richtungVorwaertsButton = new RoundButton("vorwärts", Color.WHITE, 45);
        gbc.gridx = 0;
        gbc.gridy = 1;
        leftButtonsPanel.add(richtungVorwaertsButton, gbc);
        RoundButton richtungRueckwaertsButton = new RoundButton("rückwärts", Color.WHITE, 47);
        gbc.gridx = 1;
        leftButtonsPanel.add(richtungRueckwaertsButton, gbc);
        RoundButton beschickenButton = new RoundButton("Beschicken", Color.WHITE, 41);
        gbc.gridx = 0;
        gbc.gridy = 2;
        leftButtonsPanel.add(beschickenButton, gbc);
        RoundButton umlaufButton = new RoundButton("Umlauf", Color.WHITE, 51);
        gbc.gridx = 1;
        leftButtonsPanel.add(umlaufButton, gbc);

        buttonPanel.add(leftButtonsPanel,BorderLayout.WEST);

        JPanel haltPanel = new JPanel(new BorderLayout());

        NothaltButton nothaltButton = new NothaltButton("nothalt",52);
        nothaltButton.setPreferredSize(new Dimension(200,200));
        haltPanel.add(nothaltButton, BorderLayout.WEST);

        buttonPanel.add(haltPanel, BorderLayout.EAST);

        frame.add(buttonPanel);

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
