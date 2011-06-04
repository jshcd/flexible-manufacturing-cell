package Element.Robot;

import Automaton.Slaves.Data.MailboxData;
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

public class RobotMailBox implements MailBox {
    private String _id;
    private ObjectOutputStream _out;
    private ObjectInputStream _in;
    private int _port;
    private String _address;
    private Socket _requestSocket;

    /**
     * Constructs a new <code>RobotMailBox</code> with the indicated id
     * @param id Identifier of the <code>RobotMailBox</code>
     */
    public RobotMailBox(int id){
        _id = "Robot"+id;
    }

    /**
     *
     */
    public void startConnection() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            _port = Integer.parseInt(prop.getProperty("Scada.port"));
            _address = prop.getProperty("Scada.ip");

            _requestSocket = new Socket(_address, _port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(RobotMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RobotMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param destiny
     */
    public void endConnection() {
        try {
            _requestSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(RobotMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void acceptConnection() {
        try {
            _out = new ObjectOutputStream(_requestSocket.getOutputStream());
            _out.flush();
        } catch (IOException ex) {
            Logger.getLogger(RobotMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendCommand(MailboxData command) {
         try {
            _out.writeObject(command);
            _out.flush();
        } catch (IOException ex) {
            Logger.getLogger(RobotMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveCommand() {
        try {
            _in = new ObjectInputStream(_requestSocket.getInputStream());
            System.out.println("He recibido un objeto de la clase " + _in.readObject().getClass());
        } catch (IOException ex) {
            Logger.getLogger(RobotMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RobotMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns the identifier of the <code>RobotMailBox</code>
     * @return Identifier of the <code>RobotMailBox</code>
     */
    public String getId() {
        return _id;
    }
}