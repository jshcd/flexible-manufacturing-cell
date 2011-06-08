package Scada.DataBase;

import java.io.Serializable;

/**
 * Defines the configuration parameter set for the Robot2
 * @author Echoplex
 */
public class Robot2ConfigurationData implements Serializable {

    /**
     * Stablishes the time to pick and transport an assembly
     */
    private int _pickAndTransportAssemblyTime;
    /**
     * Stablishes the time to pick and transport a Welded assembly
     */
    private int _pickAndTransportWeldedAssemblyTime;
    /**
     * Stablishes the time to pick and transport a Checked assembly
     */
    private int _pickAndTransportCheckedAssemblyTime;

    /**
     * Constructor
     * @param _pickAndTransportAssemblyTime Defines the time to pick and transport an assembly
     * @param _pickAndTransportWeldedAssemblyTime Defines the time to pick and transport a 
     * welded assembly
     * @param _pickAndTransportCheckedAssemblyTime Defines the time to pick and transport a 
     * checked assembly
     */
    public Robot2ConfigurationData(int _pickAndTransportAssemblyTime,
            int _pickAndTransportWeldedAssemblyTime,
            int _pickAndTransportCheckedAssemblyTime) {
        this._pickAndTransportAssemblyTime = _pickAndTransportAssemblyTime;
        this._pickAndTransportWeldedAssemblyTime = _pickAndTransportWeldedAssemblyTime;
        this._pickAndTransportCheckedAssemblyTime = _pickAndTransportCheckedAssemblyTime;
    }

    /**
     * @return the time to pick and transport an assembly
     */
    public int getPickAndTransportAssemblyTime() {
        return _pickAndTransportAssemblyTime;
    }

    /**
     * Sets a new value for the time to pick and transport an assembly
     * @param _pickAndTransportAssemblyTime 
     */
    public void setPickAndTransportAssemblyTime(int _pickAndTransportAssemblyTime) {
        this._pickAndTransportAssemblyTime = _pickAndTransportAssemblyTime;
    }

    /**
     * @return the time to pick and transport a checked assembly
     */
    public int getPickAndTransportCheckedAssemblyTime() {
        return _pickAndTransportCheckedAssemblyTime;
    }

    /**
     * Sets a new value for the time to pick and transport an assembly
     * @param _pickAndTransportCheckedAssemblyTime 
     */
    public void setPickAndTransportCheckedAssemblyTime(int _pickAndTransportCheckedAssemblyTime) {
        this._pickAndTransportCheckedAssemblyTime = _pickAndTransportCheckedAssemblyTime;
    }

    /**
     * @return the time to pick and transport a welded assembly 
     */
    public int getPickAndTransportWeldedAssemblyTime() {
        return _pickAndTransportWeldedAssemblyTime;
    }

    /**
     * Sets a new value for the time to pick and transport a welded assembly
     * @param _pickAndTransportWeldedAssemblyTime 
     */
    public void setPickAndTransportWeldedAssemblyTime(int _pickAndTransportWeldedAssemblyTime) {
        this._pickAndTransportWeldedAssemblyTime = _pickAndTransportWeldedAssemblyTime;
    }
}
