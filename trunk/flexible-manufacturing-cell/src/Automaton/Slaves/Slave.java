/* Interface representing a slave */

package Automaton.Slaves;

import Auxiliar.MailboxData;
import Scada.DataBase.MasterConfigurationData;
import java.io.IOException;

public interface Slave {
    public void start();

    public void stop();
    
    public void runCommand(int command);

    public void reportToMaster(MailboxData data) throws IOException;
    
    public void sendCommand(int i);

    public void startServer();
    
    public void storeInitialConfiguration(MasterConfigurationData m);
    
    public void updateStatusData();

    public void normalStop();
    
}