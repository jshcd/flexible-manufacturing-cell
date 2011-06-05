/* Interface representing a slave */

package Automaton.Slaves;

import Scada.DataBase.MasterConfigurationData;

public interface Slave {
    public void start();

    public void stop();
    
    public void runCommand(int command);

    public void reportToMaster(int i);
    
    public void orderToRobot(int i);

    public void startServer();
    
    public void storeInitialConfiguration(MasterConfigurationData m);
}