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
    protected Robot1 _robot;
    private DBConnection _dbconnection;
    private AssemblyStation _assemblyStation;
    private Sensor _sensor1;
    private Sensor _sensor2;
    private Sensor _sensor3;
    private Sensor _sensor4;
    private Sensor _sensor5;
    private boolean _finishing;

    public Slave1() {
        Logger.getLogger(Slave1.class.getName()).log(Level.INFO, "Slave 1 created");
        initialize();
    }

    public final void initialize() {
        _dbconnection = new DBConnection();
        _dbconnection.connect();

        _finishing = false;

        try {

            // TODO: Estos parametros no deben cargase asi, pero lo dejamos de momento para hacer pruebas
            int gearSpeed = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE1_BELT1_CONFIGURATION).getInt("length");
            int gearLength = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE1_BELT1_CONFIGURATION).getInt("speed");
            int axisSpeed = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE1_BELT2_CONFIGURATION).getInt("length");
            int axisLength = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE1_BELT2_CONFIGURATION).getInt("speed");
            int weldingSpeed = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE1_BELT3_CONFIGURATION).getInt("length");
            int weldingLength = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE1_BELT3_CONFIGURATION).getInt("speed");
            int sensor_range = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SENSOR_RANGE).getInt("value");

            _gearBelt = new ConveyorBelt(1, gearSpeed, gearLength);
            _axisBelt = new ConveyorBelt(2, axisSpeed, axisLength);

            _assemblyStation = new AssemblyStation(3, 0, 0);
            _assemblyStation.setAssemblyTime(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_ASSEMBLY_STATION_TIME).getInt("time"));

            _weldingBelt = new ConveyorBelt(4, weldingSpeed, weldingLength);

            _robot = new Robot1();
            _robot.setTrasportTime1(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR1).getInt("time"));
            _robot.setTransportTime2(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR2).getInt("time"));
            _robot.setTransportTime3(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR3).getInt("time"));

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

            _sensor4 = new Sensor();
            _sensor4.setSensorId(4);
            _sensor4.setAssociatedContainer(_weldingBelt);
            _sensor4.setProcess(this);
            _sensor4.setRange(sensor_range);
            _sensor4.setPositionInBelt(0);

            _sensor5 = new Sensor();
            _sensor5.setSensorId(4);
            _sensor5.setAssociatedContainer(_weldingBelt);
            _sensor5.setProcess(this);
            _sensor5.setRange(sensor_range);
            _sensor5.setPositionInBelt(weldingLength - sensor_range);

            _gearBelt.addSensor(_sensor1);
            _axisBelt.addSensor(_sensor2);
            _assemblyStation.addSensor(_sensor3);
            _weldingBelt.addSensor(_sensor4);

            // We start the belts
            Thread gearBelt = new Thread(_gearBelt);
            gearBelt.start();
            Thread axisBelt = new Thread(_axisBelt);
            axisBelt.start();
            Thread assemblyStation = new Thread(_assemblyStation);
            assemblyStation.start();
            Thread weldingBelt = new Thread(_weldingBelt);
            weldingBelt.start();


            //We start the sensors
            Thread sensor1 = new Thread(_sensor1);
            sensor1.start();
            Thread sensor2 = new Thread(_sensor2);
            sensor2.start();
            Thread sensor3 = new Thread(_sensor4);
            sensor3.start();
            Thread sensor4 = new Thread(_sensor4);
            sensor4.start();


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
        _assemblyStation.startBelt();
        _weldingBelt.startBelt();
        orderToRobot(Constants.START_ORDER);
        mainLoop();
        reportToMaster(Constants.SLAVE_ONE_STARTING);
    }
    /*
     * Emergency stop
     */

    public void stop() {
        _finishing = true;
        _gearBelt.stopBelt();
        _axisBelt.stopBelt();
        _assemblyStation.stopBelt();
        _weldingBelt.stopBelt();
        orderToRobot(Constants.EMERGENCY_STOP_ORDER);
        reportToMaster(Constants.SLAVE_ONE_STOPPING);
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
            case Constants.ROBOT1_SLAVE1_PICKS_GEAR:
                _gearBelt.removeLastPiece();
                break;
            case Constants.ROBOT1_SLAVE1_PICKS_AXIS:
                _axisBelt.removeLastPiece();
                break;
            case Constants.ROBOT1_SLAVE1_PLACES_GEAR:
                p = new Piece();
                p.setType(PieceType.gear);
                _assemblyStation.addPiece(p);
                break;
            case Constants.ROBOT1_SLAVE1_PLACES_AXIS:
                p = new Piece();
                p.setType(PieceType.axis);
                _assemblyStation.addPiece(p);
                break;
            case Constants.ROBOT1_SLAVE1_PICKS_ASSEMBLY:
                _assemblyStation.removeLastPiece();
                break;
            case Constants.ROBOT1_SLAVE1_REQUEST_ASSEMBLY:
                _assemblyStation.assemble();
                break;
            case Constants.ROBOT1_SLAVE1_PLACES_ASSEMBLY:
                p = new Piece();
                p.setType(PieceType.assembly);
                _weldingBelt.addPiece(p);
                break;
            case Constants.ROBOT2_SLAVE1_PICKS_ASSEMBLY:
                _weldingBelt.removeLastPiece();
                break;
        }
    }

    public void orderToRobot(int orderNumber) {
        throw new UnsupportedOperationException();
        // OJO que aqui los parametros no tienen pq enviarse siempre al mismo robot.
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

    // This loop is intended to keep adding pieces to the starting belts while the system is working
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
                List<Piece> pieces = _gearBelt.getPieces();
                synchronized (pieces) {
                    for (Piece p : pieces) {
                        if (p.getPosition() < pieceSize * 1.1) {
                            roomInGearBelt = false;
                            break;
                        }
                    }
                }

                // if so we add the piece
                if (roomInGearBelt) {
                    Piece p = new Piece();
                    p.setPosition(0);
                    p.setType(PieceType.gear);
                    _gearBelt.addPiece(p);

                    Logger.getLogger(Slave1.class.getName()).log(Level.INFO, "Added gear");
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
                    Logger.getLogger(Slave1.class.getName()).log(Level.INFO, "Added axis");
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
            while (true) {
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