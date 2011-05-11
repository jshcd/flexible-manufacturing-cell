/*
 * This class represents a sensor. It is inserted in a container and activated when a piece
 * reaches a certain position.
 */
package Element.Other;

import Automaton.Slaves.Slave;
import Element.Conveyor.ConveyorBelt;
import Element.Piece.Piece;
import Element.PieceContainer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sensor extends Thread{

    private int _id;
    private int _state;
    private PieceContainer _associatedContainer;
    private Slave _process;
    private double _positionInBelt;
    private SensorMailBox _mailBox;
    private double _range = 0.2;
    private Piece _detectedPiece;

    public int getSensorId() {
        return _id;
    }

    public void setSensorId(int _id) {
        this._id = _id;
    }

    public int getSensorState() {
        return _state;
    }
    
    @Override
    public void start(){
        
        // TODO: Check this works right
        while(true){
            try {
                Thread.sleep(50);
                List<Piece> pieces = _associatedContainer.getPieces();
                for(Piece p:pieces){
                    if(p.getPosition() >= _positionInBelt+_range){
                        _detectedPiece = p;
                        activate();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ConveyorBelt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void activate() {
        throw new UnsupportedOperationException();
    }

    public double getPositionInBelt() {
        return _positionInBelt;
    }

    public void setPositionInBelt(double positionInBelt) {
        _positionInBelt =  positionInBelt;
    }

    public PieceContainer getAssociatedContainer() {
        return _associatedContainer;
    }

    public void setAssociatedContainer(PieceContainer associatedContainer) {
        _associatedContainer = associatedContainer;
    }

    public SensorMailBox getMailBox() {
        return _mailBox;
    }

    public void setMailBox(SensorMailBox _mailBox) {
        this._mailBox = _mailBox;
    }

    public Slave getProcess() {
        return _process;
    }

    public void setProcess(Slave _process) {
        this._process = _process;
    }
    
    public void notifyContainer() {
        // TODO: Mailbox implementation here
    }

    public Piece getDetectedPiece() {
        return _detectedPiece;
    }

    public void setDetectedPiece(Piece _detectedPiece) {
        this._detectedPiece = _detectedPiece;
    }

    public double getRange() {
        return _range;
    }

    public void setRange(double _range) {
        this._range = _range;
    }
    
    
}