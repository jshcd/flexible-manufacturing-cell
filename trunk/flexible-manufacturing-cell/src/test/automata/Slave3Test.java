/* Slave 3 code */
package test.automata;

import Automaton.Slaves.Slave3;
import Auxiliar.IOInterface;
import Auxiliar.IOProcess;

public class Slave3Test extends Slave3 implements IOProcess{

    IOInterface ioi;

    public Slave3Test() {
        super();
        ioi = new IOInterface();
        ioi.setProcess(this);
        ioi.setPortLag(2);
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
//        System.out.println("S3 running: " + command);
        super.runCommand(command);
    }
}