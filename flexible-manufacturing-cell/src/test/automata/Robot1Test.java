package test.automata;

import Element.Robot.Robot1;

public class Robot1Test extends Robot1 {

    TestAutomata test;

    public Robot1Test() {
        super();
    }

    public void reportProcess(int command) {
        test.sendToRobot1(command);
        test.sendToRobot2(command);
        test.sendToSlave1(command);
        test.sendToSlave2(command);
        test.sendToSlave3(command);
        System.out.println("R1 " + command);
        
    }
    public void setTest(TestAutomata test) {
        this.test = test;
    }

}