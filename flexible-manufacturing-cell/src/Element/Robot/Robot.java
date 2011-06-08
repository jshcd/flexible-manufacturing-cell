package Element.Robot;

/**
 * Defines an interface for the Robot
 * @author Echoplex
 */
public interface Robot {

    /**
     * Runs a command in the Robot
     * @param command New Command to run
     * @see Auxiliar.Constants
     */
    public void runCommand(int command);

    /**
     * Sends a command to someone
     * @param command New command to send
     */
    public void sendCommand(int command);

    /**
     * Starts listening through a socket
     */
    public void startServer();
}