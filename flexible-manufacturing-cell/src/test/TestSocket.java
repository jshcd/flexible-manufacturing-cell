/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import Automaton.Master.MasterMailBox;
import Automaton.Slaves.SlaveMailBox;
import Element.Robot.RobotMailBox;

/**
 *
 * @author Javier
 */
public class TestSocket {
    public static void main(String [] args) {
        RobotMailBox r1mb = new RobotMailBox(1);
        SlaveMailBox s1mb = new SlaveMailBox(1);
        MasterMailBox mmb = new MasterMailBox();
        mmb.startConnection();
        //System.out.println(1);
        s1mb.startConnection(mmb);
        //System.out.println(2);
        s1mb.acceptConnection();
        //System.out.println(3);

        r1mb.startConnection(mmb);
        r1mb.acceptConnection();

        mmb.acceptConnection();
        //System.out.println(4);
        short a = 231;
        short d = 255;
        //System.out.println(5);
        s1mb.sendCommand(a);
        r1mb.sendCommand(d);
        System.out.println(6);
        mmb.receiveCommand();
        mmb.receiveCommand();
        //System.out.println(7);
        short b = 5;
        short c = 6;
        //System.out.println(8);
        mmb.sendCommand(b);
        mmb.sendCommand(c);
        //System.out.println(9);
        r1mb.receiveCommand();
        s1mb.receiveCommand();

        //System.out.println(10);
    }
}
