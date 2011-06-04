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
    public BeltConfigurationData _weldingBeltConfiguration;
    public int _assemblyActivationTime;
    
    public  Slave1ConfigurationData(){
      
    }

    public Slave1ConfigurationData(BeltConfigurationData _gearBeltConfiguration, 
            BeltConfigurationData _axisBeltConfiguration, 
            BeltConfigurationData _weldingBeltConfiguration,
            int _assemblyActivationTime) {
        this._gearBeltConfiguration = _gearBeltConfiguration;
        this._axisBeltConfiguration = _axisBeltConfiguration;
        this._weldingBeltConfiguration = _weldingBeltConfiguration;
        this._assemblyActivationTime = _assemblyActivationTime;
    }

    public int getAssemblyActivationTime() {
        return _assemblyActivationTime;
    }

    public void setAssemblyActivationTime(int _assemblyActivationTime) {
        this._assemblyActivationTime = _assemblyActivationTime;
    }

    public BeltConfigurationData getAxisBeltConfiguration() {
        return _axisBeltConfiguration;
    }

    public void setAxisBeltConfiguration(BeltConfigurationData _axisBeltConfiguration) {
        this._axisBeltConfiguration = _axisBeltConfiguration;
    }

    public BeltConfigurationData getGearBeltConfiguration() {
        return _gearBeltConfiguration;
    }

    public void setGearBeltConfiguration(BeltConfigurationData _gearBeltConfiguration) {
        this._gearBeltConfiguration = _gearBeltConfiguration;
    }

    public BeltConfigurationData getWeldingBeltConfiguration() {
        return _weldingBeltConfiguration;
    }

    public void setWeldingBeltConfiguration(BeltConfigurationData _weldingBeltConfiguration) {
        this._weldingBeltConfiguration = _weldingBeltConfiguration;
    }
    
}
