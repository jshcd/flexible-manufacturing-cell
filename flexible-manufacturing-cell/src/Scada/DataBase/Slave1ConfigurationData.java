/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Scada.DataBase;

/**
 *
 * @author Ringare
 */
public class Slave1ConfigurationData {
    
    public BeltConfigurationData _gearBeltConfiguration;
    public BeltConfigurationData _axisBeltConfiguration;
    public int _activationTime;
    
    public  Slave1ConfigurationData(){
      
    }

    public Slave1ConfigurationData(BeltConfigurationData _gearBeltConfiguration, BeltConfigurationData _axisBeltConfiguration, int _activationTime) {
        this._gearBeltConfiguration = _gearBeltConfiguration;
        this._axisBeltConfiguration = _axisBeltConfiguration;
        this._activationTime = _activationTime;
    }



    public void setActivationTime(int _activationTime) {
        this._activationTime = _activationTime;
    }

    public void setAxisBeltConfiguration(BeltConfigurationData _axisBeltConfiguration) {
        this._axisBeltConfiguration = _axisBeltConfiguration;
    }

    public void setGearBeltConfiguration(BeltConfigurationData _gearBeltConfiguration) {
        this._gearBeltConfiguration = _gearBeltConfiguration;
    }


    
}
