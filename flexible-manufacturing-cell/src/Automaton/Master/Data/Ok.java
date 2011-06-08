package Automaton.Master.Data;

import Auxiliar.MailboxData;
import java.io.Serializable;

/**
 * Defines the Ok packet transmitted by the slaves and the master
 * when a connection has arrived correctly and a packet has ben received
 * @author Echoplex
 */
public class Ok extends MailboxData implements Serializable {
    
    /**
     * Ok Value
     */
    private boolean _ok;

    /**
     * Constructor
     */
    public Ok() {
        _ok = true;
    }

    /**
     * Sets the value of the packet
     * @param _ok Value of the packet
     */
    public void setOk(boolean _ok) {
        this._ok = _ok;
    }

    /**
     * @return TRUE if the packet has arrived
     */
    public boolean getOk() {
        return _ok;
    }
}
