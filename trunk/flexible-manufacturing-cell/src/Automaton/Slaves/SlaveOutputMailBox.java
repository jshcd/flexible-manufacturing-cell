package Automaton.Slaves;

import Auxiliar.Constants;
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
    private final static Logger _logger = Logger.getLogger(SlaveOutputMailBox.class.getName());

    /**
     * Constructs a new <code>SlaveMailBox</code> with the indicated id
     * @param id Identifier of the <code>SlaveMailBox</code>
     */
    public SlaveOutputMailBox(int id) {
        _id = "Slave" + id;
    }

    /**
     * Starts the connection with the <code>MasterInputMailBox</code>
     */
    public void startConnection() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream(Constants.MAILBOXES_PROPERTIES_PATH);
            prop.load(is);
            _port = Integer.parseInt(prop.getProperty("Master.port"));
            _address = prop.getProperty("Master.ip");
            _requestSocket = new Socket(_address, _port);
        } catch (UnknownHostException ex) {
            _logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
            _logger.log(Level.INFO, null, "MasterInputMailBox not found: retrying in 5 secs");
            try {
                Thread.sleep(5000);
                startConnection();
            } catch (InterruptedException ex1) {
                _logger.log(Level.SEVERE, null, ex1);
            }
        }
    }

    /**
     * Closes the connection with the <code>MasterInputMailBox</code>
     */
    public void endConnection() {
        try {
            _requestSocket.close();
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prepares the connection to be able to write through it
     */
    public void acceptConnection() {
        try {
            _out = new ObjectOutputStream(_requestSocket.getOutputStream());
            synchronized (_out) {
                _out.flush();
            }
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sends a <code>MailboxData</code> command to the
     * <code>MasterInputMailBox</code>
     * @param command that the Slave wants to send to the Master
     */
    public void sendCommand(MailboxData command) throws IOException {
        synchronized (_out) {
            _out.writeObject(command);
            _out.flush();
        }
    }

    /**
     * Receive response from <code>MasterInputMailBox</code>
     */
    public void receiveCommand() {
        try {
            _in = new ObjectInputStream(_requestSocket.getInputStream());
            _logger.log(Level.FINE, "Received response from MasterInputMailBox> {0}", _in.readObject());
        } catch (IOException ex) {
            Logger.getLogger(SlaveOutputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            _logger.log(Level.SEVERE, null, ex);
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