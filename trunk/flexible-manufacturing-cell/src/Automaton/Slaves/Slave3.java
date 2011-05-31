/* Slave 3 code */
package Automaton.Slaves;

import Auxiliar.Constants;
import Element.Station.QualityControlStation;
import Element.Conveyor.ConveyorBelt;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.Piece.Piece.PieceType;
import Scada.DataBase.DBConnection;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Slave3 implements Slave {

    private SlaveMailBox _mailBox;
    private DBConnection _dbconnection;
    private ConveyorBelt _acceptedBelt;
    private ConveyorBelt _rejectedBelt;
    private Sensor _sensor8;
    private Sensor _sensor9;
    private Sensor _sensor10;
    private Sensor _sensor11;

    public Slave3() {
        Logger.getLogger(Slave3.class.getName()).log(Level.INFO, "Slave 3 created");
        initialize();
    }

    public final void initialize() {
        _dbconnection = new DBConnection();
        _dbconnection.connect();

        try {

            // TODO: Estos parametros no deben cargase asi, pero lo dejamos de momento para hacer pruebas
            int sensor_range = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SENSOR_RANGE).getInt("value");

            int acceptedSpeed = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE3_BELT1_CONFIGURATION).getInt("length");
            int acceptedLength = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE3_BELT1_CONFIGURATION).getInt("speed");
            int reyectedSpeed = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE3_BELT2_CONFIGURATION).getInt("length");
            int reyectedLength = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE3_BELT2_CONFIGURATION).getInt("speed");

            _acceptedBelt = new ConveyorBelt(7, acceptedSpeed, acceptedLength);
            _rejectedBelt = new QualityControlStation(8, reyectedSpeed, reyectedLength);

            _sensor8 = new Sensor();
            _sensor8.setSensorId(8);
            _sensor8.setAssociatedContainer(_rejectedBelt);
            _sensor8.setProcess(this);
            _sensor8.setRange(sensor_range);
            _sensor8.setPositionInBelt(0);

            _sensor9 = new Sensor();
            _sensor9.setSensorId(9);
            _sensor9.setAssociatedContainer(_acceptedBelt);
            _sensor9.setProcess(this);
            _sensor9.setRange(sensor_range);
            _sensor9.setPositionInBelt(acceptedLength - sensor_range);

            _sensor10 = new Sensor();
            _sensor10.setSensorId(10);
            _sensor10.setAssociatedContainer(_rejectedBelt);
            _sensor10.setProcess(this);
            _sensor10.setRange(sensor_range);
            _sensor10.setPositionInBelt(0);

            _sensor11 = new Sensor();
            _sensor11.setSensorId(11);
            _sensor11.setAssociatedContainer(_acceptedBelt);
            _sensor11.setProcess(this);
            _sensor11.setRange(sensor_range);
            _sensor11.setPositionInBelt(reyectedLength - sensor_range);

            // We start the belts
            _acceptedBelt.run();
            _acceptedBelt.addSensor(_sensor9);
            _acceptedBelt.addSensor(_sensor11);
            _rejectedBelt.run();
            _rejectedBelt.addSensor(_sensor8);
            _rejectedBelt.addSensor(_sensor10);

            //We start the sensors
            _sensor8.run();
            _sensor9.run();
            _sensor10.run();
            _sensor11.run();


        } catch (SQLException ex) {
            System.err.println("Error at loading database at Slave 1");
            Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void start() {
        _acceptedBelt.startContainer();
        _rejectedBelt.startContainer();
        reportToMaster(Constants.SLAVE_THREE_STARTING);
    }

    public void stop() {
        _acceptedBelt.stopContainer();
        _rejectedBelt.stopContainer();
        reportToMaster(Constants.SLAVE_THREE_STOPPING);
    }

    public void runCommand(int command) {
        Piece p;
        switch (command) {
            case Constants.ROBOT2_SLAVE3_PLACES_WELDED_OK:
                p = new Piece();
                p.setType(PieceType.weldedAssembly);
                _acceptedBelt.addPiece(p);
                break;
            case Constants.ROBOT2_SLAVE3_PLACES_WELDED_NOT_OK:
                p = new Piece();
                p.setType(PieceType.weldedAssembly);
                _rejectedBelt.addPiece(p);
                break;
        }
    }

    public void reportToMaster(int i) {
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
            int port = Integer.parseInt(prop.getProperty("Slave3.port"));
            ServerSocket skServidor = new ServerSocket(port);
            Logger.getLogger(Slave3.class.getName()).log(Level.INFO, "Server listening at port {0}", port);
            while (true) {
                Socket skCliente = skServidor.accept();
                Logger.getLogger(Slave3.class.getName()).log(Level.INFO, "Information received");
                ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                Logger.getLogger(Slave3.class.getName()).log(Level.INFO, "Received> {0}", Short.parseShort((String) in.readObject()));

                short a = (short) 0;
                out.writeObject(a);
                skCliente.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Slave3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Slave3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Slave3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}