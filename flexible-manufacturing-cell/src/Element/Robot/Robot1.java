package Element.Robot;

import Automaton.Slaves.Slave1;
import Auxiliar.AutomatonState;
import Element.Piece.Piece;
import Auxiliar.Constants;
import Auxiliar.IOInterface;
import Auxiliar.IOProcess;
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
 * Defines the Robot1 Behaviour
 * @author Echocplex
 */
public class Robot1 implements Robot, Runnable, IOProcess {
    
    /**
     * Status of the Robot1
     */
    private AutomatonState _state;
    
    /**
     * Piece that the Robot has loaded
     */
    private Piece _loadedPiece;
    
    /**
     * Time to pick and transport an axis
     * When the robot has moved the gear to the assembly table, 
     * the robot 1 waits until the sensor 2 is active.
     */
    private int _transportTime1;
    
    /**
     * Time to pick and transport a gear
     */
    private int _transportTime2;
    
    /**
     * Time to pick and transport an assembly
     */
    private int _transportTime3;
    
    /**
     * Gear Belt sensor status
     */
    private boolean _gearSensor;
    
    /**
     * Axis Belt sensor status
     */
    private boolean _axisSensor;
    
    /**
     * Assembly Station Sensor Status
     */
    private boolean _assemblySensor;
    
    /**
     * Welding Station Sensor status
     */
    private boolean _weldingSensor;
    
    /**
     * Shows if the assembly process is completed or not
     */
    private boolean _assemblyCompleted;
    
    /**
     * Shows if the robot is currently working
     */
    private boolean _running;
    
    /**
     * Instance of IOInterface
     */
    private IOInterface ioi;
    
    /**
     * Instance of slave1
     */
    private Slave1 _slave;
    // public Logger _logger = Logger.getLogger(Robot1.class.toString());

    /**
     * Constructor of the class
     */
    public Robot1() {
        _state = AutomatonState.q0;
        _gearSensor = false;
        _axisSensor = false;
        _assemblySensor = false;
        _weldingSensor = false;
        _assemblyCompleted = false;
        _running = false;
        ioi = new IOInterface();
        ioi.setProcess(this);
        ioi.setPortLag(3);
        ioi.bind();
        (new Thread(ioi)).start();
    }

    public AutomatonState getState() {
        return _state;
    }
    
    /**
     * Run method
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Robot1.class.toString()).log(Level.SEVERE, null, ex);
            }
            
            this._slave.updateRobot(_state, _loadedPiece);
            
            switch (_state) {
                case q0:
                    if (_gearSensor) {
                        pickGear();
                        _state = AutomatonState.q1;
                    } else if (_axisSensor) {
                        pickAxis();
                        _state = AutomatonState.q2;
                    }
                    break;
                case q1:
                    transportGear();
                    _state = AutomatonState.q3;
                    break;
                case q2:
                    transportAxis();
                    _state = AutomatonState.q4;
                    break;
                case q3:
                    if (_axisSensor) {
                        pickAxis();
                        _state = AutomatonState.q5;
                    }
                    break;
                case q4:
                    if (_gearSensor) {
                        pickGear();
                        _state = AutomatonState.q6;
                    }
                    break;
                case q5:
                    transportAxis();
                    _state = AutomatonState.q7;
                    break;
                case q6:
                    transportGear();
                    _state = AutomatonState.q7;
                    break;
                case q7:
                    this.sendCommand(Constants.ROBOT1_SLAVE1_REQUEST_ASSEMBLY);
                    if (_assemblyCompleted) {
                        pickAssembly();
                        _state = AutomatonState.q8;
                    }
                    break;
                case q8:
                    if (!_weldingSensor) {
                        transportAssembly();
                        _state = AutomatonState.q9;
                    }
                    break;
                case q9:
                    returnToIdle();
            }
            
            Thread.yield();
        }
    }
    
    /**
     * Method that runs a command and perform an action with the robot
     * @param command Command to run
     * @see Constants
     */
    public void runCommand(int command) {
        switch (command) {
            case Constants.START_ROBOT1:
                _running = true;
                break;
            case Constants.NORMAL_STOP_ORDER:
                _running = false;
                break;
            case Constants.SENSOR_GEAR_UNLOAD_ACTIVATED:
                _gearSensor = true;
                break;
            case Constants.SENSOR_GEAR_UNLOAD_DISACTIVATED:
                _gearSensor = false;
                break;
            case Constants.SENSOR_AXIS_UNLOAD_ACTIVATED:
                _axisSensor = true;
                break;
            case Constants.SENSOR_AXIS_UNLOAD_DISACTIVATED:
                _axisSensor = false;
                break;
            case Constants.SENSOR_ASSEMBLY_ACTIVATED:
                _assemblySensor = true;
                break;
            case Constants.SENSOR_ASSEMBLY_DISACTIVATED:
                _assemblySensor = false;
                break;
            case Constants.SENSOR_WELDING_LOAD_ACTIVATED:
                _weldingSensor = true;
                break;
            case Constants.SENSOR_WELDING_LOAD_DISACTIVATED:
                _weldingSensor = false;
                break;
            case Constants.SLAVE1_ROBOT1_ASSEMBLY_COMPLETED:
                _assemblyCompleted = true;
                break;
        }
    }
    
    /**
     * The robot picks an axis from the axis belt
     */
    public void pickAxis() {
        try {
            Thread.sleep(_transportTime2 / 3);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.toString()).log(Level.SEVERE, null, ex);
        }
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.axis);
        _axisSensor = false;
        sendCommand(Constants.ROBOT1_SLAVE1_PICKS_AXIS);
        Logger.getLogger(Robot1.class.toString()).log(Level.INFO, "Robot1 picks axis");
        
    }
    
    /**
     * The robot picks a gear from the gear belt 
     */
    public void pickGear() {
        try {
            Thread.sleep(_transportTime1 / 3);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.toString()).log(Level.SEVERE, null, ex);
        }
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.gear);
        _gearSensor = false;
        sendCommand(Constants.ROBOT1_SLAVE1_PICKS_GEAR);
        Logger.getLogger(Robot1.class.toString()).log(Level.INFO, "Robot1 picks gear");
    }
    
    /**
     * The robot picks an Assembly from the Assembly Station
     */
    public void pickAssembly() {
        try {
            Thread.sleep(_transportTime3 / 3);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.toString()).log(Level.SEVERE, null, ex);
        }
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.assembly);
        _assemblyCompleted = false;
        sendCommand(Constants.ROBOT1_SLAVE1_PICKS_ASSEMBLY);
        Logger.getLogger(Robot1.class.toString()).log(Level.INFO, "Robot1 picks assembly from assembly station");
    }
    
    /**
     * The robot transports a Gear to the assembly station
     */
    public void transportGear() {
        try {
            Thread.sleep(_transportTime1 / 3 * 2);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.toString()).log(Level.SEVERE, null, ex);
        }
        sendCommand(Constants.ROBOT1_SLAVE1_PLACES_GEAR);
        Logger.getLogger(Robot1.class.toString()).log(Level.INFO, "Robot1 places gear on assembly station");
        _loadedPiece = null;
    }
    
    /**
     * The robot transports an axis to the assembly station
     */
    public void transportAxis() {
        try {
            Thread.sleep(_transportTime2 / 3 * 2);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.toString()).log(Level.SEVERE, null, ex);
        }
        sendCommand(Constants.ROBOT1_SLAVE1_PLACES_AXIS);
        Logger.getLogger(Robot1.class.toString()).log(Level.INFO, "Robot1 places axis on assembly station");
        _loadedPiece = null;
    }
    
    /**
     * The robot transport an assembly to the next step
     */
    public void transportAssembly() {
        try {
            Thread.sleep(_transportTime3 / 3 * 2);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.toString()).log(Level.SEVERE, null, ex);
        }
        sendCommand(Constants.ROBOT1_SLAVE1_PLACES_ASSEMBLY);
        Logger.getLogger(Robot1.class.toString()).log(Level.INFO, "Robot1 places assembly on welding belt");
        _loadedPiece = null;
    }
    
    /**
     * The robot goes back to the stop position
     */
    public void returnToIdle() {
        try {
            switch (_state) {
                case q0:
                    break;
                case q9:
                    Thread.sleep(_transportTime3);
                case q8:
                case q7:
                    Thread.sleep(_transportTime2);
                case q6:
                case q5:
                case q4:
                case q3:
                case q2:
                case q1:
                    _state = AutomatonState.q0;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.toString()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Sets the time to pick and transport an axis 
     * @param transportTime1 New time in ms
     */
    public void setTrasportTime1(int transportTime1) {
        Logger.getLogger(Robot1.class.toString()).log(Level.INFO, "Robot1: tr1 = {0}", transportTime1);
        this._transportTime1 = transportTime1;
    }
    
    /**
     * Sets the time to pick and transport a gear
     * @param transportTime2 New time in ms
     */
    public void setTransportTime2(int transportTime2) {
        Logger.getLogger(Robot1.class.toString()).log(Level.INFO, "Robot1: tr2 = {0}", transportTime2);
        this._transportTime2 = transportTime2;
    }
    
    /**
     * Sets the time to pick and transport an assembly
     * @param transportTime3 New time in ms
     */
    public void setTransportTime3(int transportTime3) {
        Logger.getLogger(Robot1.class.toString()).log(Level.INFO, "Robot1: tr3 = {0}", transportTime3);
        this._transportTime3 = transportTime3;
    }
    
    /**
     * Sends a command through the IO Interface
     * @param command New command to send
     */
    public void sendCommand(int command) {
        System.out.println("R1 :" + command);
        ioi.send((short) command);
    }
    
    /**
     * Starts the server to listen through the IO Interface
     */
    public void startServer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Sets the slave to send commands
     * @param _slave New slave receiver
     */
    public void setSlave(Slave1 _slave) {
        this._slave = _slave;
    }
}
