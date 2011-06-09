
package Element.Station;

import Automaton.Slaves.Slave;
import Auxiliar.Constants;
import Element.Conveyor.ConveyorBelt;
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
 * This class includes the functionality to check if a piece is right or wrong
 * @author Echoplex
 */
public class QualityControlStation implements PieceContainer {

    /**
     * Time to process the quality control
     */
    private int _qualityTime;
    
    /**
     * Quality Control Simulation Success Rate
     * @see MasterConfigurationData
     */
    private int _sucessRate = 60;
    
    /**
     * List of pieces
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
     * Shows if the station is active
     */
    protected boolean _moving;
    
    /**
     * Shows if the station is working
     */
    private boolean _actuating;

    /**
     * Constructor of the class
     * @param id Identifier
     */
    
    public QualityControlStation(int id) {
        _id = id;
        _moving = false;
        _actuating = false;
        _pieces = Collections.synchronizedList(new ArrayList<Piece>());
    }

    /**
     * Run method
     */
    @Override
    public synchronized void run() {
        while (true) {
            try {
                Thread.sleep(100);
                if (_moving) {
                    if (!_pieces.isEmpty()) {
                        if (_pieces.get(0).getType().equals(PieceType.weldedAssemblyOk)) {
                            this._process.sendCommand(Constants.SLAVE2_ROBOT2_QUALITY_CONTROL_COMPLETED_OK);
                        } else if (_pieces.get(0).getType().equals(PieceType.weldedAssemblyNotOk)) {
                            this._process.sendCommand(Constants.SLAVE2_ROBOT2_QUALITY_CONTROL_COMPLETED_NOT_OK);
                        } else if (_pieces.get(0).getType().equals(PieceType.weldedAssembly)) {
                            checkQuality();
                        }
                    }
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(AssemblyStation.class.toString()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Function that checks the quality of the piece loaded in the station
     * @return TRUE if everything went good, FALSE in other case
     */
    public boolean checkQuality() {
        if (_pieces.size() == 1) {

            _actuating = true;
            try {
                Thread.sleep(_qualityTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(AssemblyStation.class.getName()).log(Level.SEVERE, null, ex);
            }
            _actuating = false;

            int random = (int) (Math.random() * 100);
            if (random < _sucessRate) {
                Logger.getLogger(QualityControlStation.class.getName()).log(Level.INFO, "Quality completed completed OK");
                _pieces.get(0).setType(PieceType.weldedAssemblyOk);
                this._process.sendCommand(Constants.SLAVE2_ROBOT2_QUALITY_CONTROL_COMPLETED_OK);
            } else {
                Logger.getLogger(QualityControlStation.class.getName()).log(Level.INFO, "Quality completed completed NOT OK");
                _pieces.get(0).setType(PieceType.weldedAssemblyNotOk);
                this._process.sendCommand(Constants.SLAVE2_ROBOT2_QUALITY_CONTROL_COMPLETED_NOT_OK);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return The time to process the qualityControl
     */
    public int getQualityTime() {
        return _qualityTime;
    }

    /**
     * Sets a new value for the quality control process
     * @param qualityTime New value for the process time
     */
    public void setQualityTime(int qualityTime) {
        this._qualityTime = qualityTime;
    }

    /**
     * @return The Success rate
     */
    public int getSucessRate() {
        return _sucessRate;
    }

    /**
     * Sets a new value for the success rate
     * @param sucessRate New success rate value - expected 0 - 100
     */
    public void setSucessRate(int sucessRate) {
        this._sucessRate = sucessRate;
    }

    /**
     * Adds a piece to the station
     * @param p Piece to add
     */
    public void addPiece(Piece p) {
        _pieces.add(p);
        updatePosition(p);
    }

    /**
     * Adds a sensor to the station
     * @param s Sensor to add
     */
    public void addSensor(Sensor s) {
    }

    /**
     * @return The list of pieces contained in the station
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
     * Removes the last piece added to the station
     */
    public void removeLastPiece() {
        if (_pieces.isEmpty()) {
            Logger.getLogger(ConveyorBelt.class.getName()).log(Level.SEVERE, "Assembly station with id {0}: unable to remove last element", _id);
            return;
        }
        _pieces.remove(0);
    }

    /**
     * @return The Identifier
     */
    @Override
    public int getId() {
        return _id;
    }

    /**
     * Sets a new value for the identifier
     * @param id New Identifier
     */
    @Override
    public void setId(int id) {
        _id = id;
    }

    /**
     * Sets a new list of pieces contained in the station
     * @param pieces List of pieces
     */
    public void setPieces(List<Piece> pieces) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return Process linked to the station
     */
    @Override
    public Slave getProcess() {
        return _process;
    }

    /**
     * Sets the process linked to the station
     * @param _process New linked process
     */
    @Override
    public void setProcess(Slave _process) {
        this._process = _process;
    }

    /**
     * Starts the station
     */
    @Override
    public void startContainer() {
        if (!_moving) {
            Logger.getLogger(ConveyorBelt.class.getName()).log(Level.INFO, "Conveyor Belt with id {0} has started", _id);
        }
        _moving = true;
    }

    /**
     * Stops the station
     */
    @Override
    public void stopContainer() {
        if (_moving) {
            Logger.getLogger(ConveyorBelt.class.getName()).log(Level.INFO, "Assembly table with id {0} has stopped", _id);
        }
        _moving = false;
    }

    /**
     * Updates the GUI Position of a piece
     * @param piece Piece to update in GUI
     */
    private void updatePosition(Piece piece) {
        piece.setGuiPosition(Constants.QUALITY_STATION_CENTER_POSITION);
    }
    
    /**
     * @return The current working status of the station
     */
    public boolean isActuating() {
        return _actuating;
    }
}
