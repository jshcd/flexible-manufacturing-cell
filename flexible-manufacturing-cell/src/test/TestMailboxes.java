/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import Automaton.Master.Data.Ok;
import Automaton.Master.MasterMailBox;
import Automaton.Slaves.Data.Slave1Data;
import Automaton.Slaves.Data.Slave2Data;
import Automaton.Slaves.Data.Slave3Data;
import Automaton.Slaves.SlaveMailBox;
import Element.Robot.Data.RobotData;
import Element.Robot.RobotMailBox;


/**
 *
 * @author Javier
 */
public class TestMailboxes {
    public static void main(String [] args) {
        SlaveMailBox s1mb = new SlaveMailBox(1);
        SlaveMailBox s2mb = new SlaveMailBox(2);
        SlaveMailBox s3mb = new SlaveMailBox(3);
        RobotMailBox r2mb = new RobotMailBox(2);
        final MasterMailBox mmb = new MasterMailBox();
        Slave1Data a = new Slave1Data();
        Slave2Data b = new Slave2Data();
        Slave3Data c = new Slave3Data();
        RobotData d = new RobotData();

        Thread t = new Thread(new Runnable() {
            public void run() {
                mmb.startConnection();
                mmb.acceptConnection();
                while(true){
                    Thread t1 = new Thread(new Runnable() {
                        public void run() {
                            mmb.receiveCommand();
                            Ok ok = new Ok();
                            //Check if data received is correct and if it is, to send
                            //an ok command. Otherwise, it would make the ok value
                            //false
                            mmb.sendCommand(ok);
                        }
                    });
                    t1.start();
                }
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
