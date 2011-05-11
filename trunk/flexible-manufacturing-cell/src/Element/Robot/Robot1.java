package Element.Robot;

import Auxiliar.State;
import Element.Piece.Piece;
import Element.Other.Sensor;
import Automaton.Slaves.Slave1;
import Automaton.Slaves.Slave2;
import Element.Conveyor.ConveyorBelt;
import Element.Station.AssemblyStation;

public class Robot1 implements Robot {

    private RobotMailBox _mailBox;
    private State _state;
    private Piece _loadedPiece;
    private ConveyorBelt _gearsBelt;
    private ConveyorBelt _axisBelt;

    public void pickAxisGear() {
        throw new UnsupportedOperationException();
    }

    public RobotMailBox getMailBox() {
        throw new UnsupportedOperationException();
    }

    public void notifyAutomaton() {
        throw new UnsupportedOperationException();
    }

    public void runCommand(int command) {
        throw new UnsupportedOperationException();
    }

    public void setMailBox(RobotMailBox mailBox) {
        throw new UnsupportedOperationException();
    }

    public void transportAssembly() {
        throw new UnsupportedOperationException();
    }

    public void transportAxisGear() {
        throw new UnsupportedOperationException();
    }

    public Piece getLoadedPiece() {
        throw new UnsupportedOperationException();
    }

    public void setLoadedPiece(Piece loadedPiece) {
        throw new UnsupportedOperationException();
    }
}