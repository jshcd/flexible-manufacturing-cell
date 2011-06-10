/* Slave 2 code */
package Automaton.Slaves;

import Automaton.Slaves.Data.Slave2Data;
import Auxiliar.Command;
import Auxiliar.Constants;
import Auxiliar.IOInterface;
import Auxiliar.IOProcess;
import Auxiliar.MailboxData;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.Piece.Piece.PieceType;
import Element.Station.QualityControlStation;
import Element.Station.WeldingStation;
import Scada.DataBase.MasterConfigurationData;
import Scada.DataBase.Slave2ConfigurationData;
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

/**
 * Class that represents the slave 2 execution
 * @author Echoplex
 */
public class Slave2 implements Slave, IOProcess {

    /**
     * Output mailbox
     */
    private SlaveOutputMailBox _outputMailBox;
    /**
     * Input mailbox
     */
    protected SlaveInputMailBox _inputMailBox;
    /**
     * Welding station
     */
    private WeldingStation _weldingStation;
    /**
     * Quality station
     */
    private QualityControlStation _qualityStation;
    /**
     * Sensor in the end of the welding belt
     */
    private Sensor _sensor6;
    /**
     * Sensor in the welding table
     */
    private Sensor _sensor7;
    /**
     * Data from the slave 2
     */
    private Slave2Data _statusData;
    /**
     * Indicates is the slave is stopped
     */
    private boolean _stopped;
    /**
     * 16 bits interface
     */
    IOInterface ioi;
    /**
     * Configuration data for slave 2
     */
    private Slave2ConfigurationData _slave2ConfigurationData;
    /**
     * Sensor range
     */
    private double sensor_range;
    /**
     * Logger
     */
    protected Logger _logger = Logger.getLogger(Slave2.class.toString());
    /**
     * Success rate for quality station
     */
    private int _sucessRate;

    /**
     * Main method that creates a slave
     * @param args
     */
    public static void main(String args[]) {
        Slave2 s2 = new Slave2();
    }

    /**
     * Constructor of the class, initialize the execution
     */
    public Slave2() {
        _logger.log(Level.INFO, "Slave 2 created");
        _outputMailBox = new SlaveOutputMailBox(2);
        _inputMailBox = new SlaveInputMailBox(2, this);
        _stopped = true;
        _sucessRate = 50;
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
    public void connectToMaster() {
        try {
            reportToMaster(new Command(Constants.COMMAND_SLAVE2_CONNECTED));
        } catch (IOException ex) {
            this._outputMailBox.startConnection();
            connectToMaster();
        }
    }

    /**
     * Gets the welding station
     * @return WeldingStation
     */
    public WeldingStation getWeldingStation() {
        return _weldingStation;
    }

    /**
     * Gets the sensor 6
     * @return Sensor
     */
    public Sensor getSensor6() {
        return _sensor6;
    }

    /**
     * Gets the sensor 7
     * @return Sensor
     */
    public Sensor getSensor7() {
        return _sensor7;
    }

    /**
     * Updates the status data of the slave
     */
    public void updateStatusData() {
        _statusData = new Slave2Data();
        _statusData.setSensor6Status(_sensor6.isActivated());
        _statusData.setSensor7Status(_sensor7.isActivated());
        _statusData.setQualityStationPieces(_qualityStation.getPieces());
        _statusData.setQualityStationRunning(_qualityStation.isActuating());
        _statusData.setWeldingStationPieces(_weldingStation.getPieces());
        _statusData.setWeldingStationRunning(_weldingStation.isActuating());

        try {
            reportToMaster(_statusData);
        } catch (IOException ex) {
            this._outputMailBox.startConnection();
        }
    }

    /**
     * Initializes the slave
     */
    public final void initialize() {

        ioi = new IOInterface();
        ioi.setProcess(this);
        ioi.setPortLag(1);
        ioi.bind();
        (new Thread(ioi)).start();


        _outputMailBox = new SlaveOutputMailBox(2);

        _weldingStation = new WeldingStation(5);
        _weldingStation.setWeldingTime(_slave2ConfigurationData._weldingActivationTime);
        _weldingStation.setProcess(this);
        _qualityStation = new QualityControlStation(6);
        _qualityStation.setQualityTime(_slave2ConfigurationData._qualityControlActivationTime);
        _qualityStation.setSucessRate(_sucessRate);
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


        Thread y = new Thread(new Runnable() {

            public void run() {
                try {
                    while (true) {
                        Thread.sleep(50);
                        try {
                            updateStatusData();
                        } catch (java.util.ConcurrentModificationException e) {
                        }
                    }
                } catch (InterruptedException ex) {
                    _logger.log(Level.SEVERE, null, ex);
                }
            }
        });
        y.start();
    }

    /**
     * Starts the execution of the slave
     */
    public void start() {
        _stopped = false;
        _weldingStation.startContainer();
        _qualityStation.startContainer();
        try {
            reportToMaster(new Command(Constants.SLAVE_TWO_STARTING));
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Stops the execution of the slave as an Emergency
     */
    public void emergencyStop() {
        _stopped = true;
        _weldingStation.stopContainer();
        _qualityStation.stopContainer();
        try {
            reportToMaster(new Command(Constants.SLAVE_TWO_STOPPING));
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Executes a command
     * @param command Command identifier
     */
    public void runCommand(int command) {
        try {
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
                    sendCommand(Constants.SLAVE2_ROBOT2_ASSEMBLY_PLACED);
                    Thread.sleep(50);
                    sendCommand(Constants.SLAVE2_ROBOT2_ASSEMBLY_PLACED);
                    break;
                case Constants.ROBOT2_SLAVE2_PICKS_WELDED_ASSEMBLY:
                    _weldingStation.removeLastPiece();
                    sendCommand(Constants.SLAVE2_ROBOT2_WELDED_ASSEMBLY_PICKED);
                    Thread.sleep(50);
                    sendCommand(Constants.SLAVE2_ROBOT2_WELDED_ASSEMBLY_PICKED);
                    break;
                case Constants.ROBOT2_SLAVE2_PLACES_WELDED_ASSEMBLY:
                    p = new Piece();
                    p.setType(PieceType.weldedAssembly);
                    _qualityStation.addPiece(p);
                    sendCommand(Constants.SLAVE2_ROBOT2_WELDED_ASSEMBLY_PLACED);
                    Thread.sleep(50);
                    sendCommand(Constants.SLAVE2_ROBOT2_WELDED_ASSEMBLY_PLACED);
                    break;
                case Constants.ROBOT2_SLAVE2_PICKS_CHECKED_WELDED_ASSEMBLY:
                    _qualityStation.removeLastPiece();
                    sendCommand(Constants.SLAVE2_ROBOT2_CHECKED_WELDED_ASSEMBLY_PICKED);
                    Thread.sleep(50);
                    sendCommand(Constants.SLAVE2_ROBOT2_CHECKED_WELDED_ASSEMBLY_PICKED);
                    break;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Slave2.class.getName()).log(Level.SEVERE, null, ex);
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
        _outputMailBox.startConnection();
        _outputMailBox.acceptConnection();
        _outputMailBox.sendCommand(data);
        _outputMailBox.receiveCommand();
    }

    /**
     * Starts the communication with the server
     */
    public void startServer() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream(Constants.MAILBOXES_PROPERTIES_PATH);
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Slave2.port"));
            ServerSocket skServidor = new ServerSocket(port);
            _logger.log(Level.INFO, "Server listening at port {0}", port);
            while (true) {
                Socket skCliente = skServidor.accept();
                ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                _logger.log(Level.FINE, "Slave2 received> {0}", Short.parseShort((String) in.readObject()));
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
        _slave2ConfigurationData = md._slave2ConfigurationData;
        sensor_range = (double) md._sensorRange;
        _sucessRate = md._successRate;
        initialize();
    }

    /**
     * Stops the execution of the slave
     */
    public void normalStop() {
    }
}
