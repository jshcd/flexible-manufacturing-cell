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

public class MasterInputMailBox implements MailBox {

    private String _id;
    private ServerSocket _serverSocket;
    private Master _master;
    private static final Logger _logger = Logger.getLogger(MasterInputMailBox.class.toString());

    /**
     * Constructs a new <code>MasterMailBox</code>
     * @param m <code>Master</code> who created the mailbox
     */
    public MasterInputMailBox(Master m) {
        _id = "Master";
        _master = m;
        _logger.addHandler(m.getMonitor().getLog().getLogHandler());
    }

    /**
     * 
     */
    public void startConnection() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Master.port"));
            _serverSocket = new ServerSocket(port);
            _logger.log(Level.FINE, "MasterInputMailBox listening at port {0}", port);

        } catch (UnknownHostException ex) {
            _logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
    }

    public void endConnection() {
        _logger.log(Level.FINE, "Not supoorted yet.");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void acceptConnection() {
        _logger.log(Level.FINE, "Not supoorted yet.");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendCommand(MailboxData command) {
        _logger.log(Level.FINE, "Not supoorted yet.");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void receiveCommand() {
        _logger.log(Level.FINE, "Not supoorted yet.");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns the identifier of the <code>MasterMailBox</code>
     * @return Identifier of the <code>MasterMailBox</code>
     */
    public String getId() {
        return _id;
    }

    /**
     * Starts a server which attend petitions from the <code>SlaveOutputMailBox</code>
     * and response them with an <code>Ok</code> message
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
                            _logger.log(Level.INFO, "MasterInputMailBox received {0}", o);
                            if (o instanceof Command) {
                                if (((Command) o).getCommand() == Constants.COMMAND_SLAVE1_CONNECTED) {
                                    _master.setConnectionStatus(Constants.SLAVE1_ID, true);
                                } else if (((Command) o).getCommand() == Constants.COMMAND_SLAVE2_CONNECTED) {
                                    _master.setConnectionStatus(Constants.SLAVE2_ID, true);
                                } else if (((Command) o).getCommand() == Constants.COMMAND_SLAVE3_CONNECTED) {
                                    _master.setConnectionStatus(Constants.SLAVE3_ID, true);
                                }
                            } else if (o instanceof Slave1Data) {
                                _master.setCanvasStatus(Constants.SLAVE1_ID, (Slave1Data) o);
                                _master.setConnectionStatus(Constants.SLAVE1_ID, true);
                            } else if (o instanceof Slave2Data) {
                                _master.setCanvasStatus(Constants.SLAVE2_ID, (Slave2Data) o);
                                _master.setConnectionStatus(Constants.SLAVE2_ID, true);
                            } else if (o instanceof Slave3Data) {
                                _master.setCanvasStatus(Constants.SLAVE3_ID, (Slave3Data) o);
                                _master.setConnectionStatus(Constants.SLAVE3_ID, true);
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
                                    _master.setConnectionStatus(Constants.SLAVE1_ID, false);
                                } else if (o instanceof Slave2Data) {
                                    _master.setConnectionStatus(Constants.SLAVE2_ID, false);
                                } else if (o instanceof Slave3Data) {
                                    _master.setConnectionStatus(Constants.SLAVE3_ID, false);
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
