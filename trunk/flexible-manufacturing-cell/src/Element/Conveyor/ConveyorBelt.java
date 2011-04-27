/*
 * Abstract class that defines a belt wich contains sensors. This class is extended by OneSensorBelt
 * and TwoSensorBelt depending on what tpe it is
 */


package Element.Conveyor;

import Element.Piece.Piece;
import Element.PieceContainer;
import java.util.ArrayList;

public abstract class ConveyorBelt implements PieceContainer {

    public Integer _id;
    private ArrayList<Piece> _capacity;
    private Integer _length;
    private Integer _speed;

    public void startBelt() {
        throw new UnsupportedOperationException();
    }

    public void stopBelt() {
        throw new UnsupportedOperationException();
    }

    public void detectEnd() {
        throw new UnsupportedOperationException();
    }

    public Integer getSpeed() {
        throw new UnsupportedOperationException();
    }

    public void setSpeed(Integer speed) {
        throw new UnsupportedOperationException();
    }

    public Integer getLength() {
        throw new UnsupportedOperationException();
    }

    public void setLength(Integer length) {
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

    public Integer getId() {
        throw new UnsupportedOperationException();
    }

    public void setId(Integer id) {
        throw new UnsupportedOperationException();
    }
}