package Element.Robot;

import Auxiliar.State;
import Element.Piece.Piece;
import Automaton.Master.Master;

public class Robot2 implements Robot {
	private RobotMailBox _mailBox;
	private State _state;
	private Piece _loadedPiece;

	public void pickAssembly() {
		throw new UnsupportedOperationException();
	}

	public void transportAssembly() {
		throw new UnsupportedOperationException();
	}

	public void transportWeldedAssembly() {
		throw new UnsupportedOperationException();
	}

	public RobotMailBox getMailBox() {
		throw new UnsupportedOperationException();
	}

	public void setMailBox(RobotMailBox mailBox) {
		throw new UnsupportedOperationException();
	}

	public void notifyAutomaton() {
		throw new UnsupportedOperationException();
	}

	public void runCommand(int command) {
		throw new UnsupportedOperationException();
	}

	public Piece getLoadedPiece() {
		throw new UnsupportedOperationException();
	}

	public void setLoadedPiece(Piece loadedPiece) {
		throw new UnsupportedOperationException();
	}
}