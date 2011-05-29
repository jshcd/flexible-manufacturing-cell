package Element.Robot;

import Auxiliar.AutomatonState;
import Element.Piece.Piece;
import Automaton.Master.Master;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Robot2 implements Robot {
    private RobotMailBox _mailBox;
    private AutomatonState _state;
    private Piece _loadedPiece;

    public void pickAssembly() {
            throw new UnsupportedOperationException();
    }

    public void transportAssembly() {
            throw new UnsupportedOperationException();
    }

    public void transportWeldedAssembly() {
            throw new UnsupportedOperationException();
    }

    public RobotMailBox getMailBox() {
            throw new UnsupportedOperationException();
    }

    public void setMailBox(RobotMailBox mailBox) {
            throw new UnsupportedOperationException();
    }

    public void notifyAutomaton() {
            throw new UnsupportedOperationException();
    }

    public void runCommand(int command) {
            throw new UnsupportedOperationException();
    }

    public Piece getLoadedPiece() {
            throw new UnsupportedOperationException();
    }

    public void setLoadedPiece(Piece loadedPiece) {
            throw new UnsupportedOperationException();
    }

    public void startServer() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Robot2.port"));
            ServerSocket skServidor = new ServerSocket(port);
            Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Server listening at port {0}", port);
            while(true) {
                Socket skCliente = skServidor.accept();
                Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Information received");
                ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Received> {0}", Short.parseShort((String) in.readObject()));

                short a = (short) 0;
                out.writeObject(a);
                skCliente.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Robot2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Robot2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Robot2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}