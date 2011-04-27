/*
 * Abstract class that defines a belt wich contains sensors. This class is extended by OneSensorBelt
 * and TwoSensorBelt depending on what type it is.
 */


package Element.Conveyor;

import Element.Piece.Piece;
import Element.PieceContainer;
import java.util.ArrayList;

public abstract class ConveyorBelt implements PieceContainer {

    public int _id;
    private ArrayList<Piece> _pieces;
    private int _length;
    private int _speed;

    public void startBelt() {
        throw new UnsupportedOperationException();
    }

    public void stopBelt() {
        throw new UnsupportedOperationException();
    }

    public void detectEnd() {
        throw new UnsupportedOperationException();
    }

    public int getSpeed() {
        throw new UnsupportedOperationException();
    }

    public void setSpeed(int speed) {
        throw new UnsupportedOperationException();
    }

    public int getLength() {
        throw new UnsupportedOperationException();
    }

    public void setLength(int length) {
        throw new UnsupportedOperationException();
    }

    public ArrayList<Element.Piece.Piece> getCapacity() {
        throw new UnsupportedOperationException();
    }

    public void setCapacity(ArrayList<Element.Piece.Piece> capacity) {
        throw new UnsupportedOperationException();
    }

    public void setEventListeners() {
        throw new UnsupportedOperationException();
    }

    public int getId() {
        throw new UnsupportedOperationException();
    }

    public void setId(int id) {
        throw new UnsupportedOperationException();
    }
}