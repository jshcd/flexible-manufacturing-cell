/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auxiliar;

/**
 *
 * @author Javier
 */
public class Command extends MailboxData {
    private int _command;

    public Command(int command){
        _command = command;
    }

    public void setCommand(int command){
        _command = command;
    }

    public int getCommand(){
        return _command;
    }
}
