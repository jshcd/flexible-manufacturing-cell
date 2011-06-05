/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Element.Robot;

import Auxiliar.IODevice;
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
public class RobotInputIO implements IODevice {

    private String _id;
    private ServerSocket _serverSocket;

    public RobotInputIO(int id){
        _id = "Robot" + id;
    }

    public void startConnection() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//IODevices.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty(_id + ".port"));
            _serverSocket = new ServerSocket(port);
            Logger.getLogger(RobotInputIO.class.getName()).log(Level.INFO, "Server listening at port {0}", port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(RobotInputIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RobotInputIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void endConnection() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void acceptConnection() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendCommand(short command) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void receiveCommand() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

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
                            Logger.getLogger(RobotInputIO.class.getName()).log(Level.INFO, "Received> {0}", in.readObject());
                            short ok = 0;
                            out.writeObject(ok);
                            skCliente.close();
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(RobotInputIO.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(RobotInputIO.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                t.start();
            } catch (IOException ex) {
                Logger.getLogger(RobotInputIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
