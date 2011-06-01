/* Slave 3 code */
package test.automata;

import Automaton.Slaves.Slave3;

public class Slave3Test extends Slave3 {


    TestAutomata test;
    public Slave3Test() {
        super();
    }

    public void reportToMaster(int i) {
    }

    public void orderToRobot(int i) {
        test.sendToRobot1(i);
        test.sendToRobot2(i);
        test.sendToSlave1(i);
        test.sendToSlave2(i);
    }
    
    public void setTest(TestAutomata test) {
        this.test = test;
    }
}