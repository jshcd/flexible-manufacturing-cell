/* 
 * Interface for mailboxes. It's objective is establishing connections with other mailboxes (RMI)
 * and sending and receiving commands (as ints).
 */


package Auxiliar;

public abstract class MailBox {

	public void startConnection(MailBox destiny){
            
        }

	public void endConnection(MailBox destiny){
            
        }

	public void acceptConnection(){
            
        }

	public void sendCommand(){
            
        }

	public void receiveCommand(int command){
            
        }
}