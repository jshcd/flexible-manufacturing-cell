package Element.Other;

import Auxiliar.AutomatonState;
import Element.PieceContainer;
import Element.Station.AssemblyStation;
import Element.Station.WeldingStation;

public class HidraulicActuator {
	private AutomatonState _state;
	private PieceContainer _associatedContainer;
	private int _actuationTime;

	public void activate() {
		throw new UnsupportedOperationException();
	}

	public void stop() {
		throw new UnsupportedOperationException();
	}

	public int getActuationTime() {
		throw new UnsupportedOperationException();
	}

	public void setActuationTime(int actuationTime) {
		throw new UnsupportedOperationException();
	}
}