/* 
 * Interface for mailboxes. It's objective is establishing connections with other mailboxes (RMI)
 * and sending and receiving commands (as ints).
 */


package Auxiliar;

import java.io.IOException;

public interface MailBox {
    
    public void startConnection();

    public void endConnection();

    public void acceptConnection();

    public void sendCommand(MailboxData command) throws IOException;

    public void receiveCommand() throws IOException;

    public String getId();
}