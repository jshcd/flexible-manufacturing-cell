package Automaton.Master;

import Automaton.Master.Data.Ok;
import Automaton.Slaves.Data.Slave1Data;
import Automaton.Slaves.Data.Slave2Data;
import Automaton.Slaves.Data.Slave3Data;
import Auxiliar.Command;
import Auxiliar.Constants;
import Auxiliar.MailboxData;
import Auxiliar.MailBox;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that represents the mailbox where the master receives information
 * @author Echoplex
 */
public class MasterInputMailBox implements MailBox {

    /**
     * Mailbox identifier
     */
    private String _id;
    /**
     * Server socket for communication
     */
    private ServerSocket _serverSocket;
    /**
     * The master that uses the mailbox
     */
    private Master _master;
    /**
     * Logger
     */
    private static final Logger _logger = Logger.getLogger(MasterInputMailBox.class.toString());

    /**
     * Constructs a new MasterMailBox
     * @param m Master who created the mailbox
     */
    public MasterInputMailBox(Master m) {
        _id = "Master";
        _master = m;
        _logger.addHandler(m.getMonitor().getLog().getLogHandler());
    }

    /**
     * Starts a connection at the corresponding port
     */
    public void startConnection() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream(Constants.MAILBOXES_PROPERTIES_PATH);
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Master.port"));
            _serverSocket = new ServerSocket(port);
            _logger.log(Level.INFO, "MasterInputMailBox listening at port " + port);
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
        _logger.log(Level.FINE, "Not supoorted yet.");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Accepts the connection with the SlaveInputMailBox
     */
    public void acceptConnection() {
        _logger.log(Level.FINE, "Not supoorted yet.");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Sends a MailboxData command to the correspondent MasterOutputMailBox
     * @param command which is going to be sent
     */
    public void sendCommand(MailboxData command) {
        _logger.log(Level.FINE, "Not supoorted yet.");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Receives the response from the correspondent MasterOutputMailBox
     */
    public void receiveCommand() {
        _logger.log(Level.FINE, "Not supoorted yet.");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns the identifier of the MasterMailBox
     * @return Identifier of the MasterMailBox
     */
    public String getId() {
        return _id;
    }

    /**
     * Starts a server which attend petitions from the SlaveOutputMailBox
     * and response them with an Ok message
     */
    public void startServer() {
        try {
            startConnection();
            while (true) {
                final Socket skCliente = _serverSocket.accept();
                Thread t = new Thread(new Runnable() {

                    public void run() {
                        Object o = null;
                        try {
                            ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                            ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                            o = in.readObject();
                            _logger.log(Level.FINE, "MasterInputMailBox received {0}", o);
                            if (o instanceof Command) {
                                if (((Command) o).getCommand() == Constants.COMMAND_SLAVE1_CONNECTED) {
                                    _master.setConnectionStatus(Constants.SLAVE1_ID, true);
                                    _logger.log(Level.INFO, "Slave 1 is connected");
                                } else if (((Command) o).getCommand() == Constants.COMMAND_SLAVE2_CONNECTED) {
                                    _master.setConnectionStatus(Constants.SLAVE2_ID, true);
                                    _logger.log(Level.INFO, "Slave 2 is connected");
                                } else if (((Command) o).getCommand() == Constants.COMMAND_SLAVE3_CONNECTED) {
                                    _master.setConnectionStatus(Constants.SLAVE3_ID, true);
                                    _logger.log(Level.INFO, "Slave 3 is connected");
                                }
                            } else if (o instanceof Slave1Data) {
                                _master.setCanvasStatus(Constants.SLAVE1_ID, (Slave1Data) o);
                            } else if (o instanceof Slave2Data) {
                                _master.setCanvasStatus(Constants.SLAVE2_ID, (Slave2Data) o);
                            } else if (o instanceof Slave3Data) {
                                _master.setCanvasStatus(Constants.SLAVE3_ID, (Slave3Data) o);
                            }
                            Ok ok = new Ok();
                            out.writeObject(ok);
                            skCliente.close();
                        } catch (EOFException ex) {
                            ObjectOutputStream out = null;
                            try {
                                out = new ObjectOutputStream(skCliente.getOutputStream());
                                Ok ok = new Ok();
                                out.writeObject(ok);
                                skCliente.close();
                            } catch (SocketException ex1) {
                                if (o instanceof Slave1Data) {
                                    _master.setCanvasStatus(Constants.SLAVE1_ID, (Slave1Data) o);
                                } else if (o instanceof Slave2Data) {
                                    _master.setCanvasStatus(Constants.SLAVE2_ID, (Slave2Data) o);
                                } else if (o instanceof Slave3Data) {
                                    _master.setCanvasStatus(Constants.SLAVE3_ID, (Slave3Data) o);
                                }
                            } catch (IOException ex1) {
                                _logger.log(Level.SEVERE, null, ex1);
                            } finally {
                                try {
                                    out.close();
                                } catch (IOException ex1) {
                                    _logger.log(Level.FINE, null, ex1);
                                }
                            }
                        } catch (ClassNotFoundException ex) {
                            _logger.log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            _logger.log(Level.SEVERE, null, ex);
                        }
                    }
                });
                t.start();
            }
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
    }
}
