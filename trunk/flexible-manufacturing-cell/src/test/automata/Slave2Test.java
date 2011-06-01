/* Slave 2 code */
package test.automata;

import Automaton.Slaves.Slave2;

public class Slave2Test extends Slave2 {
    
    TestAutomata test;

    public Slave2Test() {
        super();
    }

    public void reportToMaster(int i) {
    }

    public void orderToRobot(int i) {
        test.sendToRobot1(i);
        test.sendToRobot2(i);
        test.sendToSlave1(i);
        test.sendToSlave3(i);
    }
    
    
    public void setTest(TestAutomata test) {
        this.test = test;
    }
}