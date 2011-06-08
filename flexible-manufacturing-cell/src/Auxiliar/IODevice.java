
package Auxiliar;

/**
 * Interface class that is used in to connect elements in the cell
 * @author Echoplex
 */
public interface IODevice {

    /**
     * Starts the connection
     */
    public void startConnection();

    /**
     * Finishes the connection
     */
    public void endConnection();

    /**
     * Accepts a connection
     */
    public void acceptConnection();

    /**
     * Sends a command passing a short
     * @param command 
     */
    public void sendCommand(short command);

    /**
     * Receives a command
     */
    public void receiveCommand();

    /**
     * @return Returns the IO device ID
     */
    public String getId();
}
