/*
 * This class checks the quality of pieces.
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

public class QualityControlStation implements PieceContainer {

    private int _qualityTime;
    private int _sucessRate;
    protected List<Piece> _pieces;
    private int _id;
    protected Slave _process;
    protected boolean _moving;
    private int _rightPieces;
    private int _wrongPieces;

    public QualityControlStation(int id) {
        _id = id;
        _pieces = Collections.synchronizedList(new ArrayList <Piece>());
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
                checkQuality();
            }
        }
    }

    public boolean checkQuality() {
        if (_pieces.size() == 1) {

            try {
                Thread.sleep(_qualityTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(AssemblyStation.class.getName()).log(Level.SEVERE, null, ex);
            }

            int random = (int) Math.random() * 100;
            if (random > _sucessRate) {
                this._process.orderToRobot(Constants.SLAVE3_ROBOT2_QUALITY_CONTROL_COMPLETED_OK);
            } else {
                this._process.orderToRobot(Constants.SLAVE3_ROBOT2_QUALITY_CONTROL_COMPLETED_NOT_OK);
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