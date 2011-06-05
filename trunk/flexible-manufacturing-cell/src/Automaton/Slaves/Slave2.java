/* Slave 2 code */
package Automaton.Slaves;

import Automaton.Slaves.Data.Slave2Data;
import Automaton.Slaves.Slave;
import Automaton.Slaves.Slave;
import Automaton.Slaves.SlaveOutputMailBox;
import Automaton.Slaves.SlaveOutputMailBox;
import Auxiliar.Command;
import Auxiliar.Constants;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.Piece.Piece.PieceType;
import Element.Station.QualityControlStation;
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
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Slave2 implements Slave {

    private SlaveOutputMailBox _mailBox;
    private WeldingStation _weldingStation;
    private QualityControlStation _qualityStation;
    private DBConnection _dbconnection;
    private Sensor _sensor6;
    private Sensor _sensor7;
    private Slave2Data _statusData;

    public static void main(String args[]) {
        Slave2 s2 = new Slave2();
        s2.initialize();
    }

    public Slave2() {
        Logger.getLogger(Slave2.class.getName()).log(Level.INFO, "Slave 2 created");
    }

    public WeldingStation getWeldingStation() {
        return _weldingStation;
    }

    public Sensor getSensor6() {
        return _sensor6;
    }

    public Sensor getSensor7() {
        return _sensor7;
    }

    public void updateStatusData() {
        _statusData = new Slave2Data();
        _statusData.setSensor6Status(_sensor6.isActivated());
        _statusData.setSensor7Status(_sensor7.isActivated());
        _statusData.setQualityStationPieces(_qualityStation.getPieces());
        _statusData.setQualityStationRunning(_qualityStation.isMoving());
        _statusData.setWeldingStationPieces(_weldingStation.getPieces());
        _statusData.setWeldingStationRunning(_weldingStation.isMoving());
    }

    public final void initialize() {
        _dbconnection = new DBConnection();
        _dbconnection.connect();

        try {

            // TODO: Estos parametros no deben cargase asi, pero lo dejamos de momento para hacer pruebas
            int sensor_range = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SENSOR_RANGE).getInt("value");

            _weldingStation = new WeldingStation(5);
            _weldingStation.setWeldingTime(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_WELDING_STATION_TIME).getInt("time"));
            _weldingStation.setProcess(this);
            _qualityStation = new QualityControlStation(6);
            _qualityStation.setQualityTime(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_QUALITY_STATION_TIME).getInt("time"));
            _qualityStation.setProcess(this);

            _sensor6 = new Sensor();
            _sensor6.setSensorId(6);
            _sensor6.setAssociatedContainer(_weldingStation);
            _sensor6.setProcess(this);
            _sensor6.setRange(sensor_range);
            _sensor6.setPositionInBelt(0);

            _sensor7 = new Sensor();
            _sensor7.setSensorId(7);
            _sensor7.setAssociatedContainer(_qualityStation);
            _sensor7.setProcess(this);
            _sensor7.setRange(sensor_range);
            _sensor7.setPositionInBelt(0);

            _weldingStation.addSensor(_sensor6);
            _qualityStation.addSensor(_sensor7);

            // We start the belts
            new Thread(_weldingStation).start();
            new Thread(_qualityStation).start();

            //We start the sensors
            new Thread(_sensor6).start();
            new Thread(_sensor7).start();

        } catch (SQLException ex) {
            System.err.println("Error at loading database at Slave 1");
            Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void start() {
        _weldingStation.startContainer();
        _qualityStation.startContainer();
        reportToMaster(Constants.SLAVE_TWO_STARTING);
    }

    public void stop() {
        _weldingStation.stopContainer();
        _qualityStation.stopContainer();
        reportToMaster(Constants.SLAVE_TWO_STOPPING);
    }

    public void runCommand(int command) {
        Piece p;
        switch (command) {
            case Constants.START_SLAVE2:
                start();
                break;
            case Constants.ROBOT2_SLAVE2_REQUEST_WELDING:
                _weldingStation.weld();
                break;
            case Constants.ROBOT2_SLAVE2_REQUEST_QUALITY:
                _qualityStation.checkQuality();
                break;
            case Constants.ROBOT2_SLAVE2_PLACES_ASSEMBLY:
                p = new Piece();
                p.setType(PieceType.assembly);
                _weldingStation.addPiece(p);
                break;
            case Constants.ROBOT2_SLAVE2_PICKS_WELDED_ASSEMBLY:
                _weldingStation.removeLastPiece();
                break;
            case Constants.ROBOT2_SLAVE2_PLACES_WELDED_ASSEMBLY:
                p = new Piece();
                p.setType(PieceType.weldedAssembly);
                _qualityStation.addPiece(p);
                break;
            case Constants.ROBOT2_SLAVE2_PICKS_CHECKED_WELDED_ASSEMBLY:
                _qualityStation.removeLastPiece();
                break;
        }
    }

    public void orderToRobot(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void reportToMaster(int orderNumber) {
        Command command = new Command(orderNumber);
        _mailBox.startConnection();
        _mailBox.acceptConnection();
        _mailBox.sendCommand(command);
        _mailBox.receiveCommand();
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