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
    private float _positionInBelt;
    private SensorMailBox _mailBox;

    public int getSensorId() {
        return _id;
    }

    public void setSensorId(int _id) {
        this._id = _id;
    }

    public int getSensorState() {
        return _state;
    }
    
    public void run(){
        
        // TODO: Check this works right
        while(true){
            try {
                Thread.sleep(50);
                List<Piece> pieces = _associatedContainer.getPieces();
                for(Piece p:pieces){
                    if(p.getPosition() >= _positionInBelt+0.2){
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

    public float getPositionInBelt() {
        return _positionInBelt;
    }

    public void setPositionInBelt(float positionInBelt) {
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
}