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
    
    public int _weldingActivationTime;
    public int _qualityControlActivationTime;
    
    public  Slave2ConfigurationData(){}

    public Slave2ConfigurationData(int _weldingActivationTime, int _qualityControlActivationTime) {
        this._weldingActivationTime = _weldingActivationTime;
        this._qualityControlActivationTime = _qualityControlActivationTime;
    }

    public int getQualityControlActivationTime() {
        return _qualityControlActivationTime;
    }

    public void setQualityControlActivationTime(int _qualityControlActivationTime) {
        this._qualityControlActivationTime = _qualityControlActivationTime;
    }

    public int getWeldingActivationTime() {
        return _weldingActivationTime;
    }

    public void setWeldingActivationTime(int _weldingActivationTime) {
        this._weldingActivationTime = _weldingActivationTime;
    }
    
}
