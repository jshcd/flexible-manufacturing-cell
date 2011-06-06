package Automaton.Master;

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

    private MasterInputMailBox _mailBox;
    private DBManager _dbmanager;
    private MasterConfigurationData _configurationData;
    private Robot2 _robot;
    private MonitorWindow _monitor;
    private ConfigurationParameters _configurationParameters;
    private ReportData _report;
    protected Logger _logger = Logger.getLogger(Master.class.toString());
    boolean _slave1Online = false;
    boolean _slave2Online = false;
    boolean _slave3Online = false;

    // TODO: hay que borrarlo pero se usa para pruebas
    public static void main(String args[]) {
        Master m = new Master();
        m.initialize();
        m.startRobot();
    }

    public Master() {
        _mailBox = new MasterInputMailBox(this);
        _dbmanager = new DBManager();
        _configurationData = null;
        _monitor = new MonitorWindow(this);
        _report = _dbmanager.readReportData();
        _robot = new Robot2();
        _report.setFirstStart(true);
        _logger.addHandler(_monitor.getLog().getLogHandler());
        Thread t = new Thread(new Runnable() {
            public void run() {
                _mailBox.startServer();
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
                _monitor.setConnectionStatus(Constants.SLAVE2_ID, status);
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
        throw new UnsupportedOperationException();
    }

    public void stopSystem() {
        throw new UnsupportedOperationException();
    }

    public void emergencyStop() {
        throw new UnsupportedOperationException();
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
        return _mailBox;
    }

    public void setMailBox(MasterInputMailBox _mailBox) {
        this._mailBox = _mailBox;
    }

    public Robot2 getRobot2() {
        return _robot;
    }

    public void setRobot2(Robot2 _robot2) {
        this._robot = _robot2;
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
