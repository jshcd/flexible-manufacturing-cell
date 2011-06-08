/* Interface representing a slave */
package Automaton.Slaves;

import Auxiliar.MailboxData;
import Scada.DataBase.MasterConfigurationData;
import java.io.IOException;

/**
 * Defines the interface of a slave
 * @author Echoplex
 */
public interface Slave {

    /**
     * Starts the slave execution
     */
    public void start();

    /**
     * Activates de emergency stop
     */
    public void emergencyStop();

    /**
     * Activates de normal stop
     */
    public void normalStop();

    /**
     * Runs a command
     * @param command Command identifier
     */
    public void runCommand(int command);

    /**
     * Sends and update to the master
     * @param data Data that has been changed
     * @throws IOException
     */
    public void reportToMaster(MailboxData data) throws IOException;

    /**
     * Sends a command
     * @param i Command identifier
     */
    public void sendCommand(int i);

    /**
     * Starts the communication with the serve
     */
    public void startServer();

    /**
     * Stores initial configuration for the slave
     * @param m Configuration parameters
     */
    public void storeInitialConfiguration(MasterConfigurationData m);

    /**
     * Update data that has been changed
     */
    public void updateStatusData();
}
