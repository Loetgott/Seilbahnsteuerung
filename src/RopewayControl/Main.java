package RopewayControl;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import RopewayGUI.GUI;

public class Main {
    static SerialPort port;
    public static void main(String[] args) {
        GUI gui = new GUI();

        // Nachrichten vom Arduino empfangen
        //StringBuilder messageBuilder = new StringBuilder();
        //while (true) {
        //    if (port.bytesAvailable() > 0) {
        //        byte[] buffer = new byte[1]; // Ein Byte lesen, um Zeichen zu dekodieren
        //        int bytesRead = 0;
        //        try {
        //            bytesRead = port.getInputStream().read(buffer);
        //        } catch (IOException e) {
        //            throw new RuntimeException(e);
        //        }
        //        if (bytesRead > 0) {
        //            char receivedChar = (char) buffer[0];
        //            if (receivedChar == '\n') { // Nachricht ist vollständig
        //                String receivedMessage = messageBuilder.toString();
        //                System.out.println("Empfangen: " + receivedMessage);
        //                messageBuilder.setLength(0); // Nachricht löschen
        //            } else {
        //                messageBuilder.append(receivedChar);
        //            }
        //        }
        //    }
        //}
    }

    public static SerialPort[] getPorts(){
        return SerialPort.getCommPorts();
    }

    public static void setOutputPort(String portName){
        port = SerialPort.getCommPort(portName.split("\\(")[1].split("\\)")[0]);
        port.setComPortParameters(9600, 8, 1, 0); // Baudrate, Datenbits, Stoppbits, Parität
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 1000); // Timeout auf 1 Sekunde setzen

        if (port.openPort()) {
            System.out.println("Port geöffnet!");
            GUI.setButtonsEnabled(true);
        } else {
            System.err.println("Fehler beim Öffnen des Ports.");
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (port.isOpen()) {
                port.closePort();
                System.out.println("Port geschlossen.");
                GUI.setButtonsEnabled(false);
            }
        }));
    }

    public static void sendData(String message){
        try {
            port.getOutputStream().write(message.getBytes());
            port.getOutputStream().flush();
            System.out.println("Nachricht gesendet: " + message);
        } catch (IOException e) {
            System.err.println("Fehler beim Senden der Nachricht.");
            e.printStackTrace();
        }
    }
}
