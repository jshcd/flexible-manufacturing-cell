/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import Automaton.Master.Master;
import Automaton.Slaves.Slave1;

/**
 *
 * @author Javier
 */
public class TestIODevices {
    public static void main(String [] args) {
        Slave1 slave = new Slave1();
        slave.initialize();

        Thread t = new Thread(new Runnable() {
            public void run() {
               Master m = new Master();
               m.startServer();
            }
        });
        t.start();
    }
}
