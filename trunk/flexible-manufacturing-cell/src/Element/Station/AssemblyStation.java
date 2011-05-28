/*
 * This class assambles 2 pieces into a new one
 */
package Element.Station;

import Auxiliar.Constants;
import Element.Piece.Piece;
import Element.Other.Sensor;
import Element.Conveyor.ConveyorBelt;

public class AssemblyStation extends ConveyorBelt {

    public AssemblyStation(int id, int speed, int length) {
        super(id, speed, length);
    }

    public void assemble() {
        if (_pieces.size() == 2) {
            _pieces.remove(0);
            _pieces.remove(1);
            Piece p = new Piece();
            p.setType(Piece.PieceType.assembly);
            _pieces.add(p);
            this._process.orderToRobot(Constants.SLAVE1_ASSEMBLY_COMPLETED);
        } else {
            System.out.println("ASSEMBLY_TABLE: Unable to assemble pieces");
        }
    }

    public void pickAssembly() {
        if (_pieces.size() > 0) {
            _pieces.remove(0);
            Piece p = new Piece();
            p.setType(Piece.PieceType.assembly);
            _pieces.add(p);
            this._process.runCommand(MIN_PRIORITY);
        } else {
            System.out.println("ASSEMBLY_TABLE: Unable to pick assembly");
        }
    }
}