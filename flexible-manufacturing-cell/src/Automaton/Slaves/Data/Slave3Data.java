
package Automaton.Slaves.Data;

import Auxiliar.MailboxData;
import Element.Piece.Piece;
import java.util.List;

/**
 * Defines the general data that the Slave3 sends to the Master 
 * in order to update the GUI
 * @author Echoplex
 */
public class Slave3Data extends MailboxData {

    private static final long serialVersionUID = 1L;
    
    /**
     * Status of the accepted belt
     */
    private boolean _acceptedBeltRunning;
    
    /**
     * Status of the rejected belt
     */
    private boolean _rejectedBeltRunning;
    
    /**
     * Sensor8 status
     */
    private boolean _sensor8Status;
    
    /**
     * Sensor9 status
     */
    private boolean _sensor9Status;
    
    /**
     * Sensor10 status
     */
    private boolean _sensor10Status;
    
    /**
     * Sensor11 status
     */
    private boolean _sensor11Status;
    
    /**
     * List of pieces contained in the accepted belt
     */
    private List<Piece> _acceptedBeltPieces;
    
    /**
     * List of pieces contained in the rejected belt
     */
    private List<Piece> _rejectedBeltPieces;
    
    /**
     * Number of right pieces
     */
    private int _rightPieces;
    
    /**
     * Number of wrong pieces
     */
    private int _wrongPieces;
    
    /**
     * Total amount of right pieces
     */
    private int _allRightPieces;
    
    /**
     * Total amount of wrong pieces
     */
    private int _allWrongPieces;

    /**
     * Constructor de la clase
     * @param _acceptedBeltRunning Status of the accepted belt
     * @param _rejectedBeltRunning STatus of the rejected belt
     * @param _sensor8Status Sensor8 status
     * @param _sensor9Status Sensor9 status
     * @param _sensor10Status Sensor10 status
     * @param _sensor11Status Sensor11 status
     * @param _acceptedBeltPieces List of accepted belt pieces
     * @param _rejectedBeltPieces  List of the rejected belt pieces
     */
    public Slave3Data(boolean _acceptedBeltRunning, boolean _rejectedBeltRunning,
            boolean _sensor8Status, boolean _sensor9Status, boolean _sensor10Status,
            boolean _sensor11Status, List<Piece> _acceptedBeltPieces, List<Piece> _rejectedBeltPieces) {
        this._acceptedBeltRunning = _acceptedBeltRunning;
        this._rejectedBeltRunning = _rejectedBeltRunning;
        this._sensor8Status = _sensor8Status;
        this._sensor9Status = _sensor9Status;
        this._sensor10Status = _sensor10Status;
        this._sensor11Status = _sensor11Status;
        this._acceptedBeltPieces = _acceptedBeltPieces;
        this._rejectedBeltPieces = _rejectedBeltPieces;
        this._rightPieces = 0;
        this._wrongPieces = 0;
    }

    /**
     * Secondary Constructor
     */
    public Slave3Data() {
    }

    /**
     * @return List of pieces contained in the accepted belt
     */
    public List<Piece> getAcceptedBeltPieces() {
        return _acceptedBeltPieces;
    }

    /**
     * Sets the list of pieces contained in the accepted belt
     * @param _acceptedBeltPieces 
     */
    public void setAcceptedBeltPieces(List<Piece> _acceptedBeltPieces) {
        this._acceptedBeltPieces = _acceptedBeltPieces;
    }

    /**
     * @return Accepted Belt status
     */
    public boolean isAcceptedBeltRunning() {
        return _acceptedBeltRunning;
    }

    /**
     * Sets the status of the accepted belt
     * @param _acceptedBeltRunning 
     */
    public void setAcceptedBeltRunning(boolean _acceptedBeltRunning) {
        this._acceptedBeltRunning = _acceptedBeltRunning;
    }

    /**
     * @return The list of pieces in the rejected belt
     */
    public List<Piece> getRejectedBeltPieces() {
        return _rejectedBeltPieces;
    }

    /**
     * Sets the list of pieces in the rejected belt
     * @param _rejectedBeltPieces 
     */
    public void setRejectedBeltPieces(List<Piece> _rejectedBeltPieces) {
        this._rejectedBeltPieces = _rejectedBeltPieces;
    }

    /**
     * @return STatus of the rejected belt
     */
    public boolean isRejectedBeltRunning() {
        return _rejectedBeltRunning;
    }

    /**
     * Sets the status of the rejected belt
     * @param _rejectedBeltRunning 
     */
    public void setRejectedBeltRunning(boolean _rejectedBeltRunning) {
        this._rejectedBeltRunning = _rejectedBeltRunning;
    }

    /**
     * @return Sensor10 status
     */
    public boolean isSensor10Status() {
        return _sensor10Status;
    }

    /**
     * Sets the status of the senso10
     * @param _sensor10Status 
     */
    public void setSensor10Status(boolean _sensor10Status) {
        this._sensor10Status = _sensor10Status;
    }

    /**
     * Sensor 11 status
     * @return 
     */
    public boolean isSensor11Status() {
        return _sensor11Status;
    }

    /**
     * Sets the status of the sensor 11
     * @param _sensor11Status 
     */
    public void setSensor11Status(boolean _sensor11Status) {
        this._sensor11Status = _sensor11Status;
    }

    /**
     * @return Sensor8 status
     */
    public boolean isSensor8Status() {
        return _sensor8Status;
    }

    /**
     * Sets the status of the sensor8
     * @param _sensor8Status 
     */
    public void setSensor8Status(boolean _sensor8Status) {
        this._sensor8Status = _sensor8Status;
    }

    /**
     * @return Sensor9 status
     */
    public boolean isSensor9Status() {
        return _sensor9Status;
    }

    /**
     * Sets the status of the sensor9
     * @param _sensor9Status 
     */
    public void setSensor9Status(boolean _sensor9Status) {
        this._sensor9Status = _sensor9Status;
    }

    /**
     * @return Number of right pieces
     */
    public int getRightPieces() {
        return _rightPieces;
    }

    /**
     * @return Number of wrong pieces
     */
    public int getWrongPieces() {
        return _wrongPieces;
    }

    /**
     * Sets the number of right pieces
     * @param _rightPieces 
     */
    public void setRightPieces(int _rightPieces) {
        this._rightPieces = _rightPieces;
    }

    /**
     * Sets the number of wrong pieces
     * @param _wrongPieces 
     */
    public void setWrongPieces(int _wrongPieces) {
        this._wrongPieces = _wrongPieces;
    }

    /**
     * @return Total amount of right pieces
     */
    public int getAllRightPieces() {
        return _allRightPieces;
    }

    /**
     * @return Total amount of wrong pieces
     */
    public int getAllWrongPieces() {
        return _allWrongPieces;
    }

    /**
     * Sets the total number of right pieces
     * @param _allRightPieces 
     */
    public void setAllRightPieces(int _allRightPieces) {
        this._allRightPieces = _allRightPieces;
    }

    /**
     * SEts the total number of wrong pieces
     * @param _allWrongPieces 
     */
    public void setAllWrongPieces(int _allWrongPieces) {
        this._allWrongPieces = _allWrongPieces;
    }
}
