/* Slave 3 code */
package Automaton.Slaves;

import Automaton.Slaves.Data.Slave3Data;
import Auxiliar.Command;
import Auxiliar.Constants;
import Auxiliar.IOInterface;
import Auxiliar.IOProcess;
import Auxiliar.MailboxData;
import Element.Conveyor.ConveyorBelt;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.Piece.Piece.PieceType;
import Scada.DataBase.MasterConfigurationData;
import Scada.DataBase.Slave3ConfigurationData;
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
 * Class that represents the slave 3 execution
 * @author Echoplex
 */
public class Slave3 implements Slave, IOProcess {

    /**
     * Output mailbox
     */
    private SlaveOutputMailBox _outputMailBox;
    /**
     * Input mailbox
     */
    protected SlaveInputMailBox _inputMailBox;
    /**
     * Ok belt
     */
    private ConveyorBelt _acceptedBelt;
    /**
     * Not Ok belt
     */
    private ConveyorBelt _rejectedBelt;
    /**
     * Sensor at the beginning of the not ok belt
     */
    private Sensor _sensor8;
    /**
     * Sensor at the beginning of the  ok belt
     */
    private Sensor _sensor9;
    /**
     * Sensor at the end of the not ok belt
     */
    private Sensor _sensor10;
    /**
     * Sensor at the end of the  ok belt
     */
    private Sensor _sensor11;
    /**
     * Data from the slave 3
     */
    private Slave3Data _statusData;
    /**
     * 16 bits interface
     */
    private IOInterface ioi;
    /**
     * Configuration data for slave 3
     */
    private Slave3ConfigurationData _slave3ConfigurationData;
    /**
     * Sensor range
     */
    private double sensor_range;
    /**
     * Indicates is the slave is stopped
     */
    private boolean _stopped;
    /**
     * number of current ok  pieces
     */
    private int _rightPieces;
    /**
     * number of current not  ok  pieces
     */
    private int _wrongPieces;
    /**
     * number of total   ok  pieces
     */
    private int _allRightPieces;
    /**
     * number of total not  ok  pieces
     */
    private int _allWrongPieces;
    /**
     * Logger
     */
    protected Logger _logger = Logger.getLogger(Slave3.class.toString());

    /**
     * Main method that creates a slave
     * @param args
     */
    public static void main(String args[]) {
        Slave3 s3 = new Slave3();
    }

    /**
     * Constructor of the class, initialize the execution
     */
    public Slave3() {
        _rightPieces = 0;
        _wrongPieces = 0;
        _allRightPieces = 0;
        _allWrongPieces = 0;
        _logger.log(Level.INFO, "Slave 3 created");
        _outputMailBox = new SlaveOutputMailBox(3);
        _inputMailBox = new SlaveInputMailBox(3, this);
        _stopped = true;
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
            reportToMaster(new Command(Constants.COMMAND_SLAVE3_CONNECTED));
        } catch (IOException ex) {
            this._outputMailBox.startConnection();
            connectToMaster();
        }
    }

    /**
     * Gets the sensor 8
     * @return Sensor
     */
    public Sensor getSensor8() {
        return _sensor8;
    }

    /**
     * Gets the sensor 9
     * @return Sensor
     */
    public Sensor getSensor9() {
        return _sensor9;
    }

    /**
     * Gets the sensor 10
     * @return Sensor
     */
    public Sensor getSensor10() {
        return _sensor10;
    }

    /**
     * Gets the sensor 11
     * @return Sensor
     */
    public Sensor getSensor11() {
        return _sensor11;
    }

    /**
     * Gets the  Ok belt
     * @return ConveyorBelt
     */
    public ConveyorBelt getAcceptedBelt() {
        return _acceptedBelt;
    }

    /**
     * Gets the not Ok belt
     * @return ConveyorBelt
     */
    public ConveyorBelt getRejectedBelt() {
        return _rejectedBelt;
    }

    /**
     * Updates the status data of the slave
     */
    public void updateStatusData() {
        _statusData = new Slave3Data();
        _statusData.setSensor8Status(_sensor8.isActivated());
        _statusData.setSensor9Status(_sensor9.isActivated());
        _statusData.setSensor10Status(_sensor10.isActivated());
        _statusData.setSensor11Status(_sensor11.isActivated());
        _statusData.setAcceptedBeltRunning(_acceptedBelt.isMoving());
        _statusData.setAcceptedBeltPieces(_acceptedBelt.getPieces());
        _statusData.setRejectedBeltPieces(_rejectedBelt.getPieces());
        _statusData.setRejectedBeltRunning(_rejectedBelt.isMoving());
        _statusData.setRightPieces(_rightPieces);
        _statusData.setWrongPieces(_wrongPieces);
        _statusData.setAllRightPieces(_allRightPieces);
        _statusData.setAllWrongPieces(_allWrongPieces);

        try {
            reportToMaster(_statusData);
        } catch (IOException ex) {
            System.out.println("Master not found: retrying in 5 secs");
            this._outputMailBox.startConnection();
        }
    }

    /**
     * Initializes the slave
     */
    public final void initialize() {

        ioi = new IOInterface();
        ioi.setProcess(this);
        ioi.setPortLag(2);
        ioi.bind();
        (new Thread(ioi)).start();

        int acceptedSpeed = _slave3ConfigurationData._acceptedBelt.getSpeed();
        double acceptedLength = (double) _slave3ConfigurationData._acceptedBelt.getLength();
        int reyectedSpeed = _slave3ConfigurationData._notAcceptedBelt.getSpeed();
        double reyectedLength = (double) _slave3ConfigurationData._notAcceptedBelt.getLength();

        _acceptedBelt = new ConveyorBelt(7, acceptedSpeed, acceptedLength);
        _rejectedBelt = new ConveyorBelt(8, reyectedSpeed, reyectedLength);

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
        _sensor9.setPositionInBelt(0);

        _sensor10 = new Sensor();
        _sensor10.setSensorId(10);
        _sensor10.setAssociatedContainer(_rejectedBelt);
        _sensor10.setProcess(this);
        _sensor10.setRange(sensor_range);
        _sensor10.setPositionInBelt(reyectedLength - sensor_range);

        _sensor11 = new Sensor();
        _sensor11.setSensorId(11);
        _sensor11.setAssociatedContainer(_acceptedBelt);
        _sensor11.setProcess(this);
        _sensor11.setRange(sensor_range);
        _sensor11.setPositionInBelt(acceptedLength - sensor_range);

        _acceptedBelt.addSensor(_sensor9);
        _acceptedBelt.addSensor(_sensor11);
        _rejectedBelt.addSensor(_sensor8);
        _rejectedBelt.addSensor(_sensor10);

        // We start the belts
        new Thread(_acceptedBelt).start();
        new Thread(_rejectedBelt).start();

        //We start the sensors
        new Thread(_sensor8).start();
        new Thread(_sensor9).start();
        new Thread(_sensor10).start();
        new Thread(_sensor11).start();


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
        _rightPieces = 0;
        _wrongPieces = 0;
        _acceptedBelt.startContainer();
        _rejectedBelt.startContainer();
        try {
            reportToMaster(new Command(Constants.SLAVE_THREE_STARTING));
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Stops the execution of the slave as an Emergency
     */
    public void emergencyStop() {
        _stopped = true;
        _acceptedBelt.stopContainer();
        _rejectedBelt.stopContainer();
        try {
            reportToMaster(new Command(Constants.SLAVE_THREE_STOPPING));
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
        _rightPieces = 0;
        _wrongPieces = 0;
    }

    /**
     * Executes a command
     * @param command Command identifier
     */
    public void runCommand(int command) {
        try {
            //        if(command >80) System.out.println("S3 received: " + command);
            Piece p;
            switch (command) {
                case Constants.START_SLAVE3:
                    start();
                    break;
                case Constants.ROBOT2_SLAVE3_PLACES_WELDED_OK:
                    p = new Piece();
                    p.setType(PieceType.weldedAssembly);
                    _acceptedBelt.addPiece(p);
                    this._rightPieces++;
                    this._allRightPieces++;
                    sendCommand(Constants.SLAVE3_ROBOT2_WELDED_ASSEMBLY_PLACED);
                    Thread.sleep(50);
                    sendCommand(Constants.SLAVE3_ROBOT2_WELDED_ASSEMBLY_PLACED);
                    break;
                case Constants.ROBOT2_SLAVE3_PLACES_WELDED_NOT_OK:
                    p = new Piece();
                    p.setType(PieceType.weldedAssembly);
                    _rejectedBelt.addPiece(p);
                    this._wrongPieces++;
                    this._allWrongPieces++;
                    sendCommand(Constants.SLAVE3_ROBOT2_WELDED_ASSEMBLY_PLACED);
                    Thread.sleep(50);
                    sendCommand(Constants.SLAVE3_ROBOT2_WELDED_ASSEMBLY_PLACED);
                    break;
            }
            if (!_stopped) {
                switch (command) {
                    case Constants.SENSOR_OK_UNLOAD_ACTIVATED:
                        _acceptedBelt.removeLastPiece();
                        _logger.log(Level.INFO, "Piece taken out from accepted belt");
                        _acceptedBelt.stopContainer();
                        break;
                    case Constants.SENSOR_OK_UNLOAD_DISACTIVATED:
                        _acceptedBelt.startContainer();
                        break;
                    case Constants.SENSOR_NOT_OK_UNLOAD_ACTIVATED:
                        _rejectedBelt.removeLastPiece();
                        _logger.log(Level.INFO, "Piece taken out from rejected belt");
                        _rejectedBelt.stopContainer();
                        break;
                    case Constants.SENSOR_NOT_OK_UNLOAD_DISACTIVATED:
                        _rejectedBelt.startContainer();
                        break;
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Slave3.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * Sends a command
     * @param command Command identifier
     */
    public void sendCommand(int command) {
        ioi.send((short) command);
    }

    /**
     * Starts the communication with the server
     */
    public void startServer() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream(Constants.MAILBOXES_PROPERTIES_PATH);
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Slave3.port"));
            ServerSocket skServidor = new ServerSocket(port);
            _logger.log(Level.INFO, "Slave 3 listening at port {0}", port);
            while (true) {
                Socket skCliente = skServidor.accept();
                ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                _logger.log(Level.FINE, "Slave 3 received> {0}", Short.parseShort((String) in.readObject()));
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
        _slave3ConfigurationData = md._slave3ConfigurationData;
        sensor_range = (double) md._sensorRange;
        initialize();
    }

    /**
     * Stops the execution of the slave
     */
    public void normalStop() {
    }
}
