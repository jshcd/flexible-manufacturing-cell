/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Scada.DataBase;

/**
 *
 * @author Ringare
 */
public class Slave2ConfigurationData {
    
    public BeltConfigurationData _weldingBeltConfiguration;
    public int _activationTime;
    
    public  Slave2ConfigurationData(){}

    public Slave2ConfigurationData(BeltConfigurationData _weldingBeltConfiguration, int _activationTime) {
        this._weldingBeltConfiguration = _weldingBeltConfiguration;
        this._activationTime = _activationTime;
    }

    
    
}
