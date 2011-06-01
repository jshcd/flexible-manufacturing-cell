package test.automata;

import Element.Robot.Robot2;

    
public class Robot2Test extends Robot2 {

    TestAutomata test;

    public Robot2Test() {
        super();
    }

    
    public void reportProcess(int command) {
        test.sendToRobot1(command);
        test.sendToRobot2(command);
        test.sendToSlave1(command);
        test.sendToSlave2(command);
        test.sendToSlave3(command);
        
    }

    public void setTest(TestAutomata test) {
        this.test = test;
    }
}