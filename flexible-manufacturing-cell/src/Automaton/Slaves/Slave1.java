/* Slave 1 code */
package Automaton.Slaves;

import Automaton.Slaves.Data.Slave1Data;
import Element.Robot.Robot1;
import Auxiliar.*;
import Element.Conveyor.ConveyorBelt;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.Piece.Piece.PieceType;
import Element.Station.AssemblyStation;
import Scada.DataBase.MasterConfigurationData;
import Scada.DataBase.Robot1ConfigurationData;
import Scada.DataBase.Slave1ConfigurationData;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that represents the slave 1 execution
 * @author Echoplex
 */
public class Slave1 implements Slave, IOProcess {

    /**
     * Output mailbox
     */
    protected SlaveOutputMailBox _outputMailBox;
    /**
     * Input mailbox
     */
    protected SlaveInputMailBox _inputMailBox;
    /**
     * Gear belt
     */
    protected ConveyorBelt _gearBelt;
    /**
     * Axis belt
     */
    protected ConveyorBelt _axisBelt;
    /**
     * Welding belt
     */
    protected ConveyorBelt _weldingBelt;
    /**
     * Robot 1
     */
    protected Robot1 _robot;
    /**
     * Assembly station
     */
    protected AssemblyStation _assemblyStation;
    /**
     * Sensor at the end of the axis belt
     */
    protected Sensor _sensor1;
    /**
     * Sensor at the end of the gear belt
     */
    protected Sensor _sensor2;
    /**
     * Sensor at the assembly table
     */
    protected Sensor _sensor3;
    /**
     * Sensor at the beginning of the welding belt
     */
    protected Sensor _sensor4;
    /**
     * Sensor at the end of the welding belt
     */
    protected Sensor _sensor5;
    /**
     * indicates if the slaves has finishing to process all the pieces
     */
    protected boolean _finishing;
    /**
     * Indicates is the slave is stopped
     */
    private boolean _stopped;
    /**
     * Data from the slave 1
     */
    protected Slave1Data _statusData;
    /**
     * Configuration data for slave 1
     */
    protected Slave1ConfigurationData _slave1ConfigurationData;
    /**
     * Configuration data for robot 1
     */
    protected Robot1ConfigurationData _robot1ConfigurationData;
    /**
     * Sensor range
     */
    protected double sensor_range = 1.2;
    /**
     * Gear and axis size
     */
    protected double pieceSize = 1.5;
    /**
     * 16 bits interface
     */
    private IOInterface ioi;
    /**
     * Logger
     */
    protected Logger _logger = Logger.getLogger(Slave1.class.toString());

    /**
     * Main method that creates a slave
     * @param args
     */
    public static void main(String args[]) {
        Slave1 s1 = new Slave1();
    }

    /**
     * Constructor of the class, initialize the execution
     */
    public Slave1() {

        _logger.log(Level.INFO, "Slave 1 created");
        _outputMailBox = new SlaveOutputMailBox(1);
        _inputMailBox = new SlaveInputMailBox(1, this);

        _stopped = true;

        _statusData = new Slave1Data();
        Thread t = new Thread(new Runnable() {

            public void run() {
                _inputMailBox.startServer();
            }
        });
        t.start();

        connectToMaster();

    }

    /**
     * Connects to the master
     */
    private void connectToMaster() {
        try {
            reportToMaster(new Command(Constants.COMMAND_SLAVE1_CONNECTED));
        } catch (IOException ex) {
            this._outputMailBox.startConnection();
            connectToMaster();
        }
    }

    /**
     * Gets the gear conveyor belt
     * @return ConveyorBelt
     */
    public ConveyorBelt getGearBelt() {
        return _gearBelt;
    }

    /**
     * Gets the axis conveyor belt
     * @return ConveyorBelt
     */
    public ConveyorBelt getAxisBelt() {
        return _axisBelt;
    }

    /**
     * Gets the welding conveyor belt
     * @return ConveyorBelt
     */
    public ConveyorBelt getWeldingBelt() {
        return _weldingBelt;
    }

    /**
     * Gets the assembly station
     * @return AssemblyStation
     */
    public AssemblyStation getAssemblyStation() {
        return _assemblyStation;
    }

    /**
     * Updates the status data of the slave
     */
    public void updateStatusData() {
        try {
            synchronized (_statusData) {
                _statusData.setSensor1Status(_sensor1.isActivated());
                _statusData.setSensor2Status(_sensor2.isActivated());
                _statusData.setSensor3Status(_sensor3.isActivated());
                _statusData.setSensor4Status(_sensor4.isActivated());
                _statusData.setSensor5Status(_sensor5.isActivated());
                _statusData.setAssemblyStationPieces(_assemblyStation.getPieces());
                _statusData.setAssemblyStationRunning(_assemblyStation.isActuating());
                _statusData.setGearBeltPieces(_gearBelt.getPieces());
                _statusData.setGearBeltRunning(_gearBelt.isMoving());
                _statusData.setAxisBeltPieces(_axisBelt.getPieces());
                _statusData.setAxisBeltRunning(_axisBelt.isMoving());
                _statusData.setWeldingBeltPieces(_weldingBelt.getPieces());
                _statusData.setWeldingBeltRunning(_weldingBelt.isMoving());
            }
            try {
                reportToMaster(_statusData);
            } catch (IOException ex) {
                this._outputMailBox.startConnection();

            }
        } catch (java.util.ConcurrentModificationException e) {
        }
    }

    /**
     * Initializes the slave
     */
    public final void initialize() {

        ioi = new IOInterface();
        ioi.setProcess(this);
        ioi.setPortLag(0);
        ioi.bind();

        (new Thread(ioi)).start();

        _finishing = false;
        _stopped = true;

        int gearSpeed = _slave1ConfigurationData._gearBeltConfiguration.getSpeed();
        double gearLength = (double) _slave1ConfigurationData._gearBeltConfiguration.getLength();
        int axisSpeed = _slave1ConfigurationData._axisBeltConfiguration.getSpeed();
        double axisLength = (double) _slave1ConfigurationData._axisBeltConfiguration.getLength();
        int weldingSpeed = _slave1ConfigurationData._weldingBeltConfiguration.getSpeed();
        double weldingLength = (double) _slave1ConfigurationData._weldingBeltConfiguration.getLength();

        _gearBelt = new ConveyorBelt(1, gearSpeed, gearLength);
        _axisBelt = new ConveyorBelt(2, axisSpeed, axisLength);

        _assemblyStation = new AssemblyStation(3);
        _assemblyStation.setAssemblyTime(_slave1ConfigurationData._assemblyActivationTime);
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

        Thread y = new Thread(new Runnable() {

            public void run() {
                try {
                    while (true) {
                        Thread.sleep(50);
                        updateStatusData();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        y.start();


    }

    /**
     * Starts the execution of the robot
     */
    public void startRobot() {
        _robot = new Robot1();
        _robot.setSlave(this);

        _robot.setTrasportTime1(_robot1ConfigurationData.getPickAndPlaceGearTime());
        _robot.setTransportTime2(_robot1ConfigurationData.getPickAndPlaceAxisTime());
        _robot.setTransportTime3(_robot1ConfigurationData.getPickAndPlaceAssemblyTime());
        (new Thread(_robot)).start();
    }

    /**
     * Gets the sensor 1
     * @return Sensor
     */
    public Sensor getSensor1() {
        return _sensor1;
    }

    /**
     * Gets the sensor 2
     * @return Sensor
     */
    public Sensor getSensor2() {
        return _sensor2;
    }

    /**
     * Gets the sensor 3
     * @return Sensor
     */
    public Sensor getSensor3() {
        return _sensor3;
    }

    /**
     * Gets the sensor 4
     * @return Sensor
     */
    public Sensor getSensor4() {
        return _sensor4;
    }

    /**
     * Gets the sensor 5
     * @return Sensor
     */
    public Sensor getSensor5() {
        return _sensor5;
    }

    /**
     * Starts the execution of the slave
     */
    public void start() {
        _logger.log(Level.INFO, "Slave 1 starting");
        _finishing = false;
        _stopped = false;

        _gearBelt.startContainer();
        _axisBelt.startContainer();
        _assemblyStation.startContainer();
        _weldingBelt.startContainer();
        sendCommand(Constants.START_ROBOT1);

        Thread t = new Thread(new Runnable() {

            public void run() {
                mainLoop();
            }
        });
        t.start();

    }

    /**
     * Stops the execution of the slave as an Emergency
     */
    public void emergencyStop() {
        _logger.log(Level.INFO, "Slave 1 stopping");
        _finishing = true;
        _stopped = true;
        _gearBelt.stopContainer();
        _axisBelt.stopContainer();
        _assemblyStation.stopContainer();
        _weldingBelt.stopContainer();
        _robot.sendCommand(Constants.EMERGENCY_STOP_ORDER);
        try {
            reportToMaster(new Command(Constants.SLAVE_ONE_STOPPING));
        } catch (IOException ex) {
            Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
            this._outputMailBox.startConnection();
        }
    }

    /**
     * Executes a command
     * @param command Command identifier
     */
    public void runCommand(int command) {
        Piece p;
//        System.out.println("S1 receives: " + command);

        switch (command) {
            case Constants.START_SLAVE1:
                start();
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
                sendCommand(Constants.SLAVE1_ROBOT2_ASSEMBLY_PICKED);
                break;
            case Constants.SENSOR_GEAR_UNLOAD_ACTIVATED:
                _gearBelt.stopContainer();
                break;
            case Constants.SENSOR_AXIS_UNLOAD_ACTIVATED:
                _axisBelt.stopContainer();
                break;
            case Constants.SENSOR_WELDING_UNLOAD_ACTIVATED:
                _weldingBelt.stopContainer();
                break;

        }
        if (!_stopped) {
            switch (command) {
                case Constants.SENSOR_GEAR_UNLOAD_DISACTIVATED:
                    _gearBelt.startContainer();
                    break;
                case Constants.SENSOR_AXIS_UNLOAD_DISACTIVATED:
                    _axisBelt.startContainer();
                    break;
                case Constants.SENSOR_WELDING_UNLOAD_DISACTIVATED:
                    _weldingBelt.startContainer();
                    break;
            }
        }
    }

    /**
     * Sends a command
     * @param command Command identifier
     */
    public void sendCommand(int command) {
        ioi.send((short) command);
    }

    /**
     * Reports changes to the master
     * @param data Data that has been changed
     * @throws IOException
     */
    public void reportToMaster(MailboxData data) throws IOException {
        synchronized (data) {
            _outputMailBox.startConnection();
            _outputMailBox.acceptConnection();
            _outputMailBox.sendCommand(data);
            _outputMailBox.receiveCommand();
        }
    }

    /**
     * This loop is intended to keep adding pieces to the starting belts while the system is working
     */
    protected void mainLoop() {
        // If we didn't receive the order to finish, we keep adding pieces
        
            Thread addGear = new Thread(new Runnable() {
                public void run(){
                    addGear();
                }
            });
            addGear.start();
            Thread addAxis = new Thread(new Runnable() {
                public void run(){
                    addAxis();
                }
            });
            addAxis.start();
    }

    /**
     * Starts the communication with the server
     */
    public void startServer() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream(Constants.MAILBOXES_PROPERTIES_PATH);
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Slave1.port"));
            ServerSocket skServidor = new ServerSocket(port);
            _logger.log(Level.INFO, "Server listening at port {0}", port);
            while (true) {
                Socket skCliente = skServidor.accept();
                _logger.log(Level.INFO, "Information received");
                ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                _logger.log(Level.INFO, "Received> {0}", Short.parseShort((String) in.readObject()));

                short a = (short) 0;
                out.writeObject(a);
                skCliente.close();
            }
        } catch (FileNotFoundException ex) {
            _logger.log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            _logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Stores initial configuration data
     * @param md MasterConfigurationData
     */
    public void storeInitialConfiguration(MasterConfigurationData md) {
        _slave1ConfigurationData = md._slave1ConfigurationData;
        _robot1ConfigurationData = md._robot1ConfigurationData;
        sensor_range = (double) md._sensorRange;
        pieceSize = (double) md._pieceSize;
        initialize();
    }

    /**
     * Stops the execution of the slave
     */
    public void normalStop() {
        Thread t = new Thread(new Runnable() {

            public void run() {

                _finishing = true;
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException ex) {
                    _logger.log(Level.SEVERE, null, ex);
                }
                int numGears = _gearBelt.getPieces().size();
                int numAxis = _axisBelt.getPieces().size();

                AutomatonState robotState = _robot.getState();
                List<Piece> assemblyTablePieces = _assemblyStation.getPieces();

                if (robotState == AutomatonState.q4) {//Transporting Ax
                    numAxis++;
                } else if (robotState == AutomatonState.q3) {//Transporting Gear
                    numGears++;
                }
                if (assemblyTablePieces.size() == 1) {
                    Piece p = assemblyTablePieces.get(0);
                    if (p.getType() == Piece.PieceType.axis) {
                        numAxis++;
                    } else if (p.getType() == Piece.PieceType.gear) {
                        numGears++;
                    }
                }
                int differenceA = numGears - numAxis;
                int differenceG = numAxis - numGears;
                System.out.println("******************\n" + differenceA + "\n******************");
                try {
                    if (differenceA != 0) {
                        while (differenceG > 0) {
                            boolean roomInGearBelt = true;
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
                                    differenceG--;

                                    _logger.log(Level.INFO, "Added gear");
                                }
                            }
                            Thread.sleep(50);
                        }

                        while (differenceA > 0) {
                            boolean roomInAxisBelt = true;
                            //we check if there is room in the gears belt for adding a new piece
                            List<Piece> pieces = _axisBelt.getPieces();
                            synchronized (pieces) {
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
                                    differenceA--;

                                    _logger.log(Level.INFO, "Added axis");
                                }
                            }
                            Thread.sleep(50);
                        }
                    }
                } catch (InterruptedException ex) {
                    _logger.log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();

    }

    /**
     * Updates robot status
     * @param automatonState Automaton
     * @param piece Piece
     */
    public void updateRobot(AutomatonState automatonState, Piece piece) {
        this._statusData.setR1state(automatonState);
        this._statusData.setR1loadedPiece(piece);
    }

    /**
     * If it is possible, it adds a new gear on the gear belt
     */
    private void addGear() {
        while (!_finishing) {
            boolean roomInGearBelt = true;
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
                    _logger.log(Level.INFO, "Added gear");
                }
            }
            try {
                Thread.sleep((int) (5000 * (Math.random()) + 1000));
            } catch (InterruptedException ex) {
                _logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * If it is possible, it adds a new axis on the axis belt
     */
    private void addAxis() {
        while (!_finishing) {
            boolean roomInAxisBelt = true;
            List<Piece> pieces = _axisBelt.getPieces();
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
                    _logger.log(Level.INFO, "Added axis");
                }
            }
            try {
                Thread.sleep((int) (5000 * (Math.random()) + 1000));
            } catch (InterruptedException ex) {
                _logger.log(Level.SEVERE, null, ex);
            }
        }
    }
}
