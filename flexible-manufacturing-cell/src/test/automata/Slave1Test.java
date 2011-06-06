/* Slave 1 code */
package test.automata;

import Automaton.Slaves.Slave1;
import Auxiliar.IOInterface;
import Auxiliar.IOProcess;
import Element.Piece.Piece;
import Element.Piece.Piece.PieceType;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Slave1Test extends Slave1 implements IOProcess {

    public static void main(String args[]) {
        Slave1Test s1 = new Slave1Test();
        s1.initialize();
        s1.startRobot();
    }

    public Slave1Test() {
    }


    // In this loop just one gear and axis are placed
    protected void mainLoop() {

        Piece p = new Piece();
        p.setPosition(0);
        p.setType(PieceType.gear);
        _gearBelt.addPiece(p);

        Logger.getLogger(Slave1.class.getName()).log(Level.FINE, "Added gear");

        p = new Piece();
        p.setPosition(0);
        p.setType(PieceType.axis);
        _axisBelt.addPiece(p);
        Logger.getLogger(Slave1.class.getName()).log(Level.FINE, "Added axis");
    }

}