/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Scada.DataBase;

/**
 *
 * @author Ringare
 */
public class Slave3ConfigurationData {
    public BeltConfigurationData _qualityControlBelt;
    public BeltConfigurationData _notAcceptedBelt;
    public int _activationTime;
    
    public  Slave3ConfigurationData(){}

    public Slave3ConfigurationData(BeltConfigurationData _qualityControlBelt, BeltConfigurationData _notAcceptedBelt, int _activationTime) {
        this._qualityControlBelt = _qualityControlBelt;
        this._notAcceptedBelt = _notAcceptedBelt;
        this._activationTime = _activationTime;
    }

    
}
