
package Automaton.Slaves.Data;

import Auxiliar.AutomatonState;
import Auxiliar.MailboxData;
import Element.Piece.Piece;
import java.util.List;

/**
 * Defines the general data that the Slave1 sends to the Master 
 * in order to update the GUI
 * @author Echoplex
 */
public class Slave1Data extends MailboxData {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Shows if the Gear Belt is running
     */
    private boolean _gearBeltRunning;
    
    /**
     * Shows if the Axis Belt is running
     */
    private boolean _axisBeltRunning;
    
    /**
     * Shows if the Welding Belt is running
     */
    private boolean _weldingBeltRunning;
    
    /**
     * Shows if the Assembly Station is Running
     */
    private boolean _assemblyStationRunning;
    
    /**
     * Shows if the sensor1 is activated
     */
    private boolean _sensor1Status;
    
    /**
     * Shows if the sensor2 is activated
     */
    private boolean _sensor2Status;
    
    /**
     * Shows if the sensor3 is activated
     */
    private boolean _sensor3Status;
    
    /**
     * Shows if the sensor4 is activated
     */
    private boolean _sensor4Status;
    
    /**
     * Shows if the sensor5 is activated
     */
    private boolean _sensor5Status;
    
    /**
     * List of pieces contained in the Gear Belt
     */
    private List<Piece> _gearBeltPieces;
    
    /**
     * List of pieces contained in the Axis Belt
     */
    private List<Piece> _axisBeltPieces;
    
    /**
     * List of pieces contained in the Welding Belt
     */
    private List<Piece> _weldingBeltPieces;
    
    /**
     * List of pieces contained in the Assembly Station
     */
    private List<Piece> _assemblyStationPieces;
    
    /**
     * Status of the Robot1
     */
    private AutomatonState _R1state;
    
    /**
     * Piece loaded by the Robot1
     */
    private Piece _R1loadedPiece;
    
    /**
     * Constructor of the class
     * @param _gearBeltRunning if the Gear Belt is running
     * @param _axisBeltRunning if the Axis Belt is running
     * @param _weldingBeltRunning if the Welding Belt is running
     * @param _assemblyStationRunning if the Assembly Station is running
     * @param _sensor1Status status of the sensor1
     * @param _sensor2Status status of the sensor2
     * @param _sensor3Status status of the sensor3
     * @param _sensor4Status status of the sensor4
     * @param _sensor5Status status of the sensor5
     * @param _gearBeltPieces List of pieces contained in the Gear Belt
     * @param _axisBeltPieces List of pieces contained in the Axis Belt
     * @param _weldingBeltPieces List of pieces contained in the Welding Belt
     * @param _assemblyStationPieces List of pieces contained in the Assembly Station
     */
    public Slave1Data(boolean _gearBeltRunning, boolean _axisBeltRunning, 
            boolean _weldingBeltRunning, boolean _assemblyStationRunning, boolean _sensor1Status, 
            boolean _sensor2Status, boolean _sensor3Status, boolean _sensor4Status, 
            boolean _sensor5Status, List<Piece> _gearBeltPieces, List<Piece> _axisBeltPieces, 
            List<Piece> _weldingBeltPieces, List<Piece> _assemblyStationPieces) {
        this._gearBeltRunning = _gearBeltRunning;
        this._axisBeltRunning = _axisBeltRunning;
        this._weldingBeltRunning = _weldingBeltRunning;
        this._assemblyStationRunning = _assemblyStationRunning;
        this._sensor1Status = _sensor1Status;
        this._sensor2Status = _sensor2Status;
        this._sensor3Status = _sensor3Status;
        this._sensor4Status = _sensor4Status;
        this._sensor5Status = _sensor5Status;
        this._gearBeltPieces = _gearBeltPieces;
        this._axisBeltPieces = _axisBeltPieces;
        this._weldingBeltPieces = _weldingBeltPieces;
        this._assemblyStationPieces = _assemblyStationPieces;
    }
    
    /**
     * Secondary Constructor
     */
    public Slave1Data(){}

    /**
     * @return The list of pieces contained in the Assembly Station
     */
    public List<Piece> getAssemblyStationPieces() {
        return _assemblyStationPieces;
    }

    /**
     * Sets the list of pieces contained in the Assembly Station
     * @param _assemblyStationPieces 
     */
    public void setAssemblyStationPieces(List<Piece> _assemblyStationPieces) {
        this._assemblyStationPieces = _assemblyStationPieces;
    }

    /**
     * @return TRUE if the AssemblyStation is Running, FALSE if not
     */
    public boolean isAssemblyStationRunning() {
        return _assemblyStationRunning;
    }

    /**
     * Sets the status of the assembly station
     * @param _assemblyStationRunning Assembly station is running or not
     */
    public void setAssemblyStationRunning(boolean _assemblyStationRunning) {
        this._assemblyStationRunning = _assemblyStationRunning;
    }

    /**
     * @return List of pieces contained in the Axis Belt
     */
    public List<Piece> getAxisBeltPieces() {
        return _axisBeltPieces;
    }

    /**
     * Sets the list of pieces contained in the Axis Belt
     * @param _axisBeltPieces 
     */
    public void setAxisBeltPieces(List<Piece> _axisBeltPieces) {
        this._axisBeltPieces = _axisBeltPieces;
    }

    /**
     * @return TRUE if the Axis Belt is running, FALSE in other case
     */
    public boolean isAxisBeltRunning() {
        return _axisBeltRunning;
    }

    /**
     * Sets the status of the Axis Belt
     * @param _axisBeltRunning Belt is running or not
     */
    public void setAxisBeltRunning(boolean _axisBeltRunning) {
        this._axisBeltRunning = _axisBeltRunning;
    }

    /**
     * @return List of pieces of the Gear Belt
     */
    public List<Piece> getGearBeltPieces() {
        return _gearBeltPieces;
    }

    /**
     * Sets the list of pieces contained in the gear belt
     * @param _gearBeltPieces 
     */
    public void setGearBeltPieces(List<Piece> _gearBeltPieces) {
        this._gearBeltPieces = _gearBeltPieces;
    }

    /**
     * @return TRUE if the Gear Belt is Running, FALSE in other case
     */
    public boolean isGearBeltRunning() {
        return _gearBeltRunning;
    }

    /**
     * Sets the status of the Gear Belt
     * @param _gearBeltRunning if the belt is running or not
     */
    public void setGearBeltRunning(boolean _gearBeltRunning) {
        this._gearBeltRunning = _gearBeltRunning;
    }

    /**
     * @return The status of the sensor1
     */
    public boolean isSensor1Status() {
        return _sensor1Status;
    }

    /**
     * Sets the status of the sensor1
     * @param _sensor1Status Sensor activated or not
     */
    public void setSensor1Status(boolean _sensor1Status) {
        this._sensor1Status = _sensor1Status;
    }

    /**
     * @return The status of the sensor2
     */
    public boolean isSensor2Status() {
        return _sensor2Status;
    }

    /**
     * Sets the status of the sensor2
     * @param _sensor2Status  Sensor activated or not
     */
    public void setSensor2Status(boolean _sensor2Status) {
        this._sensor2Status = _sensor2Status;
    }

    /**
     * @return The status of the sensor3
     */
    public boolean isSensor3Status() {
        return _sensor3Status;
    }

    /**
     * Sets the status of the sensor3
     * @param _sensor3Status  Sensor activated or not
     */
    public void setSensor3Status(boolean _sensor3Status) {
        this._sensor3Status = _sensor3Status;
    }

    /**
     * @return The status of the sensor4
     */
    public boolean isSensor4Status() {
        return _sensor4Status;
    }

    /**
     * Sets the status of the sensor4
     * @param _sensor4Status  Sensor activated or not
     */
    public void setSensor4Status(boolean _sensor4Status) {
        this._sensor4Status = _sensor4Status;
    }

    /**
     * @return The status of the sensor5
     */
    public boolean isSensor5Status() {
        return _sensor5Status;
    }

    /**
     * Sets the status of the sensor5
     * @param _sensor5Status Sensor activated or not
     */
    public void setSensor5Status(boolean _sensor5Status) {
        this._sensor5Status = _sensor5Status;
    }

    /**
     * @return List of pieces contained in the Welding Belt
     */
    public List<Piece> getWeldingBeltPieces() {
        return _weldingBeltPieces;
    }

    /**
     * Sets the list of pieces contained in the Weldingin bElt
     * @param _weldingBeltPieces 
     */
    public void setWeldingBeltPieces(List<Piece> _weldingBeltPieces) {
        this._weldingBeltPieces = _weldingBeltPieces;
    }

    /**
     * @return The status of the Welding Belt
     */
    public boolean isWeldingBeltRunning() {
        return _weldingBeltRunning;
    }

    /**
     * Sets the status of the welding belt
     * @param _weldingBeltRunning 
     */
    public void setWeldingBeltRunning(boolean _weldingBeltRunning) {
        this._weldingBeltRunning = _weldingBeltRunning;
    }

    /**
     * @return Returns the piece that the robot2 has loaded
     */
    public Piece getR1loadedPiece() {
        return _R1loadedPiece;
    }

    /**
     * Sets the piece that the robot2 has loaded
     * @param _R1loadedPiece 
     */
    public void setR1loadedPiece(Piece _R1loadedPiece) {
        this._R1loadedPiece = _R1loadedPiece;
    }

    /**
     * @return The status of the Robot1
     */
    public AutomatonState getR1state() {
        return _R1state;
    }

    /**
     * Sets the status of the Robot1
     * @param _R1state 
     */
    public void setR1state(AutomatonState _R1state) {
        this._R1state = _R1state;
    }
}
