
package Automaton.Slaves.Data;

import Auxiliar.MailboxData;
import Element.Piece.Piece;
import java.util.List;

/**
 * Defines the general data that the Slave2 sends to the Master 
 * in order to update the GUI
 * @author Echoplex
 */
public class Slave2Data extends MailboxData {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Status of the welding station
     */
    private boolean _weldingStationRunning;
    
    /**
     * Status of the quality station
     */
    private boolean _qualityStationRunning;
    
    /**
     * Sensor 6 status
     */
    private boolean _sensor6Status;
    
    /**
     * Sensor7 status
     */
    private boolean _sensor7Status;
    
    /**
     * List of pieces of the welding station
     */
    private List<Piece> _weldingStationPieces;
    
    /**
     * List of pieces of the welding station
     */
    private List<Piece> _qualityStationPieces;

    /**
     * Constructor of the class
     * @param _weldingStationRunning Status of the welding station
     * @param _qualityStationRunning Status of the quality control station
     * @param _sensor6Status Sensor6 status
     * @param _sensor7Status Sensor7 status
     * @param _weldingStationPieces List of pieces of the welding station
     * @param _qualityStationPIeces List of pieces of the quality control station
     */
    public Slave2Data(boolean _weldingStationRunning, boolean _qualityStationRunning, 
            boolean _sensor6Status, boolean _sensor7Status, List<Piece> _weldingStationPieces, 
            List<Piece> _qualityStationPIeces) {
        this._weldingStationRunning = _weldingStationRunning;
        this._qualityStationRunning = _qualityStationRunning;
        this._sensor6Status = _sensor6Status;
        this._sensor7Status = _sensor7Status;
        this._weldingStationPieces = _weldingStationPieces;
        this._qualityStationPieces = _qualityStationPIeces;
    }
    
    /**
     * Secondary constructor of the class
     */
    public Slave2Data(){}

    /**
     * @return The list of pieces contained by the quality control station
     */
    public List<Piece> getQualityStationPieces() {
        return _qualityStationPieces;
    }

    /**
     * Sets the list of pieces in the quality control station
     * @param _qualityStationPieces 
     */
    public void setQualityStationPieces(List<Piece> _qualityStationPieces) {
        this._qualityStationPieces = _qualityStationPieces;
    }

    /**
     * @return The status of the quality control station
     */
    public boolean isQualityStationRunning() {
        return _qualityStationRunning;
    }

    /**
     * Sets the status of the quality control station
     * @param _qualityStationRunning 
     */
    public void setQualityStationRunning(boolean _qualityStationRunning) {
        this._qualityStationRunning = _qualityStationRunning;
    }

    /**
     * @return Senso6 status
     */
    public boolean isSensor6Status() {
        return _sensor6Status;
    }

    /**
     * Sets the senso6 status
     * @param _sensor6Status 
     */
    public void setSensor6Status(boolean _sensor6Status) {
        this._sensor6Status = _sensor6Status;
    }

    /**
     * @return Sensor7 status
     */
    public boolean isSensor7Status() {
        return _sensor7Status;
    }

    /**
     * Sets the Sensor7 status
     * @param _sensor7Status 
     */
    public void setSensor7Status(boolean _sensor7Status) {
        this._sensor7Status = _sensor7Status;
    }

    /**
     * @return List of pieces contained by the welding station
     */
    public List<Piece> getWeldingStationPieces() {
        return _weldingStationPieces;
    }

    /**
     * Sets the pieces contained in the welding station
     * @param _weldingStationPieces 
     */
    public void setWeldingStationPieces(List<Piece> _weldingStationPieces) {
        this._weldingStationPieces = _weldingStationPieces;
    }

    /**
     * @return Welding station status
     */
    public boolean isWeldingStationRunning() {
        return _weldingStationRunning;
    }

    /**
     * Sets the welding station status
     * @param _weldingStationRunning 
     */
    public void setWeldingStationRunning(boolean _weldingStationRunning) {
        this._weldingStationRunning = _weldingStationRunning;
    }
}
