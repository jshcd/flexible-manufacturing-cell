/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Scada.DataBase;

import java.io.Serializable;

/**
 *
 * @author Ringare
 */
public class Slave3ConfigurationData implements Serializable {
    public BeltConfigurationData _acceptedBelt;
    public BeltConfigurationData _notAcceptedBelt;
    
    public  Slave3ConfigurationData(){}

    public Slave3ConfigurationData(BeltConfigurationData _acceptedBelt, BeltConfigurationData _notAcceptedBelt) {
        this._acceptedBelt = _acceptedBelt;
        this._notAcceptedBelt = _notAcceptedBelt;
    }

    public BeltConfigurationData getNotAcceptedBelt() {
        return _notAcceptedBelt;
    }

    public void setNotAcceptedBelt(BeltConfigurationData _notAcceptedBelt) {
        this._notAcceptedBelt = _notAcceptedBelt;
    }

    public BeltConfigurationData getAcceptedBelt() {
        return _acceptedBelt;
    }

    public void setAcceptedBelt(BeltConfigurationData _acceptedBelt) {
        this._acceptedBelt = _acceptedBelt;
    }
}
