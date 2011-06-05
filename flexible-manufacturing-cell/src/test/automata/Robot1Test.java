package test.automata;

import Auxiliar.IOInterface;
import Auxiliar.IOProcess;
import Element.Robot.Robot1;

public class Robot1Test extends Robot1 implements IOProcess{
    
    IOInterface ioi;

    public Robot1Test() {
        super();
        ioi = new IOInterface();
        ioi.setProcess(this);
        ioi.setPortLag(3);
        ioi.bind();
        (new Thread(ioi)).start();
    }

    public void reportProcess(int command) {
        sendCommand(command);
        System.out.println("R1 " + command);
        
    }

    public void sendCommand(int command) {
        ioi.send((short)command);
    }
    @Override
    public void runCommand (int command){
//        System.out.println("R1 running: " + command);
        super.runCommand(command);
    }
}