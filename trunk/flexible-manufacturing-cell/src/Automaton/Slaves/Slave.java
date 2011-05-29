/* Interface representing a slave */

package Automaton.Slaves;

public interface Slave {
    public void start();

    public void stop();
    
    public void runCommand(int command);

    public void reportToMaster();
    
    public void orderToRobot(int i);

    public void startServer();
}