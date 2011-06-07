package Element.Robot;

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

public class Robot2 implements Robot, Runnable, IOProcess {

    private RobotOutputMailBox _mailBox;
    private AutomatonState _state;
    private AutomatonState _previousState;
    private Piece _loadedPiece;
    private boolean _weldingSensor;
    private boolean _weldingTableSensor;
    private boolean _qualityTableSensor;
    private boolean _OKTableSensor;
    private boolean _NotOKTableSensor;
    private boolean _weldingCompleted;
    private boolean _qualityCompletedOK;
    private boolean _qualityCompletedNotOK;
    private boolean _commandReceived;
    private int _transportTime4;
    private int _transportTime5;
    private int _transportTime6;
    IOInterface ioi;

    public Robot2() {
        _state = AutomatonState.q0;
        _previousState = AutomatonState.q0;
        _mailBox = new RobotOutputMailBox(2);
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
    }

    @Override
    public void run() {

        while (true) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
            }
            
//            System.out.println(_state);

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
                        pickWeldedAssembly(); // 305
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
                        pickCheckedWeldedAssembly(); //310
                        _state = AutomatonState.q7;
                    } else if (_qualityCompletedNotOK) {
                        _commandReceived = false;
                        pickCheckedWeldedAssembly();//310
                        _state = AutomatonState.q6;
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

    public void pickAssembly() {
        try {
            Thread.sleep(_transportTime4/3);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.assembly);
        sendCommand(Constants.ROBOT2_SLAVE1_PICKS_ASSEMBLY);
        Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Robot2 picks asembly from welding belt");
    }

    public void transportAssembly() {
        try {
            Thread.sleep(_transportTime4/3*2);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendCommand(Constants.ROBOT2_SLAVE2_PLACES_ASSEMBLY);
        sendCommand(Constants.ROBOT2_SLAVE2_REQUEST_WELDING);
        _loadedPiece = null;
        Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Robot2 places assembly on welding station");
    }

    public void pickWeldedAssembly() {
        try {
            Thread.sleep(_transportTime5/3);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.weldedAssembly);
        _weldingCompleted = false;
        sendCommand(Constants.ROBOT2_SLAVE2_PICKS_WELDED_ASSEMBLY);
        Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Robot2 picks welded assembly from welding station");
    }

    public void transportWeldedAssembly() {
        try {
            Thread.sleep(_transportTime5/3*2);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendCommand(Constants.ROBOT2_SLAVE2_PLACES_WELDED_ASSEMBLY);
        sendCommand(Constants.ROBOT2_SLAVE2_REQUEST_QUALITY);
        Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Robot2 places welded assembly in quality station");
        _loadedPiece = null;
    }

    public void pickCheckedWeldedAssembly() {
        try {
            Thread.sleep(_transportTime6/3);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.weldedAssembly);
        _weldingCompleted = false;
        sendCommand(Constants.ROBOT2_SLAVE2_PICKS_CHECKED_WELDED_ASSEMBLY);
        Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Robot2 picks welded assembly from quality station");
    }

    public void transportWeldedOK() {
        try {
            Thread.sleep(_transportTime6/3*2);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendCommand(Constants.ROBOT2_SLAVE3_PLACES_WELDED_OK);
        Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Robot2 places accepted welded assembly");
        _loadedPiece = null;
        _qualityCompletedOK = false;
    }

    public void transportWeldedNotOK() {
        try {
            Thread.sleep(_transportTime6/3*2);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendCommand(Constants.ROBOT2_SLAVE3_PLACES_WELDED_NOT_OK);
        Logger.getLogger(Robot2.class.getName()).log(Level.INFO, "Robot2 places rejected welded assembly");
        _loadedPiece = null;
        _qualityCompletedNotOK = false;
    }

    public void sendCommand(int command) {
//        System.out.println("R2 sending: " + command);
        ioi.send((short) command);
    }

    public void runCommand(int command) {
        if (command > 120) {
            System.out.println("R2 running: " + command);
        }
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

    public void setTransportTime4(int _transportTime4) {
        this._transportTime4 = _transportTime4;
    }

    public void setTransportTime5(int _transportTime5) {
        this._transportTime5 = _transportTime5;
    }

    public void setTransportTime6(int _transportTime6) {
        this._transportTime6 = _transportTime6;
    }
}