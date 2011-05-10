/*
 * Abstract class that defines a belt wich contains sensors. This class is extended by OneSensorBelt
 * and TwoSensorBelt depending on what type it is.
 */


package Element.Conveyor;

import Element.Piece.Piece;
import Element.PieceContainer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ConveyorBelt extends Thread implements PieceContainer {

    public int _id;
    private List<Piece> _pieces;
    private int _length;
    private int _speed;
    private boolean _moving;
    
    public ConveyorBelt(int id, int speed, int length){
        _id = id;
        _pieces = Collections.synchronizedList(new ArrayList<Piece>());
        _length = length;
        _speed = speed;
        _moving = false;
    }
    
    @Override
    public void run(){
        
        // TODO: Check this works right
        while(_moving){
            try {
                Thread.sleep(1000/_speed);
                for(Piece p:_pieces){
                    p.setPosition(p.getPosition() + 1);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ConveyorBelt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void startBelt() {
        _moving = true;
    }

    public void stopBelt() {
        _moving = false;
    }

    public abstract void detectEnd();

    public int getSpeed() {
        return _speed;
    }

    public void setSpeed(int speed) {
        _speed = speed;
    }

    public int getLength() {
        return _length;
    }

    public void setLength(int length) {
        _length = length;
    }

    public List<Element.Piece.Piece> getPieces() {
        return _pieces;
    }

    public void setPieces(List<Element.Piece.Piece> pieces) {
        _pieces = pieces;
    }

    public void setEventListeners() {
        throw new UnsupportedOperationException();
    }

    public int getConveyorId() {
        return _id;
    }

    public void setConveyorId(int id) {
        _id = id;
    }
}