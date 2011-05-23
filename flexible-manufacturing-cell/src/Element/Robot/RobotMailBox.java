package Element.Robot;

import Auxiliar.MailBox;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RobotMailBox implements MailBox {
    private Robot _robot;
    private String _id;
    private ServerSocket _providerSocket;
    private Socket _connection = null;
    private ObjectOutputStream _out;
    private ObjectInputStream _in;
    private short _message;

        public RobotMailBox(int id){
            _id = "Robot"+id;
        }

	public void startConnection(MailBox destiny) {
		throw new UnsupportedOperationException();
	}

	public void endConnection(MailBox destiny) {
		throw new UnsupportedOperationException();
	}

	public void acceptConnection() {
		throw new UnsupportedOperationException();
	}

	public void sendCommand(short command) {
		throw new UnsupportedOperationException();
	}

	public void receiveCommand() {
		throw new UnsupportedOperationException();
	}
}