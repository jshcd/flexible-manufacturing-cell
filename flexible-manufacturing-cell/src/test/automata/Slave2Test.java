/* Slave 2 code */
package test.automata;

import Automaton.Slaves.Slave2;
import Auxiliar.IOInterface;
import Auxiliar.IOProcess;

public class Slave2Test extends Slave2 implements IOProcess{
    
    
    IOInterface ioi;

    public Slave2Test() {
        super();
        ioi = new IOInterface();
        ioi.setProcess(this);
        ioi.setPortLag(1);
        ioi.bind();
        (new Thread(ioi)).start();
    }

    public void reportToMaster(int i) {
        sendCommand(i);
    }

    public void orderToRobot(int i) {
        sendCommand(i);
    }

    public void sendCommand(int command) {
        ioi.send((short)command);
    }
    @Override
    public void runCommand (int command){
//        System.out.println("S2 running: " + command);
        super.runCommand(command);
    }
}