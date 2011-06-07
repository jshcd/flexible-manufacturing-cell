/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Automaton.Slaves.Data;

import Auxiliar.MailboxData;
import Element.Piece.Piece;
import java.util.List;

/**
 *
 * @author David
 */
public class Slave3Data extends MailboxData {

    private static final long serialVersionUID = 1L;
    private boolean _acceptedBeltRunning;
    private boolean _rejectedBeltRunning;
    private boolean _sensor8Status;
    private boolean _sensor9Status;
    private boolean _sensor10Status;
    private boolean _sensor11Status;
    private List<Piece> _acceptedBeltPieces;
    private List<Piece> _rejectedBeltPieces;
    public int _rightPieces;
    public int _wrongPieces;

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

    public Slave3Data() {
    }

    public List<Piece> getAcceptedBeltPieces() {
        return _acceptedBeltPieces;
    }

    public void setAcceptedBeltPieces(List<Piece> _acceptedBeltPieces) {
        this._acceptedBeltPieces = _acceptedBeltPieces;
    }

    public boolean isAcceptedBeltRunning() {
        return _acceptedBeltRunning;
    }

    public void setAcceptedBeltRunning(boolean _acceptedBeltRunning) {
        this._acceptedBeltRunning = _acceptedBeltRunning;
    }

    public List<Piece> getRejectedBeltPieces() {
        return _rejectedBeltPieces;
    }

    public void setRejectedBeltPieces(List<Piece> _rejectedBeltPieces) {
        this._rejectedBeltPieces = _rejectedBeltPieces;
    }

    public boolean isRejectedBeltRunning() {
        return _rejectedBeltRunning;
    }

    public void setRejectedBeltRunning(boolean _rejectedBeltRunning) {
        this._rejectedBeltRunning = _rejectedBeltRunning;
    }

    public boolean isSensor10Status() {
        return _sensor10Status;
    }

    public void setSensor10Status(boolean _sensor10Status) {
        this._sensor10Status = _sensor10Status;
    }

    public boolean isSensor11Status() {
        return _sensor11Status;
    }

    public void setSensor11Status(boolean _sensor11Status) {
        this._sensor11Status = _sensor11Status;
    }

    public boolean isSensor8Status() {
        return _sensor8Status;
    }

    public void setSensor8Status(boolean _sensor8Status) {
        this._sensor8Status = _sensor8Status;
    }

    public boolean isSensor9Status() {
        return _sensor9Status;
    }

    public void setSensor9Status(boolean _sensor9Status) {
        this._sensor9Status = _sensor9Status;
    }

    public int getRightPieces() {
        return _rightPieces;
    }

    public int getWrongPieces() {
        return _wrongPieces;
    }

    public void setRightPieces(int _rightPieces) {
        this._rightPieces = _rightPieces;
    }

    public void setWrongPieces(int _wrongPieces) {
        this._wrongPieces = _wrongPieces;
    }


}
