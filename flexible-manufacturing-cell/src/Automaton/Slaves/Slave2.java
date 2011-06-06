/* Slave 2 code */
package Automaton.Slaves;

import Automaton.Slaves.Data.Slave2Data;
import Automaton.Slaves.Slave;
import Automaton.Slaves.Slave;
import Automaton.Slaves.SlaveOutputMailBox;
import Automaton.Slaves.SlaveOutputMailBox;
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
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Slave2 implements Slave, IOProcess {

    private SlaveOutputMailBox _outputMailBox;
    protected SlaveInputMailBox _inputMailBox;
    private WeldingStation _weldingStation;
    private QualityControlStation _qualityStation;
    private Sensor _sensor6;
    private Sensor _sensor7;
    private Slave2Data _statusData;
    IOInterface ioi;
    private Slave2ConfigurationData _slave2ConfigurationData;
    private double sensor_range;

    public static void main(String args[]) {
        Slave2 s2 = new Slave2();
    }

    public Slave2() {
        Logger.getLogger(Slave2.class.getName()).log(Level.INFO, "Slave 2 created");
        _outputMailBox = new SlaveOutputMailBox(2);
        _inputMailBox = new SlaveInputMailBox(2, this);
        Thread t = new Thread(new Runnable() {
            public void run() {
                _inputMailBox.startServer();
            }
        });
        t.start();
        reportToMaster(new Command(Constants.COMMAND_SLAVE2_CONNECTED));
    }

    public WeldingStation getWeldingStation() {
        return _weldingStation;
    }

    public Sensor getSensor6() {
        return _sensor6;
    }

    public Sensor getSensor7() {
        return _sensor7;
    }

    public void updateStatusData() {
        _statusData = new Slave2Data();
        _statusData.setSensor6Status(_sensor6.isActivated());
        _statusData.setSensor7Status(_sensor7.isActivated());
        _statusData.setQualityStationPieces(_qualityStation.getPieces());
        _statusData.setQualityStationRunning(_qualityStation.isMoving());
        _statusData.setWeldingStationPieces(_weldingStation.getPieces());
        _statusData.setWeldingStationRunning(_weldingStation.isMoving());
        reportToMaster(_statusData);
    }

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
    }

    public void start() {
        _weldingStation.startContainer();
        _qualityStation.startContainer();
        reportToMaster(new Command(Constants.SLAVE_TWO_STARTING));
    }

    public void stop() {
        _weldingStation.stopContainer();
        _qualityStation.stopContainer();
        reportToMaster(new Command(Constants.SLAVE_TWO_STOPPING));
    }

    public void runCommand(int command) {
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
                break;
            case Constants.ROBOT2_SLAVE2_PICKS_WELDED_ASSEMBLY:
                _weldingStation.removeLastPiece();
                sendCommand(Constants.SLAVE2_ROBOT2_WELDED_ASSEMBLY_PICKED);
                break;
            case Constants.ROBOT2_SLAVE2_PLACES_WELDED_ASSEMBLY:
                p = new Piece();
                p.setType(PieceType.weldedAssembly);
                _qualityStation.addPiece(p);
                sendCommand(Constants.SLAVE2_ROBOT2_WELDED_ASSEMBLY_PLACED);
                break;
            case Constants.ROBOT2_SLAVE2_PICKS_CHECKED_WELDED_ASSEMBLY:
                _qualityStation.removeLastPiece();
                sendCommand(Constants.SLAVE2_ROBOT2_CHECKED_WELDED_ASSEMBLY_PICKED);
                break;
        }
    }

    public void sendCommand(int command) {
        ioi.send((short) command);
    }

    public void reportToMaster(MailboxData data) {
        _outputMailBox.startConnection();
        _outputMailBox.acceptConnection();
        _outputMailBox.sendCommand(data);
        _outputMailBox.receiveCommand();
    }

    public void startServer() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Slave2.port"));
            ServerSocket skServidor = new ServerSocket(port);
            Logger.getLogger(Slave2.class.getName()).log(Level.INFO, "Server listening at port {0}", port);
            while (true) {
                Socket skCliente = skServidor.accept();
                Logger.getLogger(Slave2.class.getName()).log(Level.INFO, "Information received");
                ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                Logger.getLogger(Slave2.class.getName()).log(Level.INFO, "Received> {0}", Short.parseShort((String) in.readObject()));

                short a = (short) 0;
                out.writeObject(a);
                skCliente.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Slave2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Slave2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Slave2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void storeInitialConfiguration(MasterConfigurationData md) {
        _slave2ConfigurationData = md._slave2ConfigurationData;
        sensor_range = (double) md._sensorRange;
        initialize();
    }
}