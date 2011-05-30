/* Slave 2 code */
package Automaton.Slaves;

import Auxiliar.Constants;
import Element.Conveyor.ConveyorBelt;
import Element.Station.WeldingStation;
import Scada.DataBase.DBConnection;
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

public class Slave2 implements Slave {

    private SlaveMailBox _mailBox;
    private WeldingStation _weldingStation;
    private DBConnection _dbconnection;

    public void start() {
        throw new UnsupportedOperationException();
    }

    public void stop() {
        throw new UnsupportedOperationException();
    }

    public void runCommand(int command) {
        switch (command) {
            case Constants.SLAVE2_ROBOT2_REQUEST_WELDING:
                _weldingStation.weld();
                break;
            case Constants.SLAVE2_ROBOT2_REQUEST_QUALITY:
                _weldingStation.weld();
                break;

        }
    }

    public void startAssemblyBelt() {
        throw new UnsupportedOperationException();
    }

    public void stopAssemblyBelt() {
        throw new UnsupportedOperationException();
    }

    public void unloadAssembly() {
        throw new UnsupportedOperationException();
    }

    public void weld() {
        throw new UnsupportedOperationException();
    }

    public void weldingAvailable() {
        throw new UnsupportedOperationException();
    }

    public void reportToMaster() {
        throw new UnsupportedOperationException();
    }

    public void loadParameters() {
        throw new UnsupportedOperationException();
    }

    public void orderToRobot(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void startServer() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Slave2.port"));
            ServerSocket skServidor = new ServerSocket(port);
            Logger.getLogger(Slave2.class.getName()).log(Level.INFO, "Server listening at port {0}", port);
            while (true) {
                Socket skCliente = skServidor.accept();
                Logger.getLogger(Slave2.class.getName()).log(Level.INFO, "Information received");
                ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                Logger.getLogger(Slave2.class.getName()).log(Level.INFO, "Received> {0}", Short.parseShort((String) in.readObject()));

                short a = (short) 0;
                out.writeObject(a);
                skCliente.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Slave2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Slave2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Slave2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}