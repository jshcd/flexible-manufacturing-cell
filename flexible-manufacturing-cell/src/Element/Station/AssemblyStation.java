/*
 * This class assambles 2 pieces into a new one
 */
package Element.Station;

import Auxiliar.Constants;
import Element.Piece.Piece;
import Element.Conveyor.ConveyorBelt;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssemblyStation extends ConveyorBelt {

    private int _assemblyTime;

    public AssemblyStation(int id, int speed, int length) {
        super(id, speed, length);
    }

    @Override
    public void run() {
        while (true) {
            if (_moving) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(QualityControlStation.class.getName()).log(Level.SEVERE, null, ex);
                }
                assemble();
            }
        }
    }

    public boolean assemble() {
        if (_pieces.size() == 2) {
            _pieces.remove(0);
            _pieces.remove(1);
            Piece p = new Piece();
            try {
                Thread.sleep(_assemblyTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(AssemblyStation.class.getName()).log(Level.SEVERE, null, ex);
            }
            p.setType(Piece.PieceType.assembly);
            _pieces.add(p);
            this._process.orderToRobot(Constants.SLAVE1_ROBOT1_ASSEMBLY_COMPLETED);
            return true;
        } else {
            return false;
        }
    }

    public int getAssemblyTime() {
        return _assemblyTime;
    }

    public void setAssemblyTime(int assemblyTime) {
        this._assemblyTime = assemblyTime;
    }
}