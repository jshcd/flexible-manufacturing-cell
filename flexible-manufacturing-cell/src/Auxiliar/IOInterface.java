package Auxiliar;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that defines the behavior of an IO Interface
 * @author Echoplex
 */
public class IOInterface implements Runnable {

    /**
     * Process that uses the IOInterface
     */
    IOProcess process;
    /**
     * Port for  communication
     */
    int portS;
    /**
     * Port for  communication
     */
    int portR;
    /**
     * Socket
     */
    DatagramSocket socketSender;
    InetAddress address;

    /**
     * Constructor of the class, defines default ports
     */
    public IOInterface() {
        portS = 7476;
        portR = 5555;
    }

    /**
     * Binds to a port
     */
    public void bind() {
        try {
            socketSender = new DatagramSocket(portS);
        } catch (SocketException ex) {
            Logger.getLogger(IOInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Listening requests and send them to the process
     */
    public void run() {

        MulticastSocket socketReceive;
        try {
            socketReceive = new MulticastSocket(portR);
            socketReceive.setTimeToLive(255);
            Properties prop = new Properties();
            InputStream is = new FileInputStream(Constants.MAILBOXES_PROPERTIES_PATH);
            prop.load(is);
            String multicastAddress = prop.getProperty("multicastAddress");
            address = InetAddress.getByName(multicastAddress);

            socketReceive.joinGroup(InetAddress.getByName("230.0.0.1"));

            socketReceive.setTimeToLive(1);

            DatagramPacket packet;

            while (true) {
                byte[] buf = new byte[2];
                packet = new DatagramPacket(buf, buf.length);
                socketReceive.receive(packet);
                ByteBuffer bb = ByteBuffer.wrap(buf);

                short s = bb.getShort();
                if (process != null) {
                    process.runCommand(s);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(IOInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sends a command to the network using broadcast
     * @param command Command to be sent
     */
    public void send(short command) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeShort(command);//2 bytes 
            dos.flush();
            byte[] buf = baos.toByteArray();

            // send it
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, portR);

            socketSender.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(IOInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets the IOProcess
     * @param iop IOProcess
     */
    public void setProcess(IOProcess iop) {
        process = iop;
    }

    /**
     * Sets the port for communication
     * @param i Port number
     */
    public void setPortLag(int i) {
        portS = portS + i;
    }
}