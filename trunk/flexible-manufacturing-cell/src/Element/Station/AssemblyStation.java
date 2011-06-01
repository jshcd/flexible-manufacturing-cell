/*
 * This class assambles 2 pieces into a new one
 */
package Element.Station;

import Automaton.Slaves.Slave;
import Auxiliar.Constants;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.Conveyor.ConveyorBelt;
import Element.PieceContainer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssemblyStation implements PieceContainer {

    private int _assemblyTime;
    protected List<Piece> _pieces;
    private int _id;
    protected Slave _process;
    protected boolean _moving;

    public AssemblyStation(int id) {
        _id = id;
        _pieces = new ArrayList<Piece>(2);
    }

    @Override
    public synchronized void run() {
        while (true) {
            if (_moving) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(QualityControlStation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public boolean assemble() {
        synchronized (_pieces) {
            if (_pieces.size() == 2) {
                _pieces.remove(1);
                _pieces.remove(0);
                Piece p = new Piece();
                try {
                    Thread.sleep(_assemblyTime);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AssemblyStation.class.getName()).log(Level.SEVERE, null, ex);
                }
                p.setType(Piece.PieceType.assembly);
                _pieces.add(p);
                this._process.orderToRobot(Constants.SLAVE1_ROBOT1_ASSEMBLY_COMPLETED);
                Logger.getLogger(AssemblyStation.class.getName()).log(Level.INFO, "Assembly completed");

                return true;
            } else {
                return false;
            }
        }
    }

    public int getAssemblyTime() {
        return _assemblyTime;
    }

    public void setAssemblyTime(int assemblyTime) {
        this._assemblyTime = assemblyTime;
    }

    public void addPiece(Piece p) {
        _pieces.add(p);
    }

    public void addSensor(Sensor s) {
    }

    public List<Piece> getPieces() {
        return _pieces;
    }

    public boolean isMoving() {
        return _moving;
    }

    public void removeLastPiece() {
        if (_pieces.isEmpty()) {
            Logger.getLogger(ConveyorBelt.class.getName()).log(Level.SEVERE, "Assembly station with id {0}: unable to remove last element", _id);
            return;
        }
        _pieces.remove(0);
    }

    @Override
    public int getId() {
        return _id;
    }

    @Override
    public void setId(int id) {
        _id = id;
    }

    public void setPieces(List<Piece> pieces) {
        throw new UnsupportedOperationException("Not supported yet.");
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
    public void startContainer() {
        if (!_moving) {
            Logger.getLogger(ConveyorBelt.class.getName()).log(Level.INFO, "Conveyor Belt with id {0} has started", _id);
        }
        _moving = true;
    }

    @Override
    public void stopContainer() {
        if (_moving) {
            Logger.getLogger(ConveyorBelt.class.getName()).log(Level.INFO, "Assembly table with id {0} has stopped", _id);
        }
        _moving = false;
    }
}