/*
 * This class transforms assemblies into welded pieces.
 */
package Element.Station;

import Auxiliar.Constants;
import Element.Piece.Piece;
import Element.Conveyor.ConveyorBelt;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeldingStation extends ConveyorBelt {
    
    
    private int _weldingTime;
    
    public WeldingStation(int id, int speed, int length) {
        super(id, speed, length);
    }
    
    @Override
    public void start(){
        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(QualityControlStation.class.getName()).log(Level.SEVERE, null, ex);
            }
            weld();
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
            this._process.orderToRobot(Constants.SLAVE2_WELDED_ASSEMBLY_COMPLETED);
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
    
    
}