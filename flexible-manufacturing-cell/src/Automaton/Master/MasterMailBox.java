package Automaton.Master;

import Auxiliar.MailBox;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MasterMailBox implements MailBox {

    public MasterMailBox _unnamed_MasterMailBox_;
    private Socket _requestSocket;
    private ObjectOutputStream _out;
    private ObjectInputStream _in;
    private short _message;
    private String _id;
    
    public MasterMailBox(){
        _id = "Master";
    }

    public void startConnection(MailBox destiny) {
        try {
            Properties prop = new Properties();
            InputStream is = null;
            is=new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty(destiny.getId() + ".port"));
            String address = prop.getProperty(destiny.getId() + ".ip");

            _requestSocket = new Socket(address, port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
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
            System.out.println("Inicio del acceptConnection del " + _id);
            _out = new ObjectOutputStream(_requestSocket.getOutputStream());
            _out.flush();
            System.out.println("Fin del acceptConnection del " + _id);
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendCommand(short command) {
        try {
            _out.writeObject(Short.toString(command));
            _out.flush();
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveCommand() {
        try {
            _in = new ObjectInputStream(_requestSocket.getInputStream());
            _message = Short.parseShort((String) _in.readObject());
            System.out.println(_message);
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getId() {
        return _id;
    }
}