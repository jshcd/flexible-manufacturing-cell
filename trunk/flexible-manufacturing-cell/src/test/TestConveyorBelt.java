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
import test.automata.NoCommSlave;
import test.automata.Slave1Test;

/**
 *
 * @author Javier
 */
public class TestConveyorBelt {

    public static void main(String[] args) {
        Piece p1 = new Piece();
        p1.setPosition(0);
        p1.setType(Piece.PieceType.axis);
        Piece p2 = new Piece();
        p2.setPosition(1);
        p2.setType(Piece.PieceType.gear);
        final Sensor s = new Sensor();

        NoCommSlave s1 = new NoCommSlave();
        s.setProcess(s1);

        final ConveyorBelt cb = new ConveyorBelt(1, 15, 5);
        s.setAssociatedContainer(cb);
        s.setPositionInBelt(5);
        cb.addSensor(s);
        cb.addPiece(p1);
        //cb.addPiece(p2);
        cb.startContainer();

        Thread t = new Thread(new Runnable() {

            public void run() {
                cb.run();
            }
        });
        t.start();

        Thread st = new Thread(new Runnable() {

            public void run() {
                s.run();
            }
        });
        st.start();

        try {
            Thread.sleep(2000);
            cb.addPiece(p2);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestConveyorBelt.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        try {
            Thread.sleep(2500);
            cb.removeLastPiece();
        } catch (InterruptedException ex) {
            Logger.getLogger(TestConveyorBelt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
