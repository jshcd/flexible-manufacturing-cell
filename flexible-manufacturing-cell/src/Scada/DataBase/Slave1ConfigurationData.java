package Scada.DataBase;

import java.io.Serializable;

/**
 * Defines the configuration parameters for the Slave1
 * @author Echoplex
 */
public class Slave1ConfigurationData implements Serializable {

    /**
     * Gear Belt configuration instance
     */
    public BeltConfigurationData _gearBeltConfiguration;
    /**
     * Axis Belt configuration instance
     */
    public BeltConfigurationData _axisBeltConfiguration;
    /**
     * Welding Belt configuration instance
     */
    public BeltConfigurationData _weldingBeltConfiguration;
    /**
     * Defines the time that the assembly station gets to activate
     */
    public int _assemblyActivationTime;

    /**
     * Constructor
     */
    public Slave1ConfigurationData() {
    }

    /**
     * Constructor
     * @param _gearBeltConfiguration New gear belt configuration
     * @param _axisBeltConfiguration New axis belt configuration
     * @param _weldingBeltConfiguration New welding belt configuration
     * @param _assemblyActivationTime New assembly station activation time
     */
    public Slave1ConfigurationData(BeltConfigurationData _gearBeltConfiguration,
            BeltConfigurationData _axisBeltConfiguration,
            BeltConfigurationData _weldingBeltConfiguration,
            int _assemblyActivationTime) {
        this._gearBeltConfiguration = _gearBeltConfiguration;
        this._axisBeltConfiguration = _axisBeltConfiguration;
        this._weldingBeltConfiguration = _weldingBeltConfiguration;
        this._assemblyActivationTime = _assemblyActivationTime;
    }

    /**
     * @return The Assembly station activation time
     */
    public int getAssemblyActivationTime() {
        return _assemblyActivationTime;
    }

    /**
     * Sets a new value for the assembly station activation time
     * @param _assemblyActivationTime 
     */
    public void setAssemblyActivationTime(int _assemblyActivationTime) {
        this._assemblyActivationTime = _assemblyActivationTime;
    }

    /**
     * @return Inscance of Axis Belt configuration
     */
    public BeltConfigurationData getAxisBeltConfiguration() {
        return _axisBeltConfiguration;
    }

    /**
     * Sets a new instance for the axis belt configuration
     * @param _axisBeltConfiguration 
     */
    public void setAxisBeltConfiguration(BeltConfigurationData _axisBeltConfiguration) {
        this._axisBeltConfiguration = _axisBeltConfiguration;
    }

    /**
     * @return Instance of Gear Belt configuration
     */
    public BeltConfigurationData getGearBeltConfiguration() {
        return _gearBeltConfiguration;
    }

    /**
     * Sets a new instance for the Gear Belt configuration
     * @param _gearBeltConfiguration 
     */
    public void setGearBeltConfiguration(BeltConfigurationData _gearBeltConfiguration) {
        this._gearBeltConfiguration = _gearBeltConfiguration;
    }

    /**
     * @return Instance of Welding Belt configuration
     */
    public BeltConfigurationData getWeldingBeltConfiguration() {
        return _weldingBeltConfiguration;
    }

    /**
     * Sets a new instance for the welding belt configuration
     * @param _weldingBeltConfiguration 
     */
    public void setWeldingBeltConfiguration(BeltConfigurationData _weldingBeltConfiguration) {
        this._weldingBeltConfiguration = _weldingBeltConfiguration;
    }
}
