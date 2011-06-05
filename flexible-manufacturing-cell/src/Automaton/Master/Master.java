package Automaton.Master;

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
    private Robot2 _robot2;
    private MonitorWindow _monitor;
    private ConfigurationParameters _configurationParameters;
    private ReportData _report;
    protected Logger _logger = Logger.getLogger(Master.class.toString());

    public static void main(String[] args) {
         Master m = new Master();
    }

    public Master() {
        _mailBox = new MasterInputMailBox();
        _dbmanager = new DBManager();
        _configurationData = null;
        _robot2 = new Robot2();
        _monitor = new MonitorWindow(this);
        _report = _dbmanager.readReportData();
	_report.setFirstStart(true);
        _logger.addHandler(_monitor.getLog().getLogHandler());
      

    }

    public void initialize() {
        _configurationData = _dbmanager.readParameters();
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
        return _robot2;
    }

    public void setRobot2(Robot2 _robot2) {
        this._robot2 = _robot2;
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
