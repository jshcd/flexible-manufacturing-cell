/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Automaton.Master;

import Auxiliar.MailBox;
import Auxiliar.MailboxData;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier
 */
public class MasterOutputMailBox implements MailBox {

    private String _id;
    private int _port;
    private String _address;
    private Socket _requestSocket;
    private int _destination; //0: Robot2, 1: Slave1, 2: Slave2 and 3: Slave3
    private ObjectOutputStream _out;
    private ObjectInputStream _in;

    public MasterOutputMailBox(){
        _id = "Master";

    }

    public void sendInformation(MailboxData command, int destination){
        setDestination(destination);
        startConnection();
        acceptConnection();
        sendCommand(command);
        receiveCommand();
    }

    public void startConnection() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            String port = "";
            String ip = "";
            switch(_destination){
                case 1:
                    port = "Slave1.port";
                    ip = "Slave1.ip";
                    break;
                case 2:
                    port = "Slave2.port";
                    ip = "Slave2.ip";
                    break;
                case 3:
                    port = "Slave3.port";
                    ip = "Slave3.ip";
            }
            _port = Integer.parseInt(prop.getProperty(port));
            _address = prop.getProperty(ip);
            _requestSocket = new Socket(_address, _port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(MasterOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MasterOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void endConnection() {
        try {
            _requestSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MasterOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void acceptConnection() {
        try {
            _out = new ObjectOutputStream(_requestSocket.getOutputStream());
            _out.flush();
        } catch (IOException ex) {
            Logger.getLogger(MasterOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendCommand(MailboxData command) {
        try {
            _out.writeObject(command);
            _out.flush();
        } catch (IOException ex) {
            Logger.getLogger(MasterOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveCommand() {
        try {
            _in = new ObjectInputStream(_requestSocket.getInputStream());
            System.out.println("He recibido un objeto de la clase " + _in.readObject().getClass());
        } catch (IOException ex) {
            Logger.getLogger(MasterOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MasterOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getId() {
        return _id;
    }
    
    public void setDestination(int destination){
        _destination = destination;
    }

}
