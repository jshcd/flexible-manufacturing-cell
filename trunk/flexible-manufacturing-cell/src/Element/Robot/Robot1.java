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

public class Robot1 implements Robot, Runnable, IOProcess {

    private AutomatonState _state;
    private Piece _loadedPiece;
    private int _transportTime1; // When the robot has moved the gear to the assembly table, the robot 1 waits until the sensor 2 is active.
    private int _transportTime2;
    private int _transportTime3;
    private boolean _gearSensor;
    private boolean _axisSensor;
    private boolean _assemblySensor;
    private boolean _weldingSensor;
    private boolean _assemblyCompleted;
    private IOInterface ioi;

    public Logger _logger = Logger.getLogger(Robot1.class.toString());

    public Robot1() {
        _state = AutomatonState.q0;
        _gearSensor = false;
        _axisSensor = false;
        _assemblySensor = false;
        _weldingSensor = false;
        _assemblyCompleted = false;
        ioi = new IOInterface();
        ioi.setProcess(this);
        ioi.setPortLag(3);
        ioi.bind();
        (new Thread(ioi)).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                _logger.log(Level.SEVERE, null, ex);
            }
            switch (_state) {
                case q0:
                    if (_gearSensor) {
                        pickGear();
                        _state = AutomatonState.q1;
                    } else if (_axisSensor) {
                        pickAxis();
                        _state = AutomatonState.q2;
                    }
                    break;
                case q1:
                    transportGear();
                    _state = AutomatonState.q3;
                    break;
                case q2:
                    transportAxis();
                    _state = AutomatonState.q4;
                    break;
                case q3:
                    if (_axisSensor) {
                        pickAxis();
                        _state = AutomatonState.q5;
                    }
                    break;
                case q4:
                    if (_gearSensor) {
                        pickGear();
                        _state = AutomatonState.q6;
                    }
                    break;
                case q5:
                    transportAxis();
                    _state = AutomatonState.q7;
                    break;
                case q6:
                    transportGear();
                    _state = AutomatonState.q7;
                    break;
                case q7:
                    this.sendCommand(Constants.ROBOT1_SLAVE1_REQUEST_ASSEMBLY);
                    if (_assemblyCompleted) {
                        pickAssembly();
                        _state = AutomatonState.q8;
                    }
                    break;
                case q8:
                    if (!_weldingSensor) {
                        transportAssembly();
                        _state = AutomatonState.q9;
                    }
                    break;
                case q9:
                    returnToIdle();
            }

            Thread.yield();
        }
    }

    public void runCommand(int command) {
        switch (command) {
            case Constants.SENSOR_GEAR_UNLOAD_ACTIVATED:
                _gearSensor = true;
                break;
            case Constants.SENSOR_GEAR_UNLOAD_DISACTIVATED:
                _gearSensor = false;
                break;
            case Constants.SENSOR_AXIS_UNLOAD_ACTIVATED:
                _axisSensor = true;
                break;
            case Constants.SENSOR_AXIS_UNLOAD_DISACTIVATED:
                _axisSensor = false;
                break;
            case Constants.SENSOR_ASSEMBLY_ACTIVATED:
                _assemblySensor = true;
                break;
            case Constants.SENSOR_ASSEMBLY_DISACTIVATED:
                _assemblySensor = false;
                break;
            case Constants.SENSOR_WELDING_LOAD_ACTIVATED:
                _weldingSensor = true;
                break;
            case Constants.SENSOR_WELDING_LOAD_DISACTIVATED:
                _weldingSensor = false;
                break;
            case Constants.SLAVE1_ROBOT1_ASSEMBLY_COMPLETED:
                _assemblyCompleted = true;
                break;
        }
    }

    public void pickAxis() {
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.axis);
        _axisSensor = false;
        sendCommand(Constants.ROBOT1_SLAVE1_PICKS_AXIS);
        _logger.log(Level.INFO, "Robot1 picks axis");

    }

    public void pickGear() {
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.gear);
        _gearSensor = false;
        sendCommand(Constants.ROBOT1_SLAVE1_PICKS_GEAR);
       _logger.log(Level.INFO, "Robot1 picks gear");
    }

    public void pickAssembly() {
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.assembly);
        _assemblyCompleted = false;
        sendCommand(Constants.ROBOT1_SLAVE1_PICKS_ASSEMBLY);
       _logger.log(Level.INFO, "Robot1 picks assembly from assembly station");
    }

    public void transportGear() {
        try {
            Thread.sleep(_transportTime1);
        } catch (InterruptedException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
        sendCommand(Constants.ROBOT1_SLAVE1_PLACES_GEAR);
        _logger.log(Level.INFO, "Robot1 places gear on assembly station");
        _loadedPiece = null;
    }

    public void transportAxis() {
        try {
            Thread.sleep(_transportTime2);
        } catch (InterruptedException ex) {
           _logger.log(Level.SEVERE, null, ex);
        }
        sendCommand(Constants.ROBOT1_SLAVE1_PLACES_AXIS);
       _logger.log(Level.INFO, "Robot1 places axis on assembly station");
        _loadedPiece = null;
    }

    public void transportAssembly() {
        try {
            Thread.sleep(_transportTime3);
        } catch (InterruptedException ex) {
           _logger.log(Level.SEVERE, null, ex);
        }
        sendCommand(Constants.ROBOT1_SLAVE1_PLACES_ASSEMBLY);
       _logger.log(Level.INFO, "Robot1 places assembly on welding belt");
        _loadedPiece = null;
    }

    public void returnToIdle() {
        try {
            switch (_state) {
                case q0:
                    break;
                case q9:
                    Thread.sleep(_transportTime3);
                case q8:
                case q7:
                    Thread.sleep(_transportTime2);
                case q6:
                case q5:
                case q4:
                case q3:
                case q2:
                case q1:
                    _state = AutomatonState.q0;
            }
        } catch (InterruptedException ex) {
            _logger.log(Level.SEVERE, null, ex);
        }
    }

    public void setTrasportTime1(int transportTime1) {
        _logger.log(Level.INFO, "Robot1: tr1 = {0}", transportTime1);
        this._transportTime1 = transportTime1;
    }

    public void setTransportTime2(int transportTime2) {
       _logger.log(Level.INFO, "Robot1: tr2 = {0}", transportTime2);
        this._transportTime2 = transportTime2;
    }

    public void setTransportTime3(int transportTime3) {
        _logger.log(Level.INFO, "Robot1: tr3 = {0}", transportTime3);
        this._transportTime3 = transportTime3;
    }
    
    public void sendCommand(int command) {
        System.out.println("R1 :" + command);
        ioi.send((short)command);
    }

    public void startServer() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//IODevices.properties");
            prop.load(is);
            int port = Integer.parseInt(prop.getProperty("Robot1.port"));
            ServerSocket skServidor = new ServerSocket(port);
          _logger.log(Level.INFO, "Server listening at port {0}", port);
            while (true) {
                Socket skCliente = skServidor.accept();
                _logger.log(Level.INFO, "Information received");
                ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                _logger.log(Level.INFO, "Received> {0}", in.readObject());

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
    

}