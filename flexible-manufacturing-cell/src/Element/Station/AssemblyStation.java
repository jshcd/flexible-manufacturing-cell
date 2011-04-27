package Element.Station;

import Element.Piece.Piece;
import Element.Other.Sensor;
import Element.Other.HidraulicActuator;
import Element.Conveyor.ConveyorBelt;
import java.util.ArrayList;
import java.util.ArrayList;

public class AssemblyStation extends ConveyorBelt {
	private ArrayList<Piece> _capacity;
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
}