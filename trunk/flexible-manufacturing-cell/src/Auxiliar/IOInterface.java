/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auxiliar;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Portatil
 */
public class IOInterface implements Runnable {

    IOProcess process;
    int portS;
    int portR;
    DatagramSocket socketSender;

    public IOInterface() {
        portS = 7446;
        portR = 6446;
    }

    public void bind() {
        try {
//            System.out.println("BINDING: " + portS + " " + Thread.currentThread().getName());
            socketSender = new DatagramSocket(portS);
        } catch (SocketException ex) {
            Logger.getLogger(IOInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {

        MulticastSocket socketReceive;
        try {
            socketReceive = new MulticastSocket(portR);

            InetAddress address = InetAddress.getByName("230.0.0.1");
            socketReceive.joinGroup(address);

            DatagramPacket packet;

            while (true) {

                byte[] buf = new byte[2];
                packet = new DatagramPacket(buf, buf.length);
                socketReceive.receive(packet);

                ByteBuffer bb = ByteBuffer.wrap(buf);

                short s = bb.getShort();
//                System.out.println(s);
                process.runCommand(s);
            }

        } catch (IOException ex) {
            Logger.getLogger(IOInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void send(short command) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeShort(command);//2 bytes 
            dos.flush();
            byte[] buf = baos.toByteArray();


//            System.out.println("Sent: " + command);

            // send it
            InetAddress group = InetAddress.getByName("230.0.0.1");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, portR);
            socketSender.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProcess(IOProcess iop) {
        process = iop;
    }

    public void setPortLag(int i) {
        portS = portS + i;
    }
}
