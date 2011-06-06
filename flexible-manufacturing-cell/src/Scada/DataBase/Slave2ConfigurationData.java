
package Scada.DataBase;

import java.io.Serializable;

/**
 * Defines the configuration parameters for the Slave2
 * @author Echoplex
 */
public class Slave2ConfigurationData implements Serializable {
    
    /**
     * Welding Station activation time
     */
    public int _weldingActivationTime;
    
    /**
     * Quality control station activation time
     */
    public int _qualityControlActivationTime;
    
    /**
     * Constructor
     */
    public  Slave2ConfigurationData(){}

    /**
     * Constructor
     * @param _weldingActivationTime New value for the welding station activation time
     * @param _qualityControlActivationTime New value for the quality station activation time
     */
    public Slave2ConfigurationData(int _weldingActivationTime, int _qualityControlActivationTime) {
        this._weldingActivationTime = _weldingActivationTime;
        this._qualityControlActivationTime = _qualityControlActivationTime;
    }

    /**
     * @return The Quality Control station activation time 
     */
    public int getQualityControlActivationTime() {
        return _qualityControlActivationTime;
    }

    /**
     * Sets the new value for the quality control station activation time
     * @param _qualityControlActivationTime 
     */
    public void setQualityControlActivationTime(int _qualityControlActivationTime) {
        this._qualityControlActivationTime = _qualityControlActivationTime;
    }

    /**
     * @return The Welding Station activation time 
     */
    public int getWeldingActivationTime() {
        return _weldingActivationTime;
    }

    /**
     * Sets the new value for the welding station activation time
     * @param _weldingActivationTime 
     */
    public void setWeldingActivationTime(int _weldingActivationTime) {
        this._weldingActivationTime = _weldingActivationTime;
    }    
}
