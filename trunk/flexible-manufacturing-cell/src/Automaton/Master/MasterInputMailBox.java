package Automaton.Master;

import Automaton.Master.Data.Ok;
import Automaton.Slaves.Data.Slave1Data;
import Automaton.Slaves.Data.Slave2Data;
import Automaton.Slaves.Data.Slave3Data;
import Auxiliar.Command;
import Auxiliar.Constants;
import Auxiliar.MailboxData;
import Auxiliar.MailBox;
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

public class MasterInputMailBox implements MailBox {
    private String _id;
    private ServerSocket _serverSocket;
    private Master _master;

    /**
     * Constructs a new <code>MasterMailBox</code> with the indicated id
     * @param id Identifier of the <code>MasterMailBox</code>
     */
    public MasterInputMailBox(Master m){
        _id = "Master";
        _master = m;
    }

    public void startConnection() {
       try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Master.port"));
            _serverSocket = new ServerSocket(port);
            Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.INFO, "Server listening at port {0}", port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.SEVERE, null, ex);
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
                            Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.INFO, "Received> {0}", o);
                            
                            if(o instanceof Command){
                                if(((Command)o).getCommand() == Constants.COMMAND_SLAVE1_CONNECTED){
                                    _master.setConnectionStatus(Constants.SLAVE1_ID, true);
                                }else if(((Command)o).getCommand() == Constants.COMMAND_SLAVE2_CONNECTED){
                                    _master.setConnectionStatus(Constants.SLAVE2_ID, true);
                                }else if(((Command)o).getCommand() == Constants.COMMAND_SLAVE3_CONNECTED){
                                    _master.setConnectionStatus(Constants.SLAVE3_ID, true);
                                }
                            }else if(o instanceof Slave1Data){
                                _master.setCanvasStatus(Constants.SLAVE1_ID, (Slave1Data)o);
                            }else if(o instanceof Slave2Data){
                                _master.setCanvasStatus(Constants.SLAVE2_ID, (Slave2Data)o);
                            }else if(o instanceof Slave3Data){
                                _master.setCanvasStatus(Constants.SLAVE3_ID, (Slave3Data)o);
                            }
                            
                            Ok ok = new Ok();
                            out.writeObject(ok);
                            skCliente.close();
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                t.start();
            } catch (IOException ex) {
                Logger.getLogger(MasterInputMailBox.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}