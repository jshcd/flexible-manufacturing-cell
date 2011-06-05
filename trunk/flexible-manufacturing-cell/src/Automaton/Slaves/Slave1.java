/* Slave 1 code */
package Automaton.Slaves;

import Automaton.Slaves.Data.Slave1Data;
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
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Slave1 implements Slave {

    protected SlaveOutputMailBox _mailBox;
    protected ConveyorBelt _gearBelt;
    protected ConveyorBelt _axisBelt;
    protected ConveyorBelt _weldingBelt;
    protected Robot1 _robot;
    protected DBConnection _dbconnection;
    protected AssemblyStation _assemblyStation;
    protected Sensor _sensor1;
    protected Sensor _sensor2;
    protected Sensor _sensor3;
    protected Sensor _sensor4;
    protected Sensor _sensor5;
    protected boolean _finishing;
    
    protected Slave1Data _statusData;

    public Slave1() {
        Logger.getLogger(Slave1.class.getName()).log(Level.INFO, "Slave 1 created");
    }
    
    public ConveyorBelt getGearBelt(){return _gearBelt;}
    public ConveyorBelt getAxisBelt(){return _axisBelt;}
    public ConveyorBelt getWeldingBelt(){return _weldingBelt;}
    public AssemblyStation getAssemblyStation(){return _assemblyStation;}
    
    public void updateStatusData(){
        _statusData = new Slave1Data();
        _statusData.setSensor1Status(_sensor1.isActivated());
        _statusData.setSensor2Status(_sensor2.isActivated());
        _statusData.setSensor3Status(_sensor3.isActivated());
        _statusData.setSensor5Status(_sensor5.isActivated());
        _statusData.setAssemblyStationPieces(_assemblyStation.getPieces());
        _statusData.setAssemblyStationRunning(_assemblyStation.isMoving());
        _statusData.setGearBeltPieces(_gearBelt.getPieces());
        _statusData.setGearBeltRunning(_gearBelt.isMoving());
        _statusData.setAxisBeltPieces(_axisBelt.getPieces());
        _statusData.setAxisBeltRunning(_axisBelt.isMoving());
        _statusData.setWeldingBeltPieces(_weldingBelt.getPieces());
        _statusData.setWeldingBeltRunning(_weldingBelt.isMoving());
        _mailBox.sendCommand(_statusData);
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
            double axisLength = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE1_BELT2_CONFIGURATION).getDouble("speed");
            int weldingSpeed = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE1_BELT3_CONFIGURATION).getInt("length");
            double weldingLength = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SLAVE1_BELT3_CONFIGURATION).getDouble("speed");
            double sensor_range = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_SENSOR_RANGE).getDouble("value");

            _gearBelt = new ConveyorBelt(1, gearSpeed, gearLength);
            _axisBelt = new ConveyorBelt(2, axisSpeed, axisLength);

            _assemblyStation = new AssemblyStation(3);
            _assemblyStation.setAssemblyTime(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_ASSEMBLY_STATION_TIME).getInt("time"));
            _assemblyStation.setProcess(this);

            _weldingBelt = new ConveyorBelt(4, weldingSpeed, weldingLength);

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
            _sensor5.setSensorId(5);
            _sensor5.setAssociatedContainer(_weldingBelt);
            _sensor5.setProcess(this);
            _sensor5.setRange(sensor_range);
            _sensor5.setPositionInBelt(weldingLength - sensor_range);

            _gearBelt.addSensor(_sensor1);
            _axisBelt.addSensor(_sensor2);
            _assemblyStation.addSensor(_sensor3);
            _weldingBelt.addSensor(_sensor4);
            _weldingBelt.addSensor(_sensor5);

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
            Thread sensor3 = new Thread(_sensor3);
            sensor3.start();
            Thread sensor4 = new Thread(_sensor4);
            sensor4.start();
            Thread sensor5 = new Thread(_sensor5);
            sensor5.start();

            startRobot();

        } catch (SQLException ex) {
            System.err.println("Error at loading database at Slave 1");
            Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void startRobot() {
        _robot = new Robot1();
        try {
            _robot.setTrasportTime1(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR1).getInt("time"));
            _robot.setTransportTime2(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR2).getInt("time"));
            _robot.setTransportTime3(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR3).getInt("time"));
            Thread t = new Thread(new Runnable() {
                public void run() {
                   _robot.startServer();
                }
            });
            t.start();
            (new Thread(_robot)).start();

        } catch (SQLException ex) {
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
    
    public Sensor getSensor4() {
        return _sensor4;
    }

    public Sensor getSensor5() {
        return _sensor5;
    }

    /*
     * Starts the system
     */
    public void start() {
        _finishing = false;
        _gearBelt.startContainer();
        _axisBelt.startContainer();
        _assemblyStation.startContainer();
        _weldingBelt.startContainer();
        orderToRobot(Constants.START_ROBOT1);
        reportToMaster(Constants.SLAVE_ONE_STARTING);
        mainLoop();
    }
    /*
     * Emergency stop
     */

    public void stop() {
        _finishing = true;
        _gearBelt.stopContainer();
        _axisBelt.stopContainer();
        _assemblyStation.stopContainer();
        _weldingBelt.stopContainer();
        orderToRobot(Constants.EMERGENCY_STOP_ORDER);
        reportToMaster(Constants.SLAVE_ONE_STOPPING);
    }

    public void runCommand(int command) {
        Piece p;
        switch (command) {
            case Constants.START_SLAVE1:
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
            case Constants.SENSOR_GEAR_UNLOAD_ACTIVATED:
                _gearBelt.stopContainer();
                break;
            case Constants.SENSOR_GEAR_UNLOAD_DISACTIVATED:
                _gearBelt.startContainer();
                break;
            case Constants.SENSOR_AXIS_UNLOAD_ACTIVATED:
                _axisBelt.stopContainer();
                break;
            case Constants.SENSOR_AXIS_UNLOAD_DISACTIVATED:
                _axisBelt.startContainer();
                break;
            case Constants.SENSOR_WELDING_UNLOAD_ACTIVATED:
                _weldingBelt.stopContainer();
                break;
            case Constants.SENSOR_WELDING_UNLOAD_DISACTIVATED:
                _weldingBelt.startContainer();
                break;
        }
    }

    public void orderToRobot(int orderNumber) {
        try {
            InputStream is = null;
            Socket requestSocket = new Socket();
            ObjectOutputStream out;
            ObjectInputStream in;
            Properties prop = new Properties();
            is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Robot1.port"));
            String address = prop.getProperty("Robot1.ip");
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
        }
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
    protected void mainLoop() {

        double pieceSize;
        try {

            pieceSize = _dbconnection.executeSelect(Constants.DBQUERY_SELECT_PIECE_SIZE).getDouble("value");
            // if we didn't receive the order to finish, we keep adding pieces
            while (!_finishing) {

                Thread.sleep((int) (500 * (Math.random()) + 1000));

                boolean roomInGearBelt = true;
                boolean roomInAxisBelt = true;

                //we check if there is room in the gears belt for adding a new piece
                List<Piece> pieces = _gearBelt.getPieces();
                synchronized (pieces) {
                    Iterator it = pieces.iterator();
                    while (it.hasNext()) {
                        Piece p = (Piece) it.next();
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

                        Logger.getLogger(Slave1.class.getName()).log(Level.FINE, "Added gear");
                    }
                }

                pieces = _axisBelt.getPieces();
                synchronized (pieces) {

                    //we check if there is room in the axis belt for adding a new piece
                    Iterator it = pieces.iterator();
                    while (it.hasNext()) {
                        Piece p = (Piece) it.next();
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
                        Logger.getLogger(Slave1.class.getName()).log(Level.FINE, "Added axis");
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
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