package Element.Conveyor;

import Element.Other.Sensor;
import Element.Piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class TwoSensorBelt extends ConveyorBelt {

    private Sensor _unloadSensor;
    private Sensor _loadSensor;

    public TwoSensorBelt(int id, int speed, int length) {
        super(id, speed, length);
    }

    public Sensor getLoadSensor() {
        throw new UnsupportedOperationException();
    }

    public void setLoadSensor(Sensor loadSensor) {
        throw new UnsupportedOperationException();
    }

    public Sensor getUnloadSensor() {
        throw new UnsupportedOperationException();
    }

    public void setUnloadSensor(Sensor unloadSensor) {
        throw new UnsupportedOperationException();
    }

    public void loadSensorListener(Object actionEvent_e) {
        throw new UnsupportedOperationException();
    }

    public void actuateUnloadSensor(Object actionEvent_e) {
        throw new UnsupportedOperationException();
    }



    @Override
    public void detectEnd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }




}