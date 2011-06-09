
package Element.Station;

import Automaton.Slaves.Slave;
import Auxiliar.Constants;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.Piece.Piece.PieceType;
import Element.PieceContainer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the Assembly Station that assembles two pieces
 * @author Echoplex
 */
public class AssemblyStation implements PieceContainer {

    /**
     * Time to assemble pieces
     */
    private int _assemblyTime;
    
    /**
     * List of pieces in the station
     */
    protected List<Piece> _pieces;
    
    /**
     * Identifier
     */
    private int _id;
    
    /**
     * Process that makes the callback
     */
    protected Slave _process;
    
    /**
     * Shows if the assembly station is active or not
     */
    protected boolean _moving;
    
    /**
     * Shows if the assembly station is currently working
     */
    private boolean _actuating;
    
    /**
     * Logger callback
     */
    protected Logger _logger = Logger.getLogger(AssemblyStation.class.toString());

    /**
     * Constructor of the class
     * @param id Identifier
     */
    public AssemblyStation(int id) {
        _id = id;
        _moving = false;
        _actuating = false;
        _pieces = Collections.synchronizedList(new ArrayList<Piece>());
    }

    /**
     * Defines the Running method of the class
     */
    @Override
    public synchronized void run() {
        while (true) {
            try {
                Thread.sleep(100);
                if (_moving) {
                    if (!_pieces.isEmpty()) {
                        if (_pieces.get(0).getType().equals(PieceType.assembly)) {
                            this._process.sendCommand(Constants.SLAVE1_ROBOT1_ASSEMBLY_COMPLETED);
                        } else if(_pieces.size()==2){
                            assemble();
                        }
                    }
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(AssemblyStation.class.toString()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This function creates an assembled piece from a gear and an axis
     * @return TRUE if everything went good, FALSE in other case
     */
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

                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * @return The time to assembly pieces
     */
    public int getAssemblyTime() {
        return _assemblyTime;
    }

    /**
     * Sets a new value for the assembly time
     * @param assemblyTime New value for the asssembly time
     */
    public void setAssemblyTime(int assemblyTime) {
        this._assemblyTime = assemblyTime;
    }

    /**
     * Adds a piece to the station and updates its position
     * @param p New Piece
     */
    public void addPiece(Piece p) {
        _pieces.add(p);
        updatePosition(p);
    }

    /**
     * Adds a sensor to the station
     * @param s New Sensor
     */
    public void addSensor(Sensor s) {
    }

    /**
     * @return LIst of pieces contained in the station
     */
    public List<Piece> getPieces() {
        return _pieces;
    }

    /**
     * @return Status of the station
     */
    public boolean isMoving() {
        return _moving;
    }

    /**
     * Removes the last pieces added to the station
     */
    public void removeLastPiece() {
        if (_pieces.isEmpty()) {
            Logger.getLogger(AssemblyStation.class.toString()).log(Level.SEVERE, "Assembly station with id {0}: unable to remove last element", _id);
            return;
        }
        _pieces.remove(0);
    }

    /**
     * @return The identifier
     */
    @Override
    public int getId() {
        return _id;
    }

    /**
     * Sets a new value to the identifier
     * @param id New identifier value
     */
    @Override
    public void setId(int id) {
        _id = id;
    }

    /**
     * Sets the list of pieces contained in the assembly station
     * @param pieces New list of pieces
     */
    public void setPieces(List<Piece> pieces) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return The process linked to the station
     */
    @Override
    public Slave getProcess() {
        return _process;
    }

    /**
     * Sets a new process to the station
     * @param _process New process
     */
    @Override
    public void setProcess(Slave _process) {
        this._process = _process;
    }

    /**
     * Starts the Station
     */
    @Override
    public void startContainer() {
        if (!_moving) {
            _logger.log(Level.INFO, "Assembly table with id {0} has started", _id);
        }
        _moving = true;
    }

    /**
     * Stops the Station
     */
    @Override
    public void stopContainer() {
        if (_moving) {
            _logger.log(Level.INFO, "Assembly table with id {0} has stopped", _id);
        }
        _moving = false;
    }

    /**
     * Updates the GUI with the pieces contained in the station
     * @param piece Piece to update
     */
    private void updatePosition(Piece piece) {
        if (piece.getType().equals(PieceType.assembly)) {
            piece.setGuiPosition(Constants.ASSEMBLY_STATION_GEAR_POSITION);
        } else if (piece.getType().equals(PieceType.gear)) {
            piece.setGuiPosition(Constants.ASSEMBLY_STATION_GEAR_POSITION);
        } else if (piece.getType().equals(PieceType.axis)) {
            piece.setGuiPosition(Constants.ASSEMBLY_STATION_AXIS_POSITION);
        }
    }

    /**
     * @return TRUE if the station is currently working or not
     */
    public boolean isActuating() {
        return _actuating;
    }
}
