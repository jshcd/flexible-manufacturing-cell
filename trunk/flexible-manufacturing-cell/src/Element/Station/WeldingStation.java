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
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeldingStation implements PieceContainer {

    private int _weldingTime;
    protected List<Piece> _pieces;
    private int _id;
    protected Slave _process;
    protected boolean _moving;
    private boolean _actuating;
    private boolean _pieceReady;

    public WeldingStation(int id) {
        _id = id;
        _moving = false;
        _actuating = false;
        _pieceReady = false;
        _pieces = Collections.synchronizedList(new ArrayList<Piece>());
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                Thread.sleep(100);
                if (_moving) {
                    if (_pieceReady) {
                        this._process.sendCommand(Constants.SLAVE2_ROBOT2_WELDED_ASSEMBLY_COMPLETED);
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(AssemblyStation.class.toString()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean weld() {
        if (_pieces.size() == 1) {
            _actuating = true;
            try {
                Thread.sleep(_weldingTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(AssemblyStation.class.getName()).log(Level.SEVERE, null, ex);
            }
            _actuating = false;

            _pieces.remove(0);

            Piece p = new Piece();
            p.setType(Piece.PieceType.weldedAssembly);
            _pieces.add(p);
            this._process.sendCommand(Constants.SLAVE2_ROBOT2_WELDED_ASSEMBLY_COMPLETED);
            Logger.getLogger(WeldingStation.class.getName()).log(Level.INFO, "Welding completed");
            _pieceReady = true;
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
        updatePosition(p);
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
            Logger.getLogger(ConveyorBelt.class.getName()).log(Level.SEVERE, "Welding station with id {0}: unable to remove last element", _id);
            return;
        }
        _pieceReady = false;
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

    private void updatePosition(Piece piece) {
        piece.setGuiPosition(Constants.WELDING_STATION_CENTER_POSITION);
    }

    public boolean isActuating() {
        return _actuating;
    }
}