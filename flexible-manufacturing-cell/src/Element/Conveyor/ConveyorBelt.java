/*
 * Abstract class that defines a belt wich contains sensors. This class is extended by OneSensorBelt
 * and TwoSensorBelt depending on what type it is.
 */


package Element.Conveyor;

import Element.Piece.Piece;
import Element.PieceContainer;
import java.util.ArrayList;

public abstract class ConveyorBelt extends Thread implements PieceContainer {

    public int _id;
    private ArrayList<Piece> _pieces;
    private int _length;
    private int _speed;
    private boolean _moving;
    
    public ConveyorBelt(int id, int speed, int length){
        _id = id;
        _pieces = new ArrayList();
        _length = length;
        _speed = speed;
        _moving = false;
    }
    
    @Override
    public void run(){
        while(_moving){
            
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

    public ArrayList<Element.Piece.Piece> getPieces() {
        return _pieces;
    }

    public void setPieces(ArrayList<Element.Piece.Piece> pieces) {
        _pieces = pieces;
    }

    public void setEventListeners() {
        throw new UnsupportedOperationException();
    }

    public int getConveyorId() {
        return _id;
    }

    public void setConveyorId(int id) {
        throw new UnsupportedOperationException();
    }
}