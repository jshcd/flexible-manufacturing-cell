package Auxiliar;

import java.io.IOException;

/**
 * Class that defines a <code>MailBox</code> and the communication between them
 * @author Echoplex
 */
public interface MailBox {

    /**
     * Starts the connection
     */
    public void startConnection();

    /**
     * Finishes the connection
     */
    public void endConnection();

    /**
     * Accepts the connection
     */
    public void acceptConnection();

    /**
     * Sends a command to another <code>MailBox</code>
     * @param command which is going to be send
     */
    public void sendCommand(MailboxData command) throws IOException;

    /**
     * Receives a response from the other <code>MailBox</code>
     */
    public void receiveCommand();

    /**
     * Returns the <code>MailBox</code> identifier
     * @return <code>MailBox</code> identifier
     */
    public String getId();
}
