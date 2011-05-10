package Element.Conveyor;

import Element.Other.Sensor;
import Element.Piece.Piece;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class OneSensorBelt extends ConveyorBelt {

    private Sensor _unloadSensor;

    public OneSensorBelt(int id, int speed, int length) {
        super(id, speed, length);
    }

    public Sensor getUnloadSensor() {
        return _unloadSensor;
    }

    public void setUnloadSensor(Sensor unloadSensor) {
        _unloadSensor = unloadSensor;
    }

    public void actuateUnloadSensor(ActionEvent e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArrayList<Element.Piece.Piece> getPieces() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void detectEnd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}