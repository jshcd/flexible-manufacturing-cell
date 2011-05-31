/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import Element.Conveyor.ConveyorBelt;
import Element.Other.Sensor;
import Element.Piece.Piece;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier
 */
public class TestConveyorBelt {
    public static void main(String [] args) {
        Piece p1 = new Piece();
        p1.setPosition(0);
        p1.setType(Piece.PieceType.axis);
        Piece p2 = new Piece();
        p2.setPosition(1);
        p2.setType(Piece.PieceType.gear);
        Sensor s = new Sensor();
        
        final ConveyorBelt cb = new ConveyorBelt(1, 10, 50);
        s.setAssociatedContainer(cb);
        s.setPositionInBelt(50);
        cb.addSensor(s);
        cb.addPiece(p1);
        cb.addPiece(p2);
        cb.startBelt();

        Thread t = new Thread(new Runnable() {
            public void run() {
                cb.run();
            }
        });
        t.start();
        
        try {
            Thread.sleep(1000);
            cb.stopBelt();
        } catch (InterruptedException ex) {
            Logger.getLogger(TestConveyorBelt.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
