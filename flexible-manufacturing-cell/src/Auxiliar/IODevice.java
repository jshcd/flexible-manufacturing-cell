/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auxiliar;

/**
 *
 * @author Javier
 */
public interface IODevice {

    public void startConnection();

    public void endConnection();

    public void acceptConnection();

    public void sendCommand(short command);

    public void receiveCommand();

    public String getId();
}
