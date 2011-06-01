/*
 * This class transforms assemblies into welded pieces.
 */
package Element.Station;

import Automaton.Slaves.Slave;
import Auxiliar.Constants;
import Element.Conveyor.ConveyorBelt;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.PieceContainer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeldingStation implements PieceContainer {

    private int _weldingTime;
    protected List<Piece> _pieces;
    private int _id;
    protected Slave _process;
    protected boolean _moving;

    public WeldingStation(int id) {
        _id = id;
        _pieces = new ArrayList<Piece>(1);
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
                weld();
            }
        }
    }

    public boolean weld() {
        if (_pieces.size() == 1) {
            _pieces.remove(0);

            try {
                Thread.sleep(_weldingTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(AssemblyStation.class.getName()).log(Level.SEVERE, null, ex);
            }

            Piece p = new Piece();
            p.setType(Piece.PieceType.weldedAssembly);
            _pieces.add(p);
            this._process.orderToRobot(Constants.SLAVE2_ROBOT2_WELDED_ASSEMBLY_COMPLETED);
            return true;
        } else {
            return false;
        }
    }

    public int getWeldingTime() {
        return _weldingTime;
    }

    public void setWeldingTime(int weldingTime) {
        this._weldingTime = weldingTime;
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