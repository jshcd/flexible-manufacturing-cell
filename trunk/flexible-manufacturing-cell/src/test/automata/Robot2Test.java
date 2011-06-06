package test.automata;

import Auxiliar.IOInterface;
import Auxiliar.IOProcess;
import Element.Robot.Robot2;

public class Robot2Test extends Robot2 implements IOProcess{
    
    IOInterface ioi;

    public Robot2Test() {
        super();
        ioi = new IOInterface();
        ioi.setProcess(this);
        ioi.setPortLag(4);
        ioi.bind();
        (new Thread(ioi)).start();
    }

    public void sendCommand(int command) {
        sendCommand(command);
//        System.out.println("R2 " + command);
        
    }

    @Override
    public void runCommand (int command){
//        System.out.println("R2 running: " + command);
        super.runCommand(command);
    }
}