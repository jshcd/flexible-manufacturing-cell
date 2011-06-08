
package Auxiliar;

/**
 * Class that defines a command based in a serialized father
 * @author Echoplex
 */
public class Command extends MailboxData {
    
    /**
     * Command number
     * @see Constants
     */
    private int _command;

    /**
     * Constructor
     * @param command Command number
     */
    public Command(int command){
        _command = command;
    }

    /**
     * Sets a new value for the command
     * @param command New comand value
     */
    public void setCommand(int command){
        _command = command;
    }

    /**
     * @return Command number value
     */
    public int getCommand(){
        return _command;
    }
}
