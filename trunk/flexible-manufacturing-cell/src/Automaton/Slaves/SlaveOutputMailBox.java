package Automaton.Slaves;

import Auxiliar.MailboxData;
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

public class SlaveOutputMailBox implements MailBox {

    private String _id;
    private ObjectOutputStream _out;
    private ObjectInputStream _in;
    private int _port;
    private String _address;
    private Socket _requestSocket;

    /**
     * Constructs a new <code>SlaveMailBox</code> with the indicated id
     * @param id Identifier of the <code>SlaveMailBox</code>
     */
    public SlaveOutputMailBox(int id) {
        _id = "Slave" + id;
    }

    public void startConnection() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            _port = Integer.parseInt(prop.getProperty("Master.port"));
            _address = prop.getProperty("Master.ip");
            //_requestSocket = new Socket();
            _requestSocket = new Socket(_address, _port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(SlaveOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SlaveOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void endConnection() {
        try {
            _requestSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(SlaveOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void acceptConnection() {
        try {
            _out = new ObjectOutputStream(_requestSocket.getOutputStream());
            synchronized (_out) {
                _out.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(SlaveOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendCommand(MailboxData command) {
        try {
            synchronized (_out) {
                _out.writeObject(command);
                _out.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(SlaveOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveCommand() {
        try {
            _in = new ObjectInputStream(_requestSocket.getInputStream());
            Logger.getLogger(SlaveOutputMailBox.class.getName()).log(Level.FINE, "Received response> Object of the class {0}", _in.readObject().getClass());
        } catch (IOException ex) {
            Logger.getLogger(SlaveOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SlaveOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns the identifier of the <code>SlaveMailBox</code>
     * @return Identifier of the <code>SlaveMailBox</code>
     */
    public String getId() {
        return _id;
    }
}