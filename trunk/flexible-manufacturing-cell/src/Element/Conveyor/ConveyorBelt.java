/*
 * Abstract class that defines a belt wich contains sensors. This class is extended by OneSensorBelt
 * and TwoSensorBelt depending on what type it is.
 */
package Element.Conveyor;

import Automaton.Slaves.Slave;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.PieceContainer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConveyorBelt implements PieceContainer, Runnable {

    protected int _id;
    protected List<Piece> _pieces;
    protected int _length;
    protected int _speed;
    protected boolean _moving;
    protected ArrayList<Sensor> _sensors;
    // Process for which it works
    protected Slave _process;

    public ConveyorBelt(int id, int speed, int length) {
        _id = id;
        _pieces = Collections.synchronizedList(new ArrayList<Piece>());
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
            if (_moving) {
                try {
                    Thread.sleep(10);
                    synchronized (_pieces) {
                        Iterator i = _pieces.iterator();
                        while(i.hasNext()){
                            Piece p = (Piece)i.next();
                            p.setPosition(p.getPosition() + ((double)_speed / 100));
                            System.out.println(p.getPosition());
                        }
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConveyorBelt.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void startContainer() {
        _moving = true;
        Logger.getLogger(ConveyorBelt.class.getName()).log(Level.INFO, "Conveyor Belt with id {0} has started", _id);
    }

    public void stopContainer() {
        _moving = false;
        Logger.getLogger(ConveyorBelt.class.getName()).log(Level.INFO, "Conveyor Belt with id {0} has stoped", _id);
    }

    public int getSpeed() {
        return _speed;
    }

    public void setSpeed(int speed) {
        _speed = speed;
    }

    public int getLength() {
        return _length;
    }

    public void setLength(int length) {
        _length = length;
    }

    public List<Element.Piece.Piece> getPieces() {
        return _pieces;
    }

    public void setPieces(List<Element.Piece.Piece> pieces) {
        _pieces = pieces;
    }

    public int getConveyorId() {
        return _id;
    }

    public void setConveyorId(int id) {
        _id = id;
    }

    public Slave getProcess() {
        return _process;
    }

    public void setProcess(Slave _process) {
        this._process = _process;
    }

    public void addSensor(Sensor s) {
        _sensors.add(s);
    }

    public void addPiece(Piece p) {
        _pieces.add(p);
    }

    public void removeLastPiece() {
        if (_pieces.size() == 0) {
            System.out.println("CONVEYOR " + _id + ": Unable to remove last element. Empty conveyor");
        }
        Piece last = new Piece();
        last.setPosition(0);
        for (Piece p : _pieces) {
            if (p.getPosition() > last.getPosition()) {
                last = p;
            }
        }
        _pieces.remove(last);
    }
}