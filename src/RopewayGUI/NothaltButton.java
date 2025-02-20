package RopewayGUI;

import RopewayControl.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NothaltButton extends JButton {
    private final int buttonNumber;
    private boolean buttonPressed = false;

    public NothaltButton(String label, int number) {
        super(label);
        buttonNumber = number;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setPreferredSize(new Dimension(100, 100)); // Button-Größe setzen

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonPressed = true;
                Main.sendData(buttonNumber + ":true");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                buttonPressed = false;
                Main.sendData(buttonNumber + ":false");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(255, 255, 0));
        g2.fillOval(0,0,getWidth(),getHeight());

        if(buttonPressed){
            g2.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
        } else {
            g2.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(214, 214, 0));
        g2.setStroke(new BasicStroke(2)); // Rand dicker machen
        g2.drawOval(1, 1, getWidth() - 3, getHeight() - 3);
    }

    @Override
    public boolean contains(int x, int y) {
        int radius = getWidth() / 2;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(radius, 2);
    }
}