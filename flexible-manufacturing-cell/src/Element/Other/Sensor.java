/*
 * This class represents a sensor. It is inserted in a container and activated when a piece
 * reaches a certain position.
 */
package Element.Other;

import Automaton.Slaves.Slave;
import Element.Conveyor.ConveyorBelt;
import Element.Piece.Piece;
import Element.PieceContainer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sensor implements Runnable {

    private int _id;
    private int _state;
    private PieceContainer _associatedContainer;
    private Slave _process;
    private double _positionInBelt;
    private double _range = 0.2;
    private Piece _detectedPiece;
    private boolean _activated = false;

    public int getSensorId() {
        return _id;
    }

    public void setSensorId(int _id) {
        this._id = _id;
    }

    public int getSensorState() {
        return _state;
    }

    @Override
    public void run() {

        Logger.getLogger(Sensor.class.getName()).log(Level.INFO, "Sensor with id {0} has been created", _id);

        while (true) {
            try {

                if (_associatedContainer.isMoving()) {
                    Thread.sleep(25);
                } else {
                    Thread.sleep(500);
                }

                //TO-DO Sección crítica
                List<Piece> pieces = _associatedContainer.getPieces();
                boolean detected = false;
                synchronized (pieces) {
                    for (Piece p : pieces) {
                        if (p.getPosition() <= _positionInBelt + _range
                                && p.getPosition() >= _positionInBelt - _range) {
                            _detectedPiece = p;
                            detected = true;
                            break;
                        }
                    }
                }
                if (detected) {
                    activate();
                } else {
                    disactivate();
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(ConveyorBelt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void activate() throws InterruptedException {
        if (!_activated) {
            Logger.getLogger(Sensor.class.getName()).log(Level.INFO, "Sensor with id {0} is activated", _id);
        }
        _activated = true;
        report(1);
    }

    public void disactivate() throws InterruptedException {
        if (_activated) {
            Logger.getLogger(Sensor.class.getName()).log(Level.INFO, "Sensor with id {0} is disactivated", _id);
        }
        _activated = false;
        report(0);
    }

    private void report(int i) {
        _process.orderToRobot(_id * 10 + i);
        _process.reportToMaster(_id * 10 + i);
        _process.runCommand(_id * 10 + i);
    }

    public double getPositionInBelt() {
        return _positionInBelt;
    }

    public void setPositionInBelt(double positionInBelt) {
        _positionInBelt = positionInBelt;
    }

    public PieceContainer getAssociatedContainer() {
        return _associatedContainer;
    }

    public void setAssociatedContainer(PieceContainer _associatedContainer) {
        this._associatedContainer = _associatedContainer;
    }

    public Slave getProcess() {
        return _process;
    }

    public void setProcess(Slave _process) {
        this._process = _process;
    }

    public Piece getDetectedPiece() {
        return _detectedPiece;
    }

    public double getRange() {
        return _range;
    }

    public void setRange(double _range) {
        this._range = _range;
    }
}