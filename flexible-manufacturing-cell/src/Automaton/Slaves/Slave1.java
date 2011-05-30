/* Slave 1 code */
package Automaton.Slaves;

import Element.Robot.Robot1;
import Auxiliar.*;
import Element.Conveyor.ConveyorBelt;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.Piece.Piece.PieceType;
import Scada.DataBase.DBConnection;
import Element.Station.AssemblyStation;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Slave1 implements Slave {

    private SlaveMailBox _mailBox;
    private ConveyorBelt _gearBelt;
    private ConveyorBelt _axisBelt;
    private ConveyorBelt _weldingBelt;
    private Robot1 _robot;
    private DBConnection _dbconnection;
    private AssemblyStation _assemblyStation;
    private Sensor _sensor1;
    private Sensor _sensor2;
    private Sensor _sensor3;
    private boolean _finishing;

    /*
     * Arg[0] must be the IP Address of the server
     */
    public static void main(String[] args) {
        Slave1 s = new Slave1();
    }

    public Slave1() {
        initialize();
        mainLoop();
    }

    public final void initialize() {
        _dbconnection = new DBConnection();
        _dbconnection.connect();

        try {
            int gearSpeed =_dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE1_BELT1_CONFIGURATION).getInt("length");
            int gearLength = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE1_BELT1_CONFIGURATION).getInt("speed");
            int axisSpeed = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE1_BELT2_CONFIGURATION).getInt("length");
            int axisLength = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE1_BELT2_CONFIGURATION).getInt("speed");
            int sensor_range = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SENSOR_RANGE).getInt("value");
            _gearBelt = new ConveyorBelt(1, gearSpeed, gearLength);
            _axisBelt = new ConveyorBelt(2, axisSpeed, axisLength);

            _robot = new Robot1();
            // TODO: estos parametros están mal
            _robot.setTrasportTime1(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION).getInt("transport_element1"));
            _robot.setTransportTime2(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION).getInt("transport_element2"));
            _robot.setTransportTime3(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION).getInt("transport_element3"));

            _assemblyStation = new AssemblyStation(3, 0, 0);

            _sensor1 = new Sensor();
            _sensor1.setSensorId(1);
            _sensor1.setAssociatedContainer(_gearBelt);
            _sensor1.setProcess(this);
            _sensor1.setRange(sensor_range);
            _sensor1.setPositionInBelt(gearLength - sensor_range);

            _sensor2 = new Sensor();
            _sensor2.setSensorId(2);
            _sensor2.setAssociatedContainer(_axisBelt);
            _sensor2.setProcess(this);
            _sensor2.setRange(sensor_range);
            _sensor2.setPositionInBelt(axisLength - sensor_range);

            _sensor3 = new Sensor();
            _sensor3.setSensorId(3);
            _sensor3.setAssociatedContainer(_assemblyStation);
            _sensor3.setProcess(this);
            _sensor3.setRange(sensor_range);
            _sensor3.setPositionInBelt(0);

            // We start the belts
            _gearBelt.start();
            _gearBelt.addSensor(_sensor1);
            _axisBelt.start();
            _axisBelt.addSensor(_sensor2);
            _assemblyStation.start();
            _assemblyStation.addSensor(_sensor3);

            //We start the sensors
            _sensor1.start();
            _sensor2.start();
            _sensor3.start();

            _finishing = false;
        } catch (SQLException ex) {
            System.err.println("Error at loading database at Slave 1");
            Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Sensor getSensor1() {
        return _sensor1;
    }

    public Sensor getSensor2() {
        return _sensor2;
    }

    public Sensor getSensor3() {
        return _sensor3;
    }
    
    /*
     * Starts the system
     */
    public void start() {
        _finishing = false;
        _gearBelt.startBelt();
        _axisBelt.startBelt();
        orderToRobot(Constants.START_ORDER);
        mainLoop();
    }
    /*
     * Emergency stop
     */

    public void stop() {
        _finishing = true;
        _gearBelt.stopBelt();
        _axisBelt.stopBelt();
        orderToRobot(Constants.EMERGENCY_STOP_ORDER);
    }

    public void runCommand(int command) {
        Piece p;
        switch (command) {
            case Constants.START_ORDER:
                start();
                break;
            case Constants.EMERGENCY_STOP_ORDER:
                stop();
                break;
            case Constants.NORMAL_STOP_ORDER:
                _finishing = true;
                break;
            case Constants.SLAVE1_ROBOT1_PICKS_GEAR:
                _gearBelt.removeLastPiece();
                break;
            case Constants.SLAVE1_ROBOT1_PICKS_AXIS:
                _axisBelt.removeLastPiece();
                break;
            case Constants.SLAVE1_ROBOT1_PLACES_GEAR:
                p = new Piece();
                p.setType(PieceType.gear);
                _assemblyStation.addPiece(p);
                break;
            case Constants.SLAVE1_ROBOT1_PLACES_AXIS:
                p = new Piece();
                p.setType(PieceType.axis);
                _assemblyStation.addPiece(p);
                break;
            case Constants.SLAVE1_ROBOT1_PICKS_ASSEMBLY:
                _assemblyStation.removeLastPiece();
                break;
            case Constants.SLAVE1_ROBOT1_REQUEST_ASSEMBLY:
                _assemblyStation.assemble();
                break;
            case Constants.SLAVE1_ROBOT1_PLACES_ASSEMBLY:
                p = new Piece();
                p.setType(PieceType.gear);
                _weldingBelt.addPiece(p);
                break;
        }
    }

    public void orderToRobot(int orderNumber) {
        throw new UnsupportedOperationException();
    }

    public void reportToMaster(int orderNumber) {
        InputStream is = null;
        try {
            //TO-DO envia al master la información de un sensor
            Socket requestSocket = new Socket();
            ObjectOutputStream out;
            ObjectInputStream in;
            Properties prop = new Properties();
            is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Master.port"));
            String address = prop.getProperty("Master.ip");
            requestSocket = new Socket(address, port);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            out.writeObject(orderNumber);
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            try {
                System.out.println(in.readObject());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
            }
            requestSocket.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void mainLoop() {

        int pieceSize;
        try {

            pieceSize = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_PIECE_SIZE).getInt("value");

            // if we didn't receive the order to finish, we keep adding pieces
            while (!_finishing) {

                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
                }

                boolean roomInGearBelt = true;
                boolean roomInAxisBelt = true;

                //we check if there is room in the gears belt for adding a new piece
                for (Piece p : _gearBelt.getPieces()) {
                    if (p.getPosition() < pieceSize * 1.1) {
                        roomInGearBelt = false;
                        break;
                    }
                }

                // if so we add the piece
                if (roomInGearBelt) {
                    Piece p = new Piece();
                    p.setPosition(0);
                    p.setType(PieceType.gear);
                    _gearBelt.addPiece(p);
                }

                //we check if there is room in the axis belt for adding a new piece
                for (Piece p : _axisBelt.getPieces()) {
                    if (p.getPosition() < pieceSize * 1.1) {
                        roomInAxisBelt = false;
                        break;
                    }
                }

                // if so we add the piece
                if (roomInAxisBelt) {
                    Piece p = new Piece();
                    p.setPosition(0);
                    p.setType(PieceType.axis);
                    _axisBelt.addPiece(p);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void startServer() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Slave1.port"));
            ServerSocket skServidor = new ServerSocket(port);
            Logger.getLogger(Slave1.class.getName()).log(Level.INFO, "Server listening at port {0}", port);
            while(true) {
                Socket skCliente = skServidor.accept();
                Logger.getLogger(Slave1.class.getName()).log(Level.INFO, "Information received");
                ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                Logger.getLogger(Slave1.class.getName()).log(Level.INFO, "Received> {0}", Short.parseShort((String) in.readObject()));

                short a = (short) 0;
                out.writeObject(a);
                skCliente.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}