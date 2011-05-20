/*
 * This class transforms assemblies into welded pieces.
 */

package Element.Station;

import Element.Piece.Piece;
import Element.Other.HidraulicActuator;
import Element.Other.Sensor;
import Element.Conveyor.ConveyorBelt;
import java.util.ArrayList;

public class WeldingStation extends ConveyorBelt {
	private ArrayList<Piece> _capacity;
	private HidraulicActuator _actuator;

	public void weld() {
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
}