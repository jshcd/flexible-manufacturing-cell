/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.automata;

import Element.Robot.Robot2;

/**
 *
 * @author Portatil
 */
public class TestAutomata {

    Slave1Test s1;
    Slave2Test s2;
    Slave3Test s3;
    Robot1Test r1;
    Robot2Test r2;

    public static void main(String args[]) {
        final TestAutomata ta = new TestAutomata();

    }

    public TestAutomata() {
        s1 = new Slave1Test();
        s2 = new Slave2Test();
        s3 = new Slave3Test();
        r2 = new Robot2Test();
        
        s1.setTest(this);
        s1.initialize();
        
        Thread t = new Thread(new Runnable() {

            public void run() {
                s1.start();
            }
        });
        t.start();

    }

    public void sendToSlave1(int command) {
        s1.runCommand(command);
    }

    public void sendToSlave2(int command) {
        s2.runCommand(command);
    }

    public void sendToSlave3(int command) {
        s3.runCommand(command);
    }

    public void sendToRobot1(int command) {
        s1.getRobot().runCommand(command);
    }

    public void sendToRobot2(int command) {
        r2.runCommand(command);
    }
}
