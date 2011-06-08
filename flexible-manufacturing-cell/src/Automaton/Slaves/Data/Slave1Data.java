/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Automaton.Slaves.Data;

import Auxiliar.MailboxData;
import Element.Piece.Piece;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author David
 */
public class Slave1Data extends MailboxData implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private boolean _gearBeltRunning;
    private boolean _axisBeltRunning;
    private boolean _weldingBeltRunning;
    private boolean _assemblyStationRunning;
    private boolean _sensor1Status;
    private boolean _sensor2Status;
    private boolean _sensor3Status;
    private boolean _sensor4Status;
    private boolean _sensor5Status;
    private List<Piece> _gearBeltPieces;
    private List<Piece> _axisBeltPieces;
    private List<Piece> _weldingBeltPieces;
    private List<Piece> _assemblyStationPieces;

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
    
    public Slave1Data(){}

    public List<Piece> getAssemblyStationPieces() {
        return _assemblyStationPieces;
    }

    public void setAssemblyStationPieces(List<Piece> _assemblyStationPieces) {
        this._assemblyStationPieces = _assemblyStationPieces;
    }

    public boolean isAssemblyStationRunning() {
        return _assemblyStationRunning;
    }

    public void setAssemblyStationRunning(boolean _assemblyStationRunning) {
        this._assemblyStationRunning = _assemblyStationRunning;
    }

    public List<Piece> getAxisBeltPieces() {
        return _axisBeltPieces;
    }

    public void setAxisBeltPieces(List<Piece> _axisBeltPieces) {
        this._axisBeltPieces = _axisBeltPieces;
    }

    public boolean isAxisBeltRunning() {
        return _axisBeltRunning;
    }

    public void setAxisBeltRunning(boolean _axisBeltRunning) {
        this._axisBeltRunning = _axisBeltRunning;
    }

    public List<Piece> getGearBeltPieces() {
        return _gearBeltPieces;
    }

    public void setGearBeltPieces(List<Piece> _gearBeltPieces) {
        this._gearBeltPieces = _gearBeltPieces;
    }

    public boolean isGearBeltRunning() {
        return _gearBeltRunning;
    }

    public void setGearBeltRunning(boolean _gearBeltRunning) {
        this._gearBeltRunning = _gearBeltRunning;
    }

    public boolean isSensor1Status() {
        return _sensor1Status;
    }

    public void setSensor1Status(boolean _sensor1Status) {
        this._sensor1Status = _sensor1Status;
    }

    public boolean isSensor2Status() {
        return _sensor2Status;
    }

    public void setSensor2Status(boolean _sensor2Status) {
        this._sensor2Status = _sensor2Status;
    }

    public boolean isSensor3Status() {
        return _sensor3Status;
    }

    public void setSensor3Status(boolean _sensor3Status) {
        this._sensor3Status = _sensor3Status;
    }

    public boolean isSensor4Status() {
        return _sensor4Status;
    }

    public void setSensor4Status(boolean _sensor4Status) {
        this._sensor4Status = _sensor4Status;
    }

    public boolean isSensor5Status() {
        return _sensor5Status;
    }

    public void setSensor5Status(boolean _sensor5Status) {
        this._sensor5Status = _sensor5Status;
    }

    public List<Piece> getWeldingBeltPieces() {
        return _weldingBeltPieces;
    }

    public void setWeldingBeltPieces(List<Piece> _weldingBeltPieces) {
        this._weldingBeltPieces = _weldingBeltPieces;
    }

    public boolean isWeldingBeltRunning() {
        return _weldingBeltRunning;
    }

    public void setWeldingBeltRunning(boolean _weldingBeltRunning) {
        this._weldingBeltRunning = _weldingBeltRunning;
    }
}
