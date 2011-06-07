package Automaton.Master;

import Auxiliar.Command;
import Auxiliar.Constants;
import Auxiliar.MailboxData;
import Scada.DataBase.DBManager;
import Scada.Gui.Canvas;
import Element.Robot.Robot2;
import Scada.DataBase.MasterConfigurationData;
import Scada.DataBase.ReportData;
import Scada.Gui.ConfigurationParameters;
import Scada.Gui.ImageLoader;
import Scada.Gui.MonitorWindow;
import Scada.Gui.Report;
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

public class Master {

    private MasterInputMailBox _inputMailBox;
    private MasterOutputMailBox _outputMailBox;
    private DBManager _dbmanager;
    private MasterConfigurationData _configurationData;
    private Robot2 _robot;
    private MonitorWindow _monitor;
    private ConfigurationParameters _configurationParameters;
    private ReportData _reportData;
    protected Logger _logger = Logger.getLogger(Master.class.toString());
    boolean _slave1Online = false;
    boolean _slave2Online = false;
    boolean _slave3Online = false;

    // TODO: hay que borrarlo pero se usa para pruebas
    public static void main(String args[]) {
        Master m = new Master(null);
        m.initialize();
        m.startRobot();
    }

    public Master(MonitorWindow m) {
        _logger.addHandler(m.getLog().getLogHandler());
        _outputMailBox = new MasterOutputMailBox();
        _dbmanager = new DBManager();
        _configurationData = null;
        _monitor = m;
        _reportData = _dbmanager.readReportData();
        _reportData._firstStart = true;
        _robot = new Robot2();
        _inputMailBox = new MasterInputMailBox(this);
        _logger.log(Level.INFO, "prueba");
        Thread t = new Thread(new Runnable() {

            public void run() {
                _inputMailBox.startServer();
            }
        });
        t.start();
    }

    public void initialize() {
        _configurationData = _dbmanager.readParameters();
        _robot.setTransportTime4(_configurationData._robot2ConfigurationData.getPickAndTransportAssemblyTime());
        _robot.setTransportTime5(_configurationData._robot2ConfigurationData.getPickAndTransportWeldedAssemblyTime());
        _robot.setTransportTime6(_configurationData._robot2ConfigurationData.getPickAndTransportCheckedAssemblyTime());

    }

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

    public void setCanvasStatus(int slaveId, MailboxData data) {
        _monitor.setCanvasStatus(slaveId, data);
    }

    public void startRobot() {
        Thread t = new Thread(new Runnable() {

            public void run() {
                _robot.startServer();
            }
        });
        t.start();
        (new Thread(_robot)).start();

    }

    public void startSystem() {
        if (_reportData._firstStart) {
            _reportData._firstStart = false;
            _reportData._rightPiecesCurrentExec = 0;
            _reportData._wrongPiecesCurrentExec = 0;
        } else {
            _reportData._nRestarts++;
        }

        Command command1 = new Command(Constants.START_SLAVE1);
        _outputMailBox.sendInformation(command1, Constants.SLAVE1_ID);
        Command command2 = new Command(Constants.START_SLAVE2);
        _outputMailBox.sendInformation(command2, Constants.SLAVE2_ID);
        Command command3 = new Command(Constants.START_SLAVE3);
        _outputMailBox.sendInformation(command3, Constants.SLAVE3_ID);


    }

    public void stopSystem() {
        _reportData._nNormalStops++;
        _dbmanager.writeReportData(_reportData);
        Command command = new Command(Constants.NORMAL_STOP_ORDER);
        _outputMailBox.sendInformation(command, Constants.SLAVE1_ID);
        _outputMailBox.sendInformation(command, Constants.SLAVE2_ID);
        _outputMailBox.sendInformation(command, Constants.SLAVE3_ID);
    }

    public void emergencyStop() {
         _reportData._nEmergencyStops++;
        _dbmanager.writeReportData(_reportData);
        Command command = new Command(Constants.EMERGENCY_STOP_ORDER);
        _outputMailBox.sendInformation(command, Constants.SLAVE1_ID);
        _outputMailBox.sendInformation(command, Constants.SLAVE2_ID);
        _outputMailBox.sendInformation(command, Constants.SLAVE3_ID);
    }

    public void failureStop() {
        throw new UnsupportedOperationException();
    }

    public void runCommand(int command) {
        throw new UnsupportedOperationException();
    }

    public void loadParameters() {
        throw new UnsupportedOperationException();
    }

    public DBManager getDbmanager() {
        return _dbmanager;
    }

    public void setDbmanager(DBManager _dbmanager) {
        this._dbmanager = _dbmanager;
    }

    public MasterInputMailBox getMailBox() {
        return _inputMailBox;
    }

    public void setMailBox(MasterInputMailBox _mailBox) {
        this._inputMailBox = _mailBox;
    }

    public Robot2 getRobot2() {
        return _robot;
    }

    public void setRobot2(Robot2 _robot2) {
        this._robot = _robot2;
    }

    public ReportData getReportData() {
        return _reportData;
    }

    public void setReportData(ReportData _reportData) {
        this._reportData = _reportData;
    }

    public MonitorWindow getMonitor() {
        return _monitor;
    }

    public void setMonitor(MonitorWindow _monitor) {
        this._monitor = _monitor;
    }

    public MasterConfigurationData getConfigurationData() {
        return _configurationData;
    }
}
