
package Scada.DataBase;

/**
 * Defines the configuration parameter set for the Robot 1
 * @author Echoplex
 */
public class Robot1ConfigurationData {
    
    /**
     * Integer that stablishes the time to pick and place a gear
     */
    private int _pickAndPlaceGearTime;
    
    /**
     * Integer that stablishes the time to pick and place an axis
     */
    private int _pickAndPlaceAxisTime;
    
    /**
     * Integer that stablishes the time to pick and place an assembly
     */
    private int _pickAndPlaceAssemblyTime;

    /**
     * Constructor
     * @param _pickAndPlaceGearTime Time to pick and Place a Gear
     * @param _pickAndPlaceAxisTime Time to pick and Place an Axis
     * @param _pickAndPlaceAssemblyTime  Time to pick and Place an Assembly
     */
    public Robot1ConfigurationData(int _pickAndPlaceGearTime, int _pickAndPlaceAxisTime, 
            int _pickAndPlaceAssemblyTime) {
        this._pickAndPlaceGearTime = _pickAndPlaceGearTime;
        this._pickAndPlaceAxisTime = _pickAndPlaceAxisTime;
        this._pickAndPlaceAssemblyTime = _pickAndPlaceAssemblyTime;
    }

    /**
     * @return Time to pick and place an assembly
     */
    public int getPickAndPlaceAssemblyTime() {
        return _pickAndPlaceAssemblyTime;
    }

    /**
     * Sets a new value for the time to pick and place an assembly
     * @param _pickAndPlaceAssemblyTime 
     */
    public void setPickAndPlaceAssemblyTime(int _pickAndPlaceAssemblyTime) {
        this._pickAndPlaceAssemblyTime = _pickAndPlaceAssemblyTime;
    }

    /**
     * @return Time to pick and place an axis
     */
    public int getPickAndPlaceAxisTime() {
        return _pickAndPlaceAxisTime;
    }

    /**
     * Sets a new value for the time to pick and place an Axis
     * @param _pickAndPlaceAxisTime 
     */
    public void setPickAndPlaceAxisTime(int _pickAndPlaceAxisTime) {
        this._pickAndPlaceAxisTime = _pickAndPlaceAxisTime;
    }

    /**
     * @return Time to pick and place a gear
     */
    public int getPickAndPlaceGearTime() {
        return _pickAndPlaceGearTime;
    }

    /**
     * Sets a new value for the time to pick and place a gear
     * @param _pickAndPlaceGearTime 
     */
    public void setPickAndPlaceGearTime(int _pickAndPlaceGearTime) {
        this._pickAndPlaceGearTime = _pickAndPlaceGearTime;
    }
}
