package Element.Robot;

import Auxiliar.MailBox;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RobotMailBox implements MailBox {
    private Robot _robot;
    private String _id;
    private ServerSocket _providerSocket;
    private Socket _connection = null;
    private ObjectOutputStream _out;
    private ObjectInputStream _in;
    private short _message;
    private int _port;
    private String _address;

    public RobotMailBox(int id){
        _id = "Robot"+id;
    }

    public void startConnection() {
        try {
            Properties prop = new Properties();
            InputStream is = null;
            is=new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            _port = Integer.parseInt(prop.getProperty(_id + ".port"));
            _address = prop.getProperty(_id + ".ip");

            _providerSocket = new ServerSocket(_port, 10);
            
        } catch (IOException ex) {
            Logger.getLogger(RobotMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void endConnection(MailBox destiny) {
            throw new UnsupportedOperationException();
    }

    public void acceptConnection() {
        try {
            _connection = _providerSocket.accept();
            _out = new ObjectOutputStream(_connection.getOutputStream());
            _out.flush();
            _in = new ObjectInputStream(_connection.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(RobotMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendCommand(short command) {
        try {
            _out.writeObject(command);
            _out.flush();
            System.out.println(_id + "> " + command);
        } catch (IOException ex) {
            Logger.getLogger(RobotMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveCommand() {
        try {
            _message = Short.parseShort((String) _in.readObject());
            System.out.println(_id + ":Master> " + _message);
        } catch (IOException ex) {
            Logger.getLogger(RobotMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RobotMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startConnection(MailBox destiny) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getId() {
        return _id;
    }
}