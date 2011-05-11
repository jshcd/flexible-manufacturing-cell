package Automaton.Slaves;

import Auxiliar.MailBox;

public class SlaveMailBox extends MailBox {

    @Override
    public void startConnection(MailBox destiny) {
        throw new UnsupportedOperationException();
    }

    public void endConnection(MailBox destiny) {
        throw new UnsupportedOperationException();
    }

    public void acceptConnection() {
        throw new UnsupportedOperationException();
    }

    public void sendCommand() {
        throw new UnsupportedOperationException();
    }

    public void receiveCommand(int command) {
        throw new UnsupportedOperationException();
    }
}