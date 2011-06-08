package Auxiliar;


/**
 * Class that uses the Interface of 16 bits
 * @author Echoplex
 */
public interface IOProcess {
    
    /**
     * Sends a command
     * @param command Command identifier 
     */
    public void sendCommand(int command);
      /**
       * runs the command
       * @param command Command identifier
       */
    public void runCommand(int command);
}
