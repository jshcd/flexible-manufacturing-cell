/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.automata;

import Auxiliar.IOInterface;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Portatil
 */
public class IOTest {

    IOInterface io;
    IOInterface io2;
    IOInterface io3;
    IOInterface io4;
    IOInterface io5;

    public static void main(String args[]) {
        new IOTest();
    }

    public IOTest() {
        try {
            io = new IOInterface();
            io.bind();
            (new Thread(io)).start();

            io2 = new IOInterface();
            io2.setPortLag(1);
            io2.bind();
            (new Thread(io2)).start();

            io3 = new IOInterface();
            io3.setPortLag(2);
            io3.bind();
            (new Thread(io3)).start();

            io4 = new IOInterface();
            io4.setPortLag(3);
            io4.bind();
            (new Thread(io4)).start();

            io5 = new IOInterface();
            io5.setPortLag(4);
            io5.bind();
            (new Thread(io5)).start();

            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(IOTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.lang.NullPointerException e) {
        }
        io.send((short) 55);
    }
}
