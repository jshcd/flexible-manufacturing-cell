package Element.Robot;

import Automaton.Master.Master;
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
 * This class implements the methods for the Robot2 functionality
 * @author Echoplex
 */
public class Robot2 implements Robot, Runnable, IOProcess {

    /**
     * Master automaton instance
     */
    private Master _master;
    /**
     * Automaton state
     */
    private AutomatonState _state;
    /**
     * Automaton previous state
     */
    private AutomatonState _previousState;
    /**
     * Counts the number of times that the robot has not changed its status
     */
    private int _stateUnchanged;
    /**
     * Represents the instance of the loaded piece
     */
    private Piece _loadedPiece;
    /**
     * Welding Belt Sensor status
     */
    private boolean _weldingSensor;
    /**
     * Welding Station Sensor status
     */
    private boolean _weldingTableSensor;
    /**
     * Quality Station Sensor status
     */
    private boolean _qualityTableSensor;
    /**
     * Sensor of the Accepted Belt status
     */
    private boolean _OKTableSensor;
    /**
     * Rejected Belt Sensor status
     */
    private boolean _NotOKTableSensor;
    /**
     * Shows if the welding process has finished
     */
    private boolean _weldingCompleted;
    /**
     * Shows if the quality process has finished Ok
     */
    private boolean _qualityCompletedOK;
    /**
     * Shows if the quality process has finished Not Ok
     */
    private boolean _qualityCompletedNotOK;
    /**
     * Shows if a command has been received
     */
    private boolean _commandReceived;
    /**
     * Time to pick and transport an assembly
     */
    private int _transportTime4;
    /**
     * Time to pick and transport a welded assembly
     */
    private int _transportTime5;
    /**
     * Time to pick and transport a checked assembly
     */
    private int _transportTime6;
    /**
     * Instance of IOInterface
     */
    IOInterface ioi;

    /**
     * Class constructor
     */
    public Robot2() {
        try {
            _state = AutomatonState.q0;
            _previousState = AutomatonState.q0;
            _stateUnchanged = 0;
            _weldingSensor = false;
            _qualityTableSensor = false;
            _weldingCompleted = false;
            _qualityCompletedOK = false;
            _qualityCompletedNotOK = false;
            _OKTableSensor = false;
            _NotOKTableSensor = false;

            _commandReceived = false;

            ioi = new IOInterface();
            ioi.setProcess(this);
            ioi.setPortLag(4);
            ioi.bind();
            (new Thread(ioi)).start();

            Thread.sleep(4000);
            restoreState();
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Run method that stablish actions for the different states
     */
    @Override
    public void run() {

        while (true) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
            }

            _master.updateRobot(_state, _loadedPiece);

            if (_stateUnchanged > 50) {
                restoreState();
                _stateUnchanged = 0;
            }

            if (_state.equals(_previousState)) {
                _stateUnchanged++;
            } else {
                _stateUnchanged = 0;
            }

            _previousState = _state;

            switch (_state) {
                case q0:
                    if (_weldingSensor) {
                        _state = AutomatonState.q1;
                        pickAssembly(); // sends 208
                    }
                    break;
                case q1:
                    if (!_commandReceived) {
                        _commandReceived = true;
                        _state = AutomatonState.q0;
                        break;
                    }
                    if (!_weldingTableSensor) {
                        _commandReceived = false;
                        transportAssembly(); // sends 301,303
                        _state = AutomatonState.q2;
                    }
                    break;
                case q2:
                    if (!_commandReceived) {
                        _commandReceived = true;
                        _state = AutomatonState.q1;
                        break;
                    }
                    if (_weldingCompleted) {
                        _commandReceived = false;
                        if (_loadedPiece == null) {
                            pickWeldedAssembly(); // 305
                        }
                        _state = AutomatonState.q3;
                    }
                    break;
                case q3:
                    if (!_commandReceived) {
                        _commandReceived = true;
                        _state = AutomatonState.q2;
                        break;
                    }
                    if (!_qualityTableSensor) {
                        _commandReceived = false;
                        transportWeldedAssembly(); // 306 304
                        _state = AutomatonState.q4;
                    }
                    break;
                case q4:
                    if (!_commandReceived) {
                        _commandReceived = true;
                        _state = AutomatonState.q3;
                        break;
                    }
                    if (_qualityCompletedOK) {
                        _commandReceived = false;
                        if (_loadedPiece == null) {
                            pickCheckedWeldedAssembly(true); //310
                        }
                        _state = AutomatonState.q6;
                    } else if (_qualityCompletedNotOK) {
                        _commandReceived = false;
                        if (_loadedPiece == null) {
                            pickCheckedWeldedAssembly(false);//310
                        }
                        _state = AutomatonState.q7;
                    }
                    break;
                case q6:
                    if (!_commandReceived) {
                        _commandReceived = true;
                        _state = AutomatonState.q4;
                        break;
                    }
                    if (!_OKTableSensor) {
                        transportWeldedOK(); //401
                        _previousState = AutomatonState.q6;
                        _state = AutomatonState.q8;
                    }
                    break;
                case q7:
                    if (!_commandReceived) {
                        _commandReceived = true;
                        _state = AutomatonState.q4;
                        break;
                    }
                    if (!_NotOKTableSensor) {
                        transportWeldedNotOK(); //402
                        _previousState = AutomatonState.q7;
                        _state = AutomatonState.q8;
                    }
                    break;
                case q8:
                    returnToIdle();
            }
            Thread.yield();
        }
    }

    /**
     * Picks an assembly from the Welding Belt
     */
    public void pickAssembly() {
        try {
            Thread.sleep(_transportTime4 / 3);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.assembly);
        sendCommand(Constants.ROBOT2_SLAVE1_PICKS_ASSEMBLY);
        Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Robot2 picks asembly from welding belt");
    }

    /**
     * Tramsports an assembly to the Welding Station
     */
    public void transportAssembly() {
        try {
            Thread.sleep(_transportTime4 / 3 * 2);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendCommand(Constants.ROBOT2_SLAVE2_PLACES_ASSEMBLY);
        sendCommand(Constants.ROBOT2_SLAVE2_REQUEST_WELDING);
        _loadedPiece = null;
        Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Robot2 places assembly on welding station");
    }

    /**
     * Picks an assembly from the welding station
     */
    public void pickWeldedAssembly() {
        try {
            Thread.sleep(_transportTime5 / 3);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.weldedAssembly);
        _weldingCompleted = false;
        sendCommand(Constants.ROBOT2_SLAVE2_PICKS_WELDED_ASSEMBLY);
        Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Robot2 picks welded assembly from welding station");
    }

    /**
     * Tramsport a welded assembly to the quality station
     */
    public void transportWeldedAssembly() {
        try {
            Thread.sleep(_transportTime5 / 3 * 2);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendCommand(Constants.ROBOT2_SLAVE2_PLACES_WELDED_ASSEMBLY);
        sendCommand(Constants.ROBOT2_SLAVE2_REQUEST_QUALITY);
        Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Robot2 places welded assembly in quality station");
        _loadedPiece = null;
    }

    /**
     * Picks an assembly from the quality station
     * @param ok Status of the piece checked
     */
    public void pickCheckedWeldedAssembly(boolean ok) {
        try {
            Thread.sleep(_transportTime6 / 3);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        _loadedPiece = new Piece();
        if (ok) {
            _loadedPiece.setType(Piece.PieceType.weldedAssemblyOk);
        } else {
            _loadedPiece.setType(Piece.PieceType.weldedAssemblyNotOk);
        }
        _weldingCompleted = false;
        sendCommand(Constants.ROBOT2_SLAVE2_PICKS_CHECKED_WELDED_ASSEMBLY);
        Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Robot2 picks welded assembly from quality station");
    }

    /**
     * Tramsports the checked piece to the Ok Belt
     */
    public void transportWeldedOK() {
        try {
            Thread.sleep(_transportTime6 / 3 * 2);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendCommand(Constants.ROBOT2_SLAVE3_PLACES_WELDED_OK);
        Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Robot2 places accepted welded assembly");
        _loadedPiece = null;
        _qualityCompletedOK = false;
    }

    /**
     * Tramsports the checked piece to the Not Ok belt
     */
    public void transportWeldedNotOK() {
        try {
            Thread.sleep(_transportTime6 / 3 * 2);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendCommand(Constants.ROBOT2_SLAVE3_PLACES_WELDED_NOT_OK);
        Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Robot2 places rejected welded assembly");
        _loadedPiece = null;
        _qualityCompletedNotOK = false;
    }

    /**
     * Sends a command through the IO Interface
     * @param command New command to send
     * @see Constants
     */
    public void sendCommand(int command) {
        ioi.send((short) command);
    }

    /**
     * Runs a command and performs the different changes in the robot state
     * @param command New command to execute
     */
    public void runCommand(int command) {
        switch (command) {
            case Constants.SENSOR_WELDING_UNLOAD_ACTIVATED:
                _weldingSensor = true;
                break;
            case Constants.SENSOR_WELDING_UNLOAD_DISACTIVATED:
                _weldingSensor = false;
                break;
            case Constants.SENSOR_WELDING_TABLE_ACTIVATED:
                _weldingTableSensor = true;
                break;
            case Constants.SENSOR_WELDING_TABLE_DISACTIVATED:
                _weldingTableSensor = false;
                break;
            case Constants.SENSOR_QUALITY_ACTIVATED:
                _qualityTableSensor = true;
                break;
            case Constants.SENSOR_QUALITY_DISACTIVATED:
                _qualityTableSensor = false;
                break;
            case Constants.SLAVE2_ROBOT2_WELDED_ASSEMBLY_COMPLETED:
                _weldingCompleted = true;
                break;
            case Constants.SLAVE2_ROBOT2_QUALITY_CONTROL_COMPLETED_OK:
                _qualityCompletedOK = true;
                break;
            case Constants.SLAVE2_ROBOT2_QUALITY_CONTROL_COMPLETED_NOT_OK:
                _qualityCompletedNotOK = true;
                break;
            case Constants.SENSOR_OK_LOAD_ACTIVATED:
                _OKTableSensor = true;
                break;
            case Constants.SENSOR_OK_LOAD_DISACTIVATED:
                _OKTableSensor = false;
                break;
            case Constants.SENSOR_NOT_OK_LOAD_ACTIVATED:
                _NotOKTableSensor = true;
                break;
            case Constants.SENSOR_NOT_OK_LOAD_DISACTIVATED:
                _NotOKTableSensor = false;
                break;
            case Constants.SLAVE1_ROBOT2_ASSEMBLY_PICKED:
                _commandReceived = true;
                break;
            case Constants.SLAVE2_ROBOT2_ASSEMBLY_PLACED:
                _commandReceived = true;
                break;
            case Constants.SLAVE2_ROBOT2_WELDED_ASSEMBLY_PICKED:
                _commandReceived = true;
                break;
            case Constants.SLAVE2_ROBOT2_WELDED_ASSEMBLY_PLACED:
                _commandReceived = true;
                break;
            case Constants.SLAVE3_ROBOT2_WELDED_ASSEMBLY_PLACED:
                _commandReceived = true;
                break;
            case Constants.SLAVE2_ROBOT2_CHECKED_WELDED_ASSEMBLY_PICKED:
                _commandReceived = true;
                break;
        }
    }

    /**
     * @return The piece that has been loaded
     */
    public Piece getLoadedPiece() {
        return _loadedPiece;
    }

    /**
     * Starts listening through the IOInterface
     */
    public void startServer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns the Robot to the Idle state
     */
    private void returnToIdle() {
        _commandReceived = false;
        _qualityCompletedOK = false;
        _qualityCompletedNotOK = false;
        try {
            switch (_state) {
                case q0:
                    break;
                case q8:
                case q7:
                case q6:
                    Thread.sleep(_transportTime6);
                case q5:
                case q4:
                    Thread.sleep(_transportTime5);
                case q3:
                case q2:
                case q1:
                    Thread.sleep(_transportTime4);
                    _state = AutomatonState.q0;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets the time to pick and transport an assembly
     * @param _transportTime4 
     */
    public void setTransportTime4(int _transportTime4) {
        this._transportTime4 = _transportTime4;
    }

    /**
     * Sets the time to pick and transport a welded asesmbly
     * @param _transportTime5 
     */
    public void setTransportTime5(int _transportTime5) {
        this._transportTime5 = _transportTime5;
    }

    /**
     * Sets the time to pick and transport a checked assembly
     * @param _transportTime6 
     */
    public void setTransportTime6(int _transportTime6) {
        this._transportTime6 = _transportTime6;
    }

    /**
     * Sertores the state after performing an action
     */
    private void restoreState() {
        _commandReceived = true;
        if (_loadedPiece == null) {
            if (this._qualityCompletedNotOK) {
                _state = AutomatonState.q4;
            } else if (this._qualityCompletedOK) {
                _state = AutomatonState.q4;
            } else if (this._weldingCompleted) {
                _state = AutomatonState.q2;
            } else if (this._weldingSensor) {
                _state = AutomatonState.q0;
            }
        } else {
            if (_loadedPiece.equals(Piece.PieceType.assembly)) {
                _state = AutomatonState.q1;
            } else if (_loadedPiece.equals(Piece.PieceType.weldedAssembly)) {
                _state = AutomatonState.q3;
            } else if (_loadedPiece.equals(Piece.PieceType.weldedAssemblyOk)) {
                _state = AutomatonState.q6;
            } else if (_loadedPiece.equals(Piece.PieceType.weldedAssemblyNotOk)) {
                _state = AutomatonState.q7;
            }
        }
    }

    /**
     * Sets the master instance
     * @param _master MasterAutomaton
     */
    public void setMaster(Master _master) {
        this._master = _master;
    }
}