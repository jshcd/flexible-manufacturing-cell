/*
 * This class transforms assemblies into welded pieces.
 */
package Element.Station;

import Element.Piece.Piece;
import Element.Other.HidraulicActuator;
import Element.Other.Sensor;
import Element.Conveyor.ConveyorBelt;
import java.util.ArrayList;
import java.util.List;

public class WeldingStation extends ConveyorBelt {

    private List<Piece> _pieces;
    private HidraulicActuator _actuator;
    
    public WeldingStation(int id, int speed, int length) {
        super(id, speed, length);
    }

    public void weld() {
        throw new UnsupportedOperationException();
    }
    
}