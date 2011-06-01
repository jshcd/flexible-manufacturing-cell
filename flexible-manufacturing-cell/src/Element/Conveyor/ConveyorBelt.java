/*
 * Abstract class that defines a belt wich contains sensors. This class is extended by OneSensorBelt
 * and TwoSensorBelt depending on what type it is.
 */
package Element.Conveyor;

import Element.PieceContainer;
import Automaton.Slaves.Slave;
import Element.Other.Sensor;
import Element.Piece.Piece;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConveyorBelt implements PieceContainer {

    protected int _id;
    protected List<Piece> _pieces;
    protected double _length;
    protected int _speed;
    protected boolean _moving;
    protected ArrayList<Sensor> _sensors;
    // Process for which it works
    protected Slave _process;

    public ConveyorBelt(int id, int speed, double length) {
        _id = id;
        _pieces = new CopyOnWriteArrayList <Piece>();
        _length = length;
        _speed = speed;
        _moving = false;
        _sensors = new ArrayList();
    }

    @Override
    public synchronized void run() {
        Logger.getLogger(ConveyorBelt.class.getName()).log(Level.INFO, "Conveyor Belt with id {0} starts running", _id);

        // TODO: Check this works right
        while (true) {
            try {
                Thread.sleep(40);
                if (_moving) {
                    synchronized (_pieces) {
                        Iterator i = _pieces.iterator();
                        while (i.hasNext()) {
                            Piece p = (Piece) i.next();
                            p.setPosition(p.getPosition() + ((double) _speed / 100));
                            Logger.getLogger(ConveyorBelt.class.getName()).log(Level.FINEST, "ConveyorBelt " + _id + ": piece at {1}", p.getPosition());

//                            System.out.println(p.getPosition());
                        }
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ConveyorBelt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void startContainer() {
        if (!_moving) {
            Logger.getLogger(ConveyorBelt.class.getName()).log(Level.INFO, "Conveyor Belt with id {0} has started", _id);
        }

        _moving = true;
    }

    @Override
    public void stopContainer() {
        if (_moving) {
            Logger.getLogger(ConveyorBelt.class.getName()).log(Level.INFO, "Conveyor Belt with id {0} has stopped", _id);
        }
        _moving = false;
    }

    public int getSpeed() {
        return _speed;
    }

    public void setSpeed(int speed) {
        _speed = speed;
    }

    public double getLength() {
        return _length;
    }

    public void setLength(double length) {
        _length = length;
    }

    @Override
    public List<Element.Piece.Piece> getPieces() {
        return _pieces;
    }

    @Override
    public void setPieces(List<Element.Piece.Piece> pieces) {
        _pieces = pieces;
    }

    @Override
    public int getId() {
        return _id;
    }

    @Override
    public void setId(int id) {
        _id = id;
    }

    @Override
    public Slave getProcess() {
        return _process;
    }

    @Override
    public void setProcess(Slave _process) {
        this._process = _process;
    }

    @Override
    public void addSensor(Sensor s) {
        _sensors.add(s);
    }

    @Override
    public void addPiece(Piece p) {
        _pieces.add(p);
    }

    @Override
    public boolean isMoving() {
        return _moving;
    }

    // Used when an element is picked from the belt
    @Override
    public void removeLastPiece() {
        if (_pieces.isEmpty()) {
            Logger.getLogger(ConveyorBelt.class.getName()).log(Level.SEVERE, "Conveyor Belt with id {0}: unable to remove last element", _id);
            return;
        }

        Piece last = new Piece();
        last.setPosition(0);
        synchronized (_pieces) {
            Iterator it = _pieces.iterator();
            while (it.hasNext()) {
                Piece p = (Piece) it.next();
                if (p.getPosition() > last.getPosition()) {
                    last = p;
                }
            }
            _pieces.remove(last);
            this.startContainer();
        }
    }
}