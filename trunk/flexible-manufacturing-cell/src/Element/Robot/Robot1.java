package Element.Robot;

import Auxiliar.AutomatonState;
import Element.Piece.Piece;
import Element.Other.Sensor;
import Automaton.Slaves.Slave1;
import Automaton.Slaves.Slave2;
import Auxiliar.Constants;
import Element.Conveyor.ConveyorBelt;
import Element.Station.AssemblyStation;
import Scada.DataBase.DBManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Robot1 extends Thread implements Robot {

    private RobotMailBox _mailBox;
    private AutomatonState _state;
    private Piece _loadedPiece;
    private int _pickingTime;
    private int _transportTime1; // When the robot has moved the gear to the assembly table, the robot 1 waits until the sensor 2 is active.
    private int _transportTime2; // When the robot has moved the ax to the assembly table, the robot 1 waits until the sensor 1 is active.
    private boolean _gearSensor;
    private boolean _axisSensor;
    private boolean _assemblySensor;
    private boolean _weldingSensor;

    public Robot1() {
        _state = AutomatonState.q0;
        _mailBox = new RobotMailBox(1);
        _gearSensor = false;
        _axisSensor = false;
        _assemblySensor = false;
        _weldingSensor = false;
    }

    @Override
    public void start() {
        while (true) {
            switch (_state) {
                // TODO: rellenar transiciones
                case q0:
                    if (_gearSensor) {
                        pickGear();
                        _state = AutomatonState.q1;
                    } else if (_axisSensor) {
                        pickAxis();
                        _state = AutomatonState.q2;
                    }
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
                    _state = AutomatonState.q8;
                    break;
                //TODO: preguntar por la transicion t1
                case q7:
                case q8:
            }
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
        }
    }

    public void pickAxis() {
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.axis);
    }

    public void pickGear() {
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.gear);
    }
    
    public void transportGear() {
        try {
            Thread.sleep(_pickingTime);
            Thread.sleep(_transportTime1);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        reportProcess(Constants.ROBOT1_PLACES_GEAR);
        _loadedPiece = null;
    }
    
    public void transportAxis() {
        try {
            Thread.sleep(_pickingTime);
            Thread.sleep(_transportTime2);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot1.class.getName()).log(Level.SEVERE, null, ex);
        }
        reportProcess(Constants.ROBOT1_PLACES_AXIS);
        _loadedPiece = null;
    }
    
    public void transportAssembly() {
        throw new UnsupportedOperationException();
    }

    public Piece getLoadedPiece() {
        return this._loadedPiece;
    }

    public void setLoadedPiece(Piece loadedPiece) {
        _loadedPiece = loadedPiece;
    }

    public int getPickingTime() {
        return _pickingTime;
    }

    public void setPickingTime(int pickingTime) {
        this._pickingTime = pickingTime;
    }

    public int getTransportTime1() {
        return _transportTime1;
    }

    public void setTrasportTime1(int transportTime1) {
        this._transportTime1 = transportTime1;
    }

    public int getTransportTime2() {
        return _transportTime2;
    }

    public void setTransportTime2(int transportTime2) {
        this._transportTime2 = transportTime2;
    }

    public void reportProcess(int command) {
        throw new UnsupportedOperationException();
    }
}