package RopewayGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundButton extends JButton {
    private boolean blinking = false;
    private Thread blinkThread;
    private boolean ledEnabled = false;
    private boolean buttonPressed = false;
    private Color buttonColor;
    private Color defaultColor;
    private Color pressedColor;

    public RoundButton(String label, Color color) {
        super(label);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setPreferredSize(new Dimension(100, 100)); // Button-Größe setzen
        defaultColor = color;
        pressedColor = defaultColor.darker();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonPressed = true;
                if(ledEnabled){
                    setButtonColor(pressedColor);
                }else{
                    setButtonColor(pressedColor.darker());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                buttonPressed = false;
                if (ledEnabled) {
                    setButtonColor(defaultColor); // Setzt die Farbe zurück
                }else{
                    setButtonColor(defaultColor.darker());
                }
            }
        });
    }

    // Methode zum Ändern der Button-Farbe
    public void setButtonColor(Color color) {
        this.buttonColor = color;
        repaint(); // Erzwingt Neuzeichnen
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(20,20,20));
        g2.fillOval(0,0,getWidth(),getHeight());

        // Erstelle einen radialen Farbverlauf von innen nach außen
        RadialGradientPaint gradient = new RadialGradientPaint(
                new Point(getWidth() / 2, getHeight() / 2),
                (getWidth()-10) / 2,
                new float[]{0f, 1f},
                new Color[]{buttonColor.brighter(), buttonColor}
        );

        g2.setPaint(gradient);
        if(buttonPressed){

            g2.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
        }else{
            g2.fillOval(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(60, 60, 60));
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

    public void setLedEnabled(boolean ledEnabled) {
        this.ledEnabled = ledEnabled;
        if(ledEnabled){
            if(buttonPressed){
                setButtonColor(pressedColor);
            }else{
                setButtonColor(defaultColor);
            }
        }else{
            if(buttonPressed){
                setButtonColor(pressedColor.darker());
            }else{
                setButtonColor(pressedColor);
            }
        }
    }

    public boolean isLedEnabled() {
        return ledEnabled;
    }

    public boolean isBlinking() {
        return blinking;
    }

    public void turnBlinkOn(int delay) {
        if (blinking) return;
        blinking = true;
        blinkThread = new Thread(() -> {
            try {
                while (blinking) {
                    setLedEnabled(true);
                    Thread.sleep(delay);
                    setLedEnabled(false);
                    Thread.sleep(delay);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        blinkThread.start();
    }

    public void turnBlinkOff() {
        blinking = false;
        if (blinkThread != null) {
            blinkThread.interrupt();
        }
        setButtonColor(defaultColor);
    }
}
