/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import Automaton.Slaves.Data.Slave1Data;
import Automaton.Slaves.SlaveMailBox;
import Scada.Communication.Server;


/**
 *
 * @author Javier
 */
public class TestSocket {
    public static void main(String [] args) {
        SlaveMailBox s1mb = new SlaveMailBox(1);

        Thread t = new Thread(new Runnable() {
            public void run() {
                Server s = new Server();
                s.start();
            }
        });
        t.start();

        s1mb.startConnection();
        s1mb.acceptConnection();
        Slave1Data a = new Slave1Data();

        SlaveMailBox s2mb = new SlaveMailBox(2);
        s2mb.startConnection();
        //System.out.println(5);
        s1mb.sendCommand(a);
        s2mb.acceptConnection();
        //System.out.println(7);
        s2mb.sendCommand(a);
        s1mb.receiveCommand();
        s2mb.receiveCommand();

        //System.out.println(10);
    }
}
