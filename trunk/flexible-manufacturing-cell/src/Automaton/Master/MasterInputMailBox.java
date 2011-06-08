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
     protected Logger _logger = Logger.getLogger(MasterInputMailBox.class.toString());
    /**
     * Constructs a new <code>MasterMailBox</code> with the indicated id
     * @param id Identifier of the <code>MasterMailBox</code>
     */
    public MasterInputMailBox(Master m){
        _id = "Master";
        _master = m;
      //  _logger.addHandler(m.getMonitor().getLog().getLogHandler());
       
 }

    public void startConnection() {
       try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Master.port"));
            _serverSocket = new ServerSocket(port);
            _logger.log(Level.FINE, "Server listening at port {0}", port);

        } catch (UnknownHostException ex) {
           _logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
           _logger.log(Level.SEVERE, null, ex);
        }
    }

    public void endConnection() {
        /*
        try {
            _serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
    }

    public void acceptConnection() {
        /*
        try {
            Socket skCliente = _serverSocket.accept();
            _out = new ObjectOutputStream(skCliente.getOutputStream());
            _in = new ObjectInputStream(skCliente.getInputStream());
            _out.flush();
        } catch (IOException ex) {
            Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
    }

    public void sendCommand(MailboxData command) {
        /*
        try {
            _out.writeObject(command);
            _out.flush();
        } catch (IOException ex) {
            Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
    }

    public void receiveCommand() {
        /*
        try {
            _message = (MailboxData) _in.readObject();
            System.out.println("El objeto que ha llegado es del tipo " + _message.getClass());
        } catch (IOException ex) {
            Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
    }

    /**
     * Returns the identifier of the <code>MasterMailBox</code>
     * @return Identifier of the <code>MasterMailBox</code>
     */
    public String getId() {
        return _id;
    }

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
                            Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.FINE, "Received> {0}", o);
                            _logger.log(Level.FINE, "Received> {0}", o);
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
                                Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.SEVERE, null, ex1);
                            } finally {
                                try {
                                    out.close();
                                } catch (IOException ex1) {
                                    Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.SEVERE, null, ex1);
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
            _logger.getLogger(MasterInputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
