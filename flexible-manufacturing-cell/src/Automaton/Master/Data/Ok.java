/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Automaton.Master.Data;

import Auxiliar.MailboxData;
import java.io.Serializable;

/**
 *
 * @author Javier
 */
public class Ok extends MailboxData implements Serializable {
    private boolean _ok;

    public Ok() {
        _ok = true;
    }

    public void setOk(boolean _ok) {
        this._ok = _ok;
    }

    public boolean getOk() {
        return _ok;
    }
}
