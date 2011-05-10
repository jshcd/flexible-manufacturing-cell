/*
 * This class assambles 2 pieces into a new one
 */

package Element.Station;

import Element.Piece.Piece;
import Element.Other.Sensor;
import Element.Other.HidraulicActuator;
import Element.Conveyor.ConveyorBelt;
import java.util.ArrayList;
import java.util.List;

public class AssemblyStation extends ConveyorBelt {
	private List<Piece> _capacity;
	private Sensor _loadSensor;
	private HidraulicActuator _actuator;

	public void assemble() {
		throw new UnsupportedOperationException();
	}

	public Sensor getLoadSensor() {
		throw new UnsupportedOperationException();
	}

	public void setLoadSensor(Sensor loadSensor) {
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

    @Override
    public void detectEnd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}