package Automaton.Master;

import Scada.DataBase.DBManager;
import Scada.Gui.Canvas;
import Element.Robot.Robot2;
import Scada.DataBase.MasterConfigurationData;
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

    private MasterMailBox _mailBox;
    private DBManager _dbmanager;
    private MasterConfigurationData _configurationData;
    //   private Canvas _canvas;
    private Robot2 _robot2;
    // private ImageLoader _imageLoader;
    private MonitorWindow _monitor;
    private ConfigurationParameters _configurationParameters;
    private Report _report;
    protected Logger _logger = Logger.getLogger(Master.class.toString());

    public static void main(String[] args) {
        // Master m = new Master();
    }

    public Master() {
        _mailBox = new MasterMailBox();
        _dbmanager = new DBManager();
        _configurationData = null;
        _robot2 = new Robot2();
        _monitor = new MonitorWindow(this);
        // _logger.addHandler(monitor.getLog().getLogHandler());
        //TODO: _report = _dbmanager. leer Datos Informe

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

    public MasterMailBox getMailBox() {
        return _mailBox;
    }

    public void setMailBox(MasterMailBox _mailBox) {
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

    public void startServer() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Master.port"));
            ServerSocket skServidor = new ServerSocket(port);
            Logger.getLogger(Master.class.getName()).log(Level.INFO, "Server listening at port {0}", port);
            while (true) {
                Socket skCliente = skServidor.accept();
                Logger.getLogger(Master.class.getName()).log(Level.INFO, "Information received");
                ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                Logger.getLogger(Master.class.getName()).log(Level.INFO, "Received> {0}", (String) in.readObject());
                // _logger.getLogger(Master.class.getName()).log(Level.INFO, "Received> {0}", (String) in.readObject());

                int a = 0;
                out.writeObject(a);
                skCliente.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
