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

    public SlaveMailBox (int id) {
        _id = "Slave"+id;
    }

    public void startConnection() {
        try {
            Properties prop = new Properties();
            InputStream is = null;
            is=new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            _port = Integer.parseInt(prop.getProperty(_id + ".port"));
            _address = prop.getProperty(_id + ".ip");

            _providerSocket = new ServerSocket(_port, 10);
        } catch (IOException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void endConnection(MailBox destiny) {
        throw new UnsupportedOperationException();
    }

    public void acceptConnection() {
        try {
            _connection = _providerSocket.accept();
            _out = new ObjectOutputStream(_connection.getOutputStream());
            _out.flush();
            _in = new ObjectInputStream(_connection.getInputStream());
            System.out.println("Fin del acceptConnection del " + _id);
        } catch (IOException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void sendCommand(short command) {
        try {
            _out.writeObject(Short.toString(command));
            _out.flush();
            System.out.println(_id + "> " + command);
        } catch (IOException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveCommand() {
        try {
            _message = Short.parseShort((String) _in.readObject());
            System.out.println("Master> " + _message);
        } catch (IOException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startConnection(MailBox destiny) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getId() {
        return _id;
    }
}