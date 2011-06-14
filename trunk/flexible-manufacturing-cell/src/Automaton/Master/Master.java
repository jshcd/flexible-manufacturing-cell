package Automaton.Master;

import Automaton.Slaves.Data.Slave3Data;
import Auxiliar.AutomatonState;
import Auxiliar.Command;
import Auxiliar.Constants;
import Auxiliar.MailboxData;
import Element.Piece.Piece;
import Scada.DataBase.DBManager;
import Element.Robot.Robot2;
import Scada.DataBase.MasterConfigurationData;
import Scada.DataBase.ReportData;
import Scada.Gui.MonitorWindow;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the Master automaton, that controls the slaves in order to get the manufacturing to work
 * @author Echoplex
 */
public class Master {

    /**
     * Mailbox where the master receives information
     */
    private MasterInputMailBox _inputMailBox;
    /**
     * Mailbox where the master sends information
     */
    private MasterOutputMailBox _outputMailBox;
    /**
     * DataBase manager that controls SCADA
     */
    private DBManager _dbmanager;
    /**
     * Configuration data for the master
     */
    private MasterConfigurationData _configurationData;
    /**
     * Robot controlled by the master
     */
    private Robot2 _robot;
    /**
     * Window where the system is displayed
     */
    private MonitorWindow _monitor;
    /**
     * Information to show in the reports
     */
    private ReportData _reportData;
    /**
     * Logger
     */
    protected static final Logger _logger = Logger.getLogger(Master.class.toString());
    /**
     * slave 1 state
     */
    boolean _slave1Online = false;
    /**
     * slave 2 state
     */
    boolean _slave2Online = false;
    /**
     * slave 3 state
     */
    boolean _slave3Online = false;

    /**
     * Constructs the master objects and initializes its components
     * @param m Monitor window where the system is displayed
     */
    public Master(MonitorWindow m) {
        _logger.addHandler(m.getLog().getLogHandler());
        _outputMailBox = new MasterOutputMailBox();
        _dbmanager = new DBManager();
        _configurationData = null;
        _monitor = m;
        _reportData = new ReportData();
        _reportData._firstStart = true;
        _robot = new Robot2();
        _robot.setMaster(this);
        _inputMailBox = new MasterInputMailBox(this);
        Thread t = new Thread(new Runnable() {

            public void run() {
                _inputMailBox.startServer();
            }
        });
        t.start();
    }

    /**
     * Initialize the configurable parameters reading them from the Data Base
     */
    public void initialize() {
        _configurationData = _dbmanager.readParameters();
        _robot.setTransportTime4(_configurationData._robot2ConfigurationData.getPickAndTransportAssemblyTime());
        _robot.setTransportTime5(_configurationData._robot2ConfigurationData.getPickAndTransportWeldedAssemblyTime());
        _robot.setTransportTime6(_configurationData._robot2ConfigurationData.getPickAndTransportCheckedAssemblyTime());

    }

    /**
     * Sets the status of the slave
     * @param slaveId Slave identifier
     * @param status Whether the slave is connected or not
     */
    public void setConnectionStatus(int slaveId, boolean status) {
        switch (slaveId) {
            case Constants.SLAVE1_ID:
                _slave1Online = status;
                _monitor.setConnectionStatus(Constants.SLAVE1_ID, status);
                break;
            case Constants.SLAVE2_ID:
                _slave2Online = status;
                _monitor.setConnectionStatus(Constants.SLAVE2_ID, status);
                break;
            case Constants.SLAVE3_ID:
                _slave3Online = status;
                _monitor.setConnectionStatus(Constants.SLAVE3_ID, status);
                break;
        }
        _outputMailBox.sendInformation(_configurationData, slaveId);
    }

    /**
     * Updates the status of the canvas components
     * @param slaveId Slave identifier
     * @param data Data to be updated
     */
    public void setCanvasStatus(int slaveId, MailboxData data) {
        if (slaveId == Constants.SLAVE3_ID) {
            Slave3Data d = ((Slave3Data) data);
            _reportData._rightPiecesCurrentExec = d.getRightPieces();
            _reportData._wrongPiecesCurrentExec = d.getWrongPieces();
            _reportData._rightPiecesAllExec = d.getAllRightPieces();
            _reportData._wrongPiecesAllExec = d.getAllWrongPieces();
            _dbmanager.writeReportData(_reportData);
        }
        _monitor.setCanvasStatus(slaveId, data);
    }

    /**
     * Starts the robot 2 controlled by the master
     */
    public void startRobot() {
        (new Thread(_robot)).start();

    }

    /**
     * Starts the normal execution of the system
     */
    public void startSystem() {
        _logger.log(Level.INFO, "System started");
        if (_reportData._firstStart) {
            _reportData._firstStart = false;
            _reportData._rightPiecesAllExec = 0;
            _reportData._wrongPiecesAllExec = 0;
            _reportData._rightPiecesCurrentExec = 0;
            _reportData._wrongPiecesCurrentExec = 0;
            _reportData._nRestarts = 0;
            _reportData._nEmergencyStops = 0;
            _reportData._nNormalStops = 0;
        } else {
            _reportData._nRestarts++;
        }
        _dbmanager.writeReportData(_reportData);
        initialize();
        _outputMailBox.sendInformation(_configurationData, Constants.SLAVE1_ID);
        _outputMailBox.sendInformation(_configurationData, Constants.SLAVE2_ID);
        _outputMailBox.sendInformation(_configurationData, Constants.SLAVE3_ID);
        Command command1 = new Command(Constants.START_SLAVE1);
        _outputMailBox.sendInformation(command1, Constants.SLAVE1_ID);
        Command command2 = new Command(Constants.START_SLAVE2);
        _outputMailBox.sendInformation(command2, Constants.SLAVE2_ID);
        Command command3 = new Command(Constants.START_SLAVE3);
        _outputMailBox.sendInformation(command3, Constants.SLAVE3_ID);
        _robot.sendCommand(Constants.START_ROBOT2);

    }

    /**
     * Stops the generation of axis and gears and finishes the manufacturing of the pieces that are in the belts
     */
    public void stopSystem() {
        _reportData._nNormalStops++;
        _dbmanager.writeReportData(_reportData);
        Command command = new Command(Constants.NORMAL_STOP_ORDER);
        _outputMailBox.sendInformation(command, Constants.SLAVE1_ID);
        _outputMailBox.sendInformation(command, Constants.SLAVE2_ID);
        _outputMailBox.sendInformation(command, Constants.SLAVE3_ID);
        _logger.log(Level.INFO, "Normal Stop");
    }

    /**
     * Stops immediately the system and clean all the belts
     */
    public void emergencyStop() {
        _reportData._nEmergencyStops++;
        _dbmanager.writeReportData(_reportData);
        Command command = new Command(Constants.EMERGENCY_STOP_ORDER);
        _outputMailBox.sendInformation(command, Constants.SLAVE1_ID);
        _outputMailBox.sendInformation(command, Constants.SLAVE2_ID);
        _outputMailBox.sendInformation(command, Constants.SLAVE3_ID);
        _robot.sendCommand(Constants.EMERGENCY_STOP_ORDER);
        _logger.log(Level.INFO, "Emergency Stop");
    }

    /**
     * Gets the Data base manager
     * @return Data base manager
     */
    public DBManager getDbmanager() {
        return _dbmanager;
    }

    /**
     * Sets the data base manager
     * @param _dbmanager
     */
    public void setDbmanager(DBManager _dbmanager) {
        this._dbmanager = _dbmanager;
    }

    /**
     * Gets the input mailbox
     * @return MasterInputMailBox
     */
    public MasterInputMailBox getMailBox() {
        return _inputMailBox;
    }

    /**
     * Sets the MasterInputMailBox
     * @param _mailBox
     */
    public void setMailBox(MasterInputMailBox _mailBox) {
        this._inputMailBox = _mailBox;
    }

    /**
     * Gets the robot 2
     * @return Robot2
     */
    public Robot2 getRobot2() {
        return _robot;
    }

    /**
     * Sets the robot 2
     * @param _robot2
     */
    public void setRobot2(Robot2 _robot2) {
        this._robot = _robot2;
    }

    /**
     * Gets the report data
     * @return ReportData
     */
    public ReportData getReportData() {
        return _reportData;
    }

    /**
     * Sets the report data
     * @param _reportData
     */
    public void setReportData(ReportData _reportData) {
        this._reportData = _reportData;
    }

    /**
     * Gets the window where the system is displayed
     * @return MonitorWindow
     */
    public MonitorWindow getMonitor() {
        return _monitor;
    }

    /**
     * Sets the window where the system is displayed
     * @param _monitor
     */
    public void setMonitor(MonitorWindow _monitor) {
        this._monitor = _monitor;
    }

    /**
     * Gets the master configuration data
     * @return MasterConfigurationData
     */
    public MasterConfigurationData getConfigurationData() {
        return _configurationData;
    }

    /**
     * Updates the robot 2 status
     * @param automatonState state of the autoomaton
     * @param piece Piece to be updated
     */
    public void updateRobot(AutomatonState automatonState, Piece piece) {
        _monitor.updateRobot2(automatonState, piece);
    }
}
