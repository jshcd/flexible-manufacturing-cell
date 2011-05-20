package Automaton.Master;

import Auxiliar.MailBox;
import java.io.IOException;
import java.net.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;import java.util.logging.Level;
import java.util.logging.Logger;

public class MasterMailBox implements MailBox {

    public MasterMailBox _unnamed_MasterMailBox_;
    private Socket _requestSocket;
    private ObjectOutputStream _out;
    private ObjectInputStream _in;
    private short _message;
    
    MasterMailBox(){
        
    }

    public void startConnection(MailBox destiny) {
        try {
            _requestSocket = new Socket("localhost", 2004);
        } catch (UnknownHostException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void endConnection(MailBox destiny) {
            throw new UnsupportedOperationException();
    }

    public void operation() {
            throw new UnsupportedOperationException();
    }

    public void acceptConnection() {
        try {
            _out = new ObjectOutputStream(_requestSocket.getOutputStream());
            _out.flush();
            _in = new ObjectInputStream(_requestSocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendCommand(short command) {
        try {
            _out.writeObject(command);
            _out.flush();
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveCommand() {
        try {
            _message = Short.parseShort((String) _in.readObject());
        } catch (IOException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MasterMailBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}