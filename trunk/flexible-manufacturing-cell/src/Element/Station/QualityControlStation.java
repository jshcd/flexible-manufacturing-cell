/*
 * This class checks the quality of pieces.
 */
package Element.Station;

import Automaton.Slaves.Slave;
import Auxiliar.Constants;
import Element.Conveyor.ConveyorBelt;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.Piece.Piece.PieceType;
import Element.PieceContainer;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QualityControlStation implements PieceContainer {

    private int _qualityTime;
    private int _sucessRate = 60;
    protected List<Piece> _pieces;
    private int _id;
    protected Slave _process;
    protected boolean _moving;
    private boolean _actuating;

    public QualityControlStation(int id) {
        _id = id;
        _moving = false;
        _actuating = false;
        _pieces = Collections.synchronizedList(new ArrayList<Piece>());
    }

    @Override
    public synchronized void run() {
    }

    public boolean checkQuality() {
        if (_pieces.size() == 1) {

            _actuating = true;
            try {
                Thread.sleep(_qualityTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(AssemblyStation.class.getName()).log(Level.SEVERE, null, ex);
            }
            _actuating = false;

            int random = (int) Math.random() * 100;
            if (random > _sucessRate) {
                Logger.getLogger(QualityControlStation.class.getName()).log(Level.INFO, "Quality completed completed OK");
                this._process.sendCommand(Constants.SLAVE2_ROBOT2_QUALITY_CONTROL_COMPLETED_OK);
            } else {
                Logger.getLogger(QualityControlStation.class.getName()).log(Level.INFO, "Quality completed completed NOT OK");
                this._process.sendCommand(Constants.SLAVE2_ROBOT2_QUALITY_CONTROL_COMPLETED_NOT_OK);
            }
            return true;
        } else {
            return false;
        }

    }

    public int getQualityTime() {
        return _qualityTime;
    }

    public void setQualityTime(int qualityTime) {
        this._qualityTime = qualityTime;
    }

    public int getSucessRate() {
        return _sucessRate;
    }

    public void setSucessRate(int sucessRate) {
        this._sucessRate = sucessRate;
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

    private void updatePosition(Piece piece) {
        piece.setGuiPosition(Constants.QUALITY_STATION_CENTER_POSITION);
    }
    
    
    public boolean isActuating() {
        return _actuating;
    }
}
