/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Scada.DataBase;

/**
 *
 * @author David
 */
public class Robot2ConfigurationData {
    
    private int _pickAndTransportAssemblyTime;
    private int _pickAndTransportWeldedAssemblyTime;
    private int _pickAndTransportCheckedAssemblyTime;

    public Robot2ConfigurationData(int _pickAndTransportAssemblyTime, 
            int _pickAndTransportWeldedAssemblyTime, 
            int _pickAndTransportCheckedAssemblyTime) {
        this._pickAndTransportAssemblyTime = _pickAndTransportAssemblyTime;
        this._pickAndTransportWeldedAssemblyTime = _pickAndTransportWeldedAssemblyTime;
        this._pickAndTransportCheckedAssemblyTime = _pickAndTransportCheckedAssemblyTime;
    }

    public int getPickAndTransportAssemblyTime() {
        return _pickAndTransportAssemblyTime;
    }

    public void setPickAndTransportAssemblyTime(int _pickAndTransportAssemblyTime) {
        this._pickAndTransportAssemblyTime = _pickAndTransportAssemblyTime;
    }

    public int getPickAndTransportCheckedAssemblyTime() {
        return _pickAndTransportCheckedAssemblyTime;
    }

    public void setPickAndTransportCheckedAssemblyTime(int _pickAndTransportCheckedAssemblyTime) {
        this._pickAndTransportCheckedAssemblyTime = _pickAndTransportCheckedAssemblyTime;
    }

    public int getPickAndTransportWeldedAssemblyTime() {
        return _pickAndTransportWeldedAssemblyTime;
    }

    public void setPickAndTransportWeldedAssemblyTime(int _pickAndTransportWeldedAssemblyTime) {
        this._pickAndTransportWeldedAssemblyTime = _pickAndTransportWeldedAssemblyTime;
    }
    
    
}
