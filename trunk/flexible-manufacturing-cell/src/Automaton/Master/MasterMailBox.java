package Automaton.Master;

import Automaton.Slaves.Data.SlaveData;
import Auxiliar.MailBox;
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

public class MasterMailBox implements MailBox {
    private String _id;
    private ObjectOutputStream _out;
    private ObjectInputStream _in;
    private SlaveData _message;
    private Socket _requestSocket;

    /**
     * Constructs a new <code>MasterMailBox</code> with the indicated id
     * @param id Identifier of the <code>MasterMailBox</code>
     */
    public MasterMailBox(){
        _id = "Master";
    }

    public void startConnection() {
       try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Scada.port"));
            String address = prop.getProperty("Scada.ip");

            _requestSocket = new Socket(address, port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void endConnection() {
        try {
            _requestSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void acceptConnection() {
        try {
            _out = new ObjectOutputStream(_requestSocket.getOutputStream());
            _out.flush();
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendCommand(SlaveData command) {
        try {
            _out.writeObject(command);
            _out.flush();
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveCommand() {
        try {
            _in = new ObjectInputStream(_requestSocket.getInputStream());
            _message = (SlaveData) _in.readObject();
            System.out.println("El objeto que ha llegado es del tipo " + _message.getClass());
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns the identifier of the <code>MasterMailBox</code>
     * @return Identifier of the <code>MasterMailBox</code>
     */
    public String getId() {
        return _id;
    }
}