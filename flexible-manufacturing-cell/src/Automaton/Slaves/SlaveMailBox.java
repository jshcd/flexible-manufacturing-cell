package Automaton.Slaves;

import Auxiliar.MailBox;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SlaveMailBox implements MailBox {

    private String _id;
    private ServerSocket _providerSocket;
    private Socket _connection = null;
    private ObjectOutputStream _out;
    private ObjectInputStream _in;
    private short _message;
    private int _port;
    private String _address;
    private Socket _requestSocket;

    public SlaveMailBox (int id) {
        _id = "Slave"+id;
    }

    public void startConnection() {
        try {
            Properties prop = new Properties();
            InputStream is = null;
            is=new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = 5000;//Integer.parseInt(prop.getProperty(destiny.getId() + ".port"));
            String address = "localhost";//prop.getProperty(destiny.getId() + ".ip");

            _requestSocket = new Socket(address, port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void endConnection(MailBox destiny) {
        throw new UnsupportedOperationException();
    }

    public void acceptConnection() {
        try {
            _out = new ObjectOutputStream(_requestSocket.getOutputStream());
            _out.flush();
        } catch (IOException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendCommand(short command) {
        try {
            _out.writeObject(Short.toString(command));
            _out.flush();
        } catch (IOException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveCommand() {
         try {
            _in = new ObjectInputStream(_requestSocket.getInputStream());
            System.out.println(_in.readObject());
        } catch (IOException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getId() {
        return _id;
    }

    public void startConnection(MailBox destiny) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}