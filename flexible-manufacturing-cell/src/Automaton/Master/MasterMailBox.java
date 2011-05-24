package Automaton.Master;

import Auxiliar.MailBox;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MasterMailBox implements MailBox {

    public MasterMailBox _unnamed_MasterMailBox_;
    private ObjectOutputStream _out;
    private ObjectInputStream _in;
    private short _message;
    private String _id;
    private int _port;
    //private String _address;
    private ServerSocket _providerSocket;
    private Socket _connection;
    
    public MasterMailBox(){
        _id = "Master";
    }

    public void startConnection() {
        try {
            Properties prop = new Properties();
            InputStream is = null;
            is=new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            _port = Integer.parseInt(prop.getProperty(_id + ".port"));
            //_address = prop.getProperty(_id + ".ip");

            _providerSocket = new ServerSocket(_port, 10);
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void endConnection(MailBox destiny) {
            throw new UnsupportedOperationException();
    }

    public void operation() {
            throw new UnsupportedOperationException();
    }

    public void acceptConnection() {
        try {
            _connection = _providerSocket.accept();
            _out = new ObjectOutputStream(_connection.getOutputStream());
            _out.flush();
            _in = new ObjectInputStream(_connection.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendCommand(short command) {
       try {
            _out.writeObject(Short.toString(command));
            _out.flush();
            System.out.println("Sending command to " + _id + "> " + command);
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveCommand() {
        try {
            _message = Short.parseShort((String) _in.readObject());
            System.out.println("Receive command> " + _message);
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getId() {
        return _id;
    }

    public void startConnection(MailBox destiny) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}