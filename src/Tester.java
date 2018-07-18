
import data.Packet;
import data.PacketHandler;
import javax.swing.JOptionPane;
import server.UDPServer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ceyha
 */
public class Tester {

    UDPServer udpserver;

    public Tester() {

        boolean configCorrect = true;
        if (AnaPencere.textDeviceIp.getText().length() != 0) {
            AnaPencere.labelDeviceIP.setFont(new java.awt.Font("Tahoma", 0, 11));
        } else {
            AnaPencere.labelDeviceIP.setFont(new java.awt.Font("Tahoma", 1, 11));
            configCorrect = false;
        }
        if (AnaPencere.textDevicePort.getText().length() != 0 && configCorrect) {
            AnaPencere.labelDevicePort.setFont(new java.awt.Font("Tahoma", 0, 11));
        } else {
            AnaPencere.labelDevicePort.setFont(new java.awt.Font("Tahoma", 1, 11));
            configCorrect = false;
        }
        if (AnaPencere.textLocalPort.getText().length() != 0 && configCorrect) {
            AnaPencere.labelServerPort.setFont(new java.awt.Font("Tahoma", 0, 11));
        } else {
            AnaPencere.labelServerPort.setFont(new java.awt.Font("Tahoma", 1, 11));
            configCorrect = false;
        }
        if (AnaPencere.textSerialNumber.getText().length() != 0 && configCorrect) {
            AnaPencere.labelSerialNumber.setFont(new java.awt.Font("Tahoma", 0, 11));
        } else {
            AnaPencere.labelSerialNumber.setFont(new java.awt.Font("Tahoma", 1, 11));
            configCorrect = false;
        }
        if (AnaPencere.textServerIp.getText().length() != 0 && configCorrect) {
            AnaPencere.labelServerIP.setFont(new java.awt.Font("Tahoma", 0, 11));
        } else {
            AnaPencere.labelServerIP.setFont(new java.awt.Font("Tahoma", 1, 11));
            configCorrect = false;
        }
        if (AnaPencere.textTestCardID.getText().length() != 0 && configCorrect) {
            AnaPencere.labelTestCardID.setFont(new java.awt.Font("Tahoma", 0, 11));
        } else {
            AnaPencere.labelTestCardID.setFont(new java.awt.Font("Tahoma", 1, 11));
            configCorrect = false;
        }
        if (configCorrect) {
            AnaPencere.jTextArea1.append("Test başladı!\r\n");
            startTest();
        } else {
            JOptionPane.showMessageDialog(null, "Cihaz bilgilerini eksiksiz giriniz!");
        }
    }

    private void startTest() {
        PacketHandler packet = new PacketHandler() {
            @Override
            public void process(Packet packet) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        udpserver = new UDPServer(Integer.parseInt(AnaPencere.textLocalPort.getText()));
        udpserver.receive(packet);
        if (AnaPencere.jPanel1.isShowing()) {
            System.out.println("0901 seçildi");
        }
        if (AnaPencere.jPanel2.isShowing()) {
            System.out.println("EAC-1746 seçildi");
        }
    }

    public void stopTest() {
        udpserver.stop();
    }
}
