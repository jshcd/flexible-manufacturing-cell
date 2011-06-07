/* Slave 3 code */
package Automaton.Slaves;

import Automaton.Slaves.Data.Slave3Data;
import Automaton.Slaves.Slave;
import Automaton.Slaves.Slave;
import Automaton.Slaves.SlaveOutputMailBox;
import Automaton.Slaves.SlaveOutputMailBox;
import Auxiliar.Command;
import Auxiliar.Constants;
import Auxiliar.IOInterface;
import Auxiliar.IOProcess;
import Auxiliar.MailboxData;
import Element.Station.QualityControlStation;
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
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Slave3 implements Slave, IOProcess {

    private SlaveOutputMailBox _outputMailBox;
    protected SlaveInputMailBox _inputMailBox;
    private ConveyorBelt _acceptedBelt;
    private ConveyorBelt _rejectedBelt;
    private Sensor _sensor8;
    private Sensor _sensor9;
    private Sensor _sensor10;
    private Sensor _sensor11;
    private Slave3Data _statusData;
    private IOInterface ioi;
    private Slave3ConfigurationData _slave3ConfigurationData;
    private double sensor_range;
    private boolean _stopped;
    protected Logger _logger = Logger.getLogger(Slave3.class.toString());

    public static void main(String args[]) {
        Slave3 s3 = new Slave3();
    }

    public Slave3() {
        _logger.log(Level.INFO, "Slave 3 created");
        _outputMailBox = new SlaveOutputMailBox(3);
        _inputMailBox = new SlaveInputMailBox(3, this);
        Thread t = new Thread(new Runnable() {

            public void run() {
                _inputMailBox.startServer();
            }
        });
        t.start();
        reportToMaster(new Command(Constants.COMMAND_SLAVE3_CONNECTED));
    }

    public Sensor getSensor8() {
        return _sensor8;
    }

    public Sensor getSensor9() {
        return _sensor9;
    }

    public Sensor getSensor10() {
        return _sensor10;
    }

    public Sensor getSensor11() {
        return _sensor11;
    }

    public ConveyorBelt getAcceptedBelt() {
        return _acceptedBelt;
    }

    public ConveyorBelt getRejectedBelt() {
        return _rejectedBelt;
    }

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
        reportToMaster(_statusData);
    }

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
                        if(!_stopped) updateStatusData();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        y.start();
    }

    public void start() {
        _stopped = false;
        _acceptedBelt.startContainer();
        _rejectedBelt.startContainer();
        reportToMaster(new Command(Constants.SLAVE_THREE_STARTING));
    }

    public void stop() {
        _stopped = true;
        _acceptedBelt.stopContainer();
        _rejectedBelt.stopContainer();
        reportToMaster(new Command(Constants.SLAVE_THREE_STOPPING));
    }

    public void runCommand(int command) {
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
                sendCommand(Constants.SLAVE3_ROBOT2_WELDED_ASSEMBLY_PLACED);
                break;
            case Constants.ROBOT2_SLAVE3_PLACES_WELDED_NOT_OK:
                p = new Piece();
                p.setType(PieceType.weldedAssembly);
                sendCommand(Constants.SLAVE3_ROBOT2_WELDED_ASSEMBLY_PLACED);
                _rejectedBelt.addPiece(p);
                break;
        }
        if (!_stopped) {
            switch (command) {
                case Constants.SENSOR_OK_UNLOAD_ACTIVATED:
                    _acceptedBelt.stopContainer();
                    break;
                case Constants.SENSOR_OK_UNLOAD_DISACTIVATED:
                    _acceptedBelt.startContainer();
                    break;
                case Constants.SENSOR_NOT_OK_UNLOAD_ACTIVATED:
                    _rejectedBelt.stopContainer();
                    break;
                case Constants.SENSOR_NOT_OK_UNLOAD_DISACTIVATED:
                    _rejectedBelt.startContainer();
                    break;
            }
        }
    }

    public void reportToMaster(MailboxData data) {
        _outputMailBox.startConnection();
        _outputMailBox.acceptConnection();
        _outputMailBox.sendCommand(data);
        _outputMailBox.receiveCommand();
    }

    public void sendCommand(int command) {
        ioi.send((short) command);
    }

    public void startServer() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Slave3.port"));
            ServerSocket skServidor = new ServerSocket(port);
            _logger.log(Level.INFO, "Server listening at port {0}", port);
            while (true) {
                Socket skCliente = skServidor.accept();
                _logger.log(Level.INFO, "Information received");
                ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                _logger.log(Level.INFO, "Received> {0}", Short.parseShort((String) in.readObject()));

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

    public void storeInitialConfiguration(MasterConfigurationData md) {
        _slave3ConfigurationData = md._slave3ConfigurationData;
        sensor_range = (double) md._sensorRange;
        initialize();
    }
}