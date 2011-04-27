/*
 * This class represents a sensor. It is inserted in a container and activated when a piece
 * reaches a certain position.
 */

package Element.Other;

import Element.PieceContainer;

public class Sensor {
	private Integer _state;
	private PieceContainer _associatedContainer;
	private Integer _positionInBelt;
	private SensorMailBox _mailBox;

	public void getState() {
		throw new UnsupportedOperationException();
	}

	public void activate() {
		throw new UnsupportedOperationException();
	}

	public Integer getPositionInBelt() {
		throw new UnsupportedOperationException();
	}

	public void setPositionInBelt(Integer positionInBelt) {
		throw new UnsupportedOperationException();
	}

	public PieceContainer getAssociatedContainer() {
		throw new UnsupportedOperationException();
	}

	public void setAssociatedContainer(PieceContainer associatedContainer) {
		throw new UnsupportedOperationException();
	}

	public void notifyContainer() {
		throw new UnsupportedOperationException();
	}
}