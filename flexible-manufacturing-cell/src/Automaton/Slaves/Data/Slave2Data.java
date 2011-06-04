/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Automaton.Slaves.Data;

import Element.Piece.Piece;
import java.util.List;

/**
 *
 * @author David
 */
public class Slave2Data extends MailboxData {
    
    private static final long serialVersionUID = 1L;
    private boolean _weldingStationRunning;
    private boolean _qualityStationRunning;
    private boolean _sensor6Status;
    private boolean _sensor7Status;
    private List<Piece> _weldingStationPieces;
    private List<Piece> _qualityStationPieces;

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
    
    public Slave2Data(){}

    public List<Piece> getQualityStationPieces() {
        return _qualityStationPieces;
    }

    public void setQualityStationPieces(List<Piece> _qualityStationPieces) {
        this._qualityStationPieces = _qualityStationPieces;
    }

    public boolean isQualityStationRunning() {
        return _qualityStationRunning;
    }

    public void setQualityStationRunning(boolean _qualityStationRunning) {
        this._qualityStationRunning = _qualityStationRunning;
    }

    public boolean isSensor6Status() {
        return _sensor6Status;
    }

    public void setSensor6Status(boolean _sensor6Status) {
        this._sensor6Status = _sensor6Status;
    }

    public boolean isSensor7Status() {
        return _sensor7Status;
    }

    public void setSensor7Status(boolean _sensor7Status) {
        this._sensor7Status = _sensor7Status;
    }

    public List<Piece> getWeldingStationPieces() {
        return _weldingStationPieces;
    }

    public void setWeldingStationPieces(List<Piece> _weldingStationPieces) {
        this._weldingStationPieces = _weldingStationPieces;
    }

    public boolean isWeldingStationRunning() {
        return _weldingStationRunning;
    }

    public void setWeldingStationRunning(boolean _weldingStationRunning) {
        this._weldingStationRunning = _weldingStationRunning;
    }
}
