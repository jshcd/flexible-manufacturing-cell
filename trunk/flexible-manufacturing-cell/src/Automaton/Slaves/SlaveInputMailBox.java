package Automaton.Slaves;

import Automaton.Master.Data.Ok;
import Auxiliar.Command;
import Auxiliar.Constants;
import Auxiliar.MailBox;
import Auxiliar.MailboxData;
import Scada.DataBase.MasterConfigurationData;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier
 */
public class SlaveInputMailBox implements MailBox {

    private String _id;
    private ServerSocket _serverSocket;
    private Slave _slave;
    private final static Logger _logger = Logger.getLogger(SlaveInputMailBox.class.getName());

    /**
     * Constructor of the class
     * @param Identifier of the <code>Slave</code> who owns the <code>SlaveInputMailBox</code>
     * @param <code>Slave</code> who owns the <code>SlaveInputMailBox</code>
     */
    public SlaveInputMailBox(int id, Slave slave){
        _id = "Slave" + id;
        _slave = slave;
    }

    /**
     * Starts a connection at the corresponding port
     */
    public void startConnection() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream(Constants.MAILBOXES_PROPERTIES_PATH);
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty(_id + ".port"));
            _serverSocket = new ServerSocket(port);
            _logger.log(Level.INFO, "{0} SlaveInputMailBox listening at port {1}", new Object [] {_id, port});
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
     * Returns the id of the <code>SlaveInputMailBox</code>
     * @return Id of the <code>SlaveInputMailBox</code>
     */
    public String getId() {
        return _id;
    }

    /**
     *  Server starts listening for connections from <code>MasterOutputMailBox</code>
     */
    public void startServer() {
        startConnection();
        while (true) {
            try {
                final Socket skCliente = _serverSocket.accept();
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                            ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                            
                            Object o = in.readObject();
                            _logger.log(Level.FINE, "Slave {0} SlaveInputMailBox received {1}", new Object[] {_id, o});
                            if(o instanceof Command){
                                switch(((Command)o).getCommand()){
                                    case Constants.START_SLAVE1:
                                        _slave.start();
                                        _logger.log(Level.INFO, "Slave 1 starts");
                                        break;
                                    case Constants.START_SLAVE2:
                                        _slave.start();
                                        _logger.log(Level.INFO, "Slave 2 starts");
                                        break;
                                    case Constants.START_SLAVE3:
                                        _slave.start();
                                        _logger.log(Level.INFO, "Slave 3 starts");
                                        break;
                                    case Constants.EMERGENCY_STOP_ORDER:
                                        _slave.emergencyStop();
                                        _logger.log(Level.INFO, "Emergency Stop");
                                        break;
                                    case Constants.NORMAL_STOP_ORDER:
                                        _slave.normalStop();
                                        _logger.log(Level.INFO, "Normal Stop");
                                }
                            }else if(o instanceof MasterConfigurationData){
                                _slave.storeInitialConfiguration((MasterConfigurationData)o);
                            }
                            Ok ok = new Ok();
                            out.writeObject(ok);
                            skCliente.close();
                        } catch (ClassNotFoundException ex) {
                            _logger.log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            _logger.log(Level.SEVERE, null, ex);
                        }
                    }
                });
                t.start();
            } catch (IOException ex) {
                _logger.log(Level.SEVERE, null, ex);
            }
        }
    }

}
