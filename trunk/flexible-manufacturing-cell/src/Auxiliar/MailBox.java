/* 
 * Interface for mailboxes. It's objective is establishing connections with other mailboxes (RMI)
 * and sending and receiving commands (as ints).
 */


package Auxiliar;

import Automaton.Slaves.Data.SlaveData;

public interface MailBox {
    
    public void startConnection();

    public void endConnection();

    public void acceptConnection();

    public void sendCommand(SlaveData command);

    public void receiveCommand();

    public String getId();
}