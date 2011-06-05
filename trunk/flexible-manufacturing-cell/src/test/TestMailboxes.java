/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import Automaton.Master.Data.Ok;
import Automaton.Master.MasterInputMailBox;
import Automaton.Slaves.Data.Slave1Data;
import Automaton.Slaves.Data.Slave2Data;
import Automaton.Slaves.Data.Slave3Data;
import Automaton.Slaves.SlaveOutputMailBox;
import Element.Robot.Data.RobotData;
import Element.Robot.RobotOutputMailBox;


/**
 *
 * @author Javier
 */
public class TestMailboxes {
    public static void main(String [] args) {
        SlaveOutputMailBox s1mb = new SlaveOutputMailBox(1);
        SlaveOutputMailBox s2mb = new SlaveOutputMailBox(2);
        SlaveOutputMailBox s3mb = new SlaveOutputMailBox(3);
        RobotOutputMailBox r2mb = new RobotOutputMailBox(2);
        final MasterInputMailBox mmb = new MasterInputMailBox();
        Slave1Data a = new Slave1Data();
        Slave2Data b = new Slave2Data();
        Slave3Data c = new Slave3Data();
        RobotData d = new RobotData();

        Thread t = new Thread(new Runnable() {
            public void run() {
                mmb.startServer();
            }
        });
        t.start();

        s1mb.startConnection();
        s1mb.acceptConnection();
        
        s2mb.startConnection();
        
        s1mb.sendCommand(a);
        s2mb.acceptConnection();

        r2mb.startConnection();
        r2mb.acceptConnection();
        r2mb.sendCommand(d);
        r2mb.receiveCommand();

        s2mb.sendCommand(b);
        s1mb.receiveCommand();
        s2mb.receiveCommand();

        s3mb.startConnection();
        s3mb.acceptConnection();
        s3mb.sendCommand(c);
        s3mb.receiveCommand();
    }
}
