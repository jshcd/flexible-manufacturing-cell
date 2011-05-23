package Automaton.Slaves;

import Auxiliar.MailBox;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SlaveMailBox implements MailBox {

    private String _id;
    private ServerSocket _providerSocket;
    private Socket _connection = null;
    private ObjectOutputStream _out;
    private ObjectInputStream _in;
    private short _message;

    public SlaveMailBox (int id) {
        _id = "Slave"+id;
    }

    public void startConnection(MailBox destiny) {
        try {
            _providerSocket = new ServerSocket(2004, 10);
            _out = new ObjectOutputStream(_connection.getOutputStream());
            _out.flush();
            _in = new ObjectInputStream(_connection.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void endConnection(MailBox destiny) {
        throw new UnsupportedOperationException();
    }

    public void acceptConnection() {
        try {
            _connection = _providerSocket.accept();
        } catch (IOException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void sendCommand(short command) {
        try {
            _out.writeObject(command);
            _out.flush();
            System.out.println(_id + "> " + command);
        } catch (IOException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveCommand() {
        try {
            _message = Short.parseShort((String) _in.readObject());
            System.out.println("Master> " + _message);
        } catch (IOException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SlaveMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}