/*
 * This class assambles 2 pieces into a new one
 */
package Element.Station;

import Element.Piece.Piece;
import Element.Other.Sensor;
import Element.Other.HidraulicActuator;
import Element.Conveyor.ConveyorBelt;
import java.util.List;

public class AssemblyStation extends ConveyorBelt {

    private List<Piece> _capacity;
    private Sensor _loadSensor;
    private HidraulicActuator _actuator;

    public AssemblyStation(int id, int speed, int length) {
        super(id, speed, length);
    }

    public void assemble() {
        throw new UnsupportedOperationException();
    }

}