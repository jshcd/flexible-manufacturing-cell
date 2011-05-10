/*
 * This class represents a sensor. It is inserted in a container and activated when a piece
 * reaches a certain position.
 */
package Element.Other;

import Element.Conveyor.ConveyorBelt;
import Element.Piece.Piece;
import Element.PieceContainer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sensor extends Thread{

    private int _state;
    private PieceContainer _associatedContainer;
    private int _positionInBelt;
    private SensorMailBox _mailBox;

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
                    if(p.getPosition() == _positionInBelt){
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

    public int getPositionInBelt() {
        throw new UnsupportedOperationException();
    }

    public void setPositionInBelt(int positionInBelt) {
        throw new UnsupportedOperationException();
    }

    public PieceContainer getAssociatedContainer() {
        throw new UnsupportedOperationException();
    }

    public void setAssociatedContainer(PieceContainer associatedContainer) {
        throw new UnsupportedOperationException();
    }

    public void notifyContainer() {
        throw new UnsupportedOperationException();
    }
}