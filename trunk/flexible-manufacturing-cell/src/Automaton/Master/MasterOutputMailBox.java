package Automaton.Master;

import Auxiliar.Constants;
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
 * Class that represents the mailbox where the master sends information
 * @author Echoplex
 */
public class MasterOutputMailBox implements MailBox {

    private String _id;
    private int _port;
    private String _address;
    private Socket _requestSocket;
    private int _destination;
    private ObjectOutputStream _out;
    private ObjectInputStream _in;
    private final static Logger _logger = Logger.getLogger(MasterOutputMailBox.class.getName());

    /**
     * Constructor of the class
     */
    public MasterOutputMailBox() {
        _id = "Master";
    }

    /**
     * Sends the indicated <code>MailboxData</code> command to a the indicated
     * <code>SlaveInputMailBox</code>
     *
     * @param <code>MailboxData</code> command which is going to be send
     * @param destination <code>SlaveInputMailBox</code>
     * 1 in case of Slave1
     * 2 in case of Slave2
     * 3 in case of Slave3
     */
    public void sendInformation(MailboxData command, int destination) {
        setDestination(destination);
        startConnection();
        acceptConnection();
        sendCommand(command);
        receiveCommand();
    }

    /**
     * Starts a connection to a concrete <code>SlaveInputMailBox</code>
     */
    public void startConnection() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream(Constants.MAILBOXES_PROPERTIES_PATH);
            prop.load(is);
            String port = "";
            String ip = "";
            switch (_destination) {
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
            if (port.compareTo("") != 0) {
                _port = Integer.parseInt(prop.getProperty(port));
                _address = prop.getProperty(ip);
                _requestSocket = new Socket(_address, _port);
            } else {
                _logger.log(Level.INFO, null, "The connection could not be made");
            }
        } catch (UnknownHostException ex) {
            _logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Closes the connection with the SlaveInputMailBox
     */
    public void endConnection() {
        try {
            _requestSocket.close();
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Accepts the connection with the SlaveInputMailBox
     */
    public void acceptConnection() {
        try {
            _out = new ObjectOutputStream(_requestSocket.getOutputStream());
            _out.flush();
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sends a MailboxData command to the correspondent MasterOutputMailBox
     * @param command which is going to be sent
     */
    public void sendCommand(MailboxData command) {
        try {
            _out.writeObject(command);
            _out.flush();
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Receives the response from the correspondent MasterOutputMailBox
     */
    public void receiveCommand() {
        try {
            _in = new ObjectInputStream(_requestSocket.getInputStream());
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns the <code>MasterOutputMailBox</code> identifier
     * @return <code>MasterOutputMailBox</code> identifier
     */
    public String getId() {
        return _id;
    }

    /**
     * Returns identifier of the destination SlaveInputMailBox
     * @return SlaveInputMailBox identifier
     */
    public int getDestination() {
        return _destination;
    }

    /**
     * Sets the value of the destination SlaveInputMailBox
     * @param destination SlaveInputMailBox
     */
    public void setDestination(int destination) {
        _destination = destination;
    }
}
