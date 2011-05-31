package Element.Robot;

import Auxiliar.AutomatonState;
import Element.Piece.Piece;
import Auxiliar.Constants;
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

public class Robot2 extends Thread implements Robot {

    private RobotMailBox _mailBox;
    private AutomatonState _state;
    private Piece _loadedPiece;
    private boolean _weldingSensor;
    private boolean _weldingTableSensor;
    private boolean _qualityTableSensor;
    private boolean _OKTableSensor;
    private boolean _NotOKTableSensor;
    private boolean _weldingCompleted;
    private boolean _qualityCompletedOK;
    private boolean _qualityCompletedNotOK;
    private int _transportTime4;
    private int _transportTime5;
    private int _transportTime6;

    public Robot2() {
        _state = AutomatonState.q0;
        _mailBox = new RobotMailBox(2);
        _weldingSensor = false;
        _qualityTableSensor = false;
        _weldingCompleted = false;
        _qualityCompletedOK = false;
        _qualityCompletedNotOK = false;
        _OKTableSensor = false;
        _NotOKTableSensor = false;
    }

    @Override
    public void start() {
        while (true) {
            switch (_state) {
                case q0:
                    if (_weldingSensor) {
                        _state = AutomatonState.q1;
                        pickAssembly();
                    }
                case q1:
                    if (!_weldingTableSensor) {
                        transportAssembly();
                        _state = AutomatonState.q2;
                    }
                    break;
                case q2:
                    if (_weldingCompleted) {
                        pickWeldedAssembly();
                        _state = AutomatonState.q3;
                    }
                    break;
                case q3:
                    if (!_qualityTableSensor) {
                        transportWeldedAssembly();
                        _state = AutomatonState.q4;
                    }
                    break;
                case q4:
                    pickCheckedWeldedAssembly();
                    if (_qualityCompletedOK) {
                        _state = AutomatonState.q7;
                    } else if (_qualityCompletedNotOK) {
                        _state = AutomatonState.q6;
                    }
                    break;
                case q6:
                    if (!_OKTableSensor) {
                        transportWeldedOK();
                        returnToIdle();
                    }
                    break;
                case q7:
                    if (!_NotOKTableSensor) {
                        transportWeldedNotOK();
                        returnToIdle();
                    }
                    break;
            }
        }
    }

    public void pickAssembly() {
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.assembly);
        reportProcess(Constants.SLAVE1_ROBOT2_PICKS_ASSEMBLY);
    }

    public void transportAssembly() {
        try {
            Thread.sleep(_transportTime4);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        reportProcess(Constants.SLAVE2_ROBOT2_PLACES_ASSEMBLY);
        reportProcess(Constants.SLAVE2_ROBOT2_REQUEST_WELDING);
        _loadedPiece = null;
    }

    public void pickWeldedAssembly() {
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.weldedAssembly);
        _weldingCompleted = false;
        reportProcess(Constants.SLAVE2_ROBOT2_PICKS_WELDED_ASSEMBLY);
    }

    public void transportWeldedAssembly() {
        try {
            Thread.sleep(_transportTime5);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        reportProcess(Constants.SLAVE3_ROBOT2_PLACES_WELDED_ASSEMBLY);
        reportProcess(Constants.SLAVE2_ROBOT2_REQUEST_QUALITY);
        _loadedPiece = null;
    }

    public void pickCheckedWeldedAssembly() {
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.weldedAssembly);
        _weldingCompleted = false;
        reportProcess(Constants.SLAVE3_ROBOT2_PICKS_CHECKED_WELDED_ASSEMBLY);
    }

    public void transportWeldedOK() {
        try {
            Thread.sleep(_transportTime6);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        reportProcess(Constants.SLAVE3_ROBOT2_PLACES_WELDED_OK);
        _loadedPiece = null;
        _qualityCompletedOK = false;
    }

    public void transportWeldedNotOK() {
        try {
            Thread.sleep(_transportTime6);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        reportProcess(Constants.SLAVE3_ROBOT2_PLACES_WELDED_NOT_OK);
        _loadedPiece = null;
        _qualityCompletedNotOK = false;
    }

    public void reportProcess(int command) {
        throw new UnsupportedOperationException();
    }

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
            case Constants.SLAVE2_WELDED_ASSEMBLY_COMPLETED:
                _weldingCompleted = true;
                break;
            case Constants.SLAVE3_QUALITY_CONTROL_COMPLETED_OK:
                _qualityCompletedOK = true;
                break;
            case Constants.SLAVE3_QUALITY_CONTROL_COMPLETED_NOT_OK:
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
        }
    }

    public Piece getLoadedPiece() {
        return _loadedPiece;
    }

    public void startServer() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Robot2.port"));
            ServerSocket skServidor = new ServerSocket(port);
            Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Server listening at port {0}", port);
            while (true) {
                Socket skCliente = skServidor.accept();
                Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Information received");
                ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Received> {0}", Short.parseShort((String) in.readObject()));

                short a = (short) 0;
                out.writeObject(a);
                skCliente.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Robot2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Robot2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Robot2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void returnToIdle() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}