/*
 * This class assambles 2 pieces into a new one
 */
package Element.Station;

import Automaton.Slaves.Slave;
import Auxiliar.Constants;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.Conveyor.ConveyorBelt;
import Element.Piece.Piece.PieceType;
import Element.PieceContainer;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
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
    private boolean _actuating;
    private boolean _pieceReady;
    protected Logger _logger = Logger.getLogger(AssemblyStation.class.toString());

    public AssemblyStation(int id) {
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
                    if (!_pieces.isEmpty()) {
                        if (_pieces.get(0).getType().equals(PieceType.assembly)) {
                            this._process.sendCommand(Constants.SLAVE1_ROBOT1_ASSEMBLY_COMPLETED);
                        }
                    }

                }

            } catch (InterruptedException ex) {
                Logger.getLogger(AssemblyStation.class.toString()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean assemble() {
        synchronized (_pieces) {
            if (_pieces.size() == 2) {
                _pieces.remove(1);
                _pieces.remove(0);
                Piece p = new Piece();
                _actuating = true;
                try {
                    Thread.sleep(_assemblyTime);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AssemblyStation.class.toString()).log(Level.SEVERE, null, ex);
                }
                _actuating = false;

                p.setType(Piece.PieceType.assembly);
                _pieces.add(p);
                updatePosition(p);
                this._process.sendCommand(Constants.SLAVE1_ROBOT1_ASSEMBLY_COMPLETED);
                _logger.log(Level.INFO, "Assembly completed");
                _pieceReady = true;
                
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
            Logger.getLogger(AssemblyStation.class.toString()).log(Level.SEVERE, "Assembly station with id {0}: unable to remove last element", _id);
            return;
        }
        _pieces.remove(0);
        _pieceReady = false;
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
            _logger.log(Level.INFO, "Assembly table with id {0} has started", _id);
        }
        _moving = true;
    }

    @Override
    public void stopContainer() {
        if (_moving) {
            _logger.log(Level.INFO, "Assembly table with id {0} has stopped", _id);
        }
        _moving = false;
    }

    private void updatePosition(Piece piece) {
        if (piece.getType().equals(PieceType.assembly)) {
            piece.setGuiPosition(Constants.ASSEMBLY_STATION_GEAR_POSITION);
        } else if (piece.getType().equals(PieceType.gear)) {
            piece.setGuiPosition(Constants.ASSEMBLY_STATION_GEAR_POSITION);
        } else if (piece.getType().equals(PieceType.axis)) {
            piece.setGuiPosition(Constants.ASSEMBLY_STATION_AXIS_POSITION);
        }
    }

    public boolean isActuating() {
        return _actuating;
    }
}
