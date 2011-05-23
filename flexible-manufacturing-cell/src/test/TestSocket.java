/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import Automaton.Master.MasterMailBox;
import Automaton.Slaves.SlaveMailBox;

/**
 *
 * @author Javier
 */
public class TestSocket {
    public static void main(String [] args) {
        SlaveMailBox s1mb = new SlaveMailBox(1);
        MasterMailBox mmb = new MasterMailBox();
        s1mb.startConnection();
        mmb.startConnection(s1mb);
        mmb.acceptConnection();
        s1mb.acceptConnection();
        
        short a = 231;
        mmb.sendCommand(a);
        s1mb.receiveCommand();
        short b = 5;
        s1mb.sendCommand(b);
        mmb.receiveCommand();
    }
}
