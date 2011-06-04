/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Scada.DataBase;

/**
 *
 * @author Ringare
 */
public class Robot1ConfigurationData {
    private int _pickAndPlaceGearTime;
    private int _pickAndPlaceAxisTime;
    private int _pickAndPlaceAssemblyTime;

    public Robot1ConfigurationData(int _pickAndPlaceGearTime, int _pickAndPlaceAxisTime, 
            int _pickAndPlaceAssemblyTime) {
        this._pickAndPlaceGearTime = _pickAndPlaceGearTime;
        this._pickAndPlaceAxisTime = _pickAndPlaceAxisTime;
        this._pickAndPlaceAssemblyTime = _pickAndPlaceAssemblyTime;
    }

    public int getPickAndPlaceAssemblyTime() {
        return _pickAndPlaceAssemblyTime;
    }

    public void setPickAndPlaceAssemblyTime(int _pickAndPlaceAssemblyTime) {
        this._pickAndPlaceAssemblyTime = _pickAndPlaceAssemblyTime;
    }

    public int getPickAndPlaceAxisTime() {
        return _pickAndPlaceAxisTime;
    }

    public void setPickAndPlaceAxisTime(int _pickAndPlaceAxisTime) {
        this._pickAndPlaceAxisTime = _pickAndPlaceAxisTime;
    }

    public int getPickAndPlaceGearTime() {
        return _pickAndPlaceGearTime;
    }

    public void setPickAndPlaceGearTime(int _pickAndPlaceGearTime) {
        this._pickAndPlaceGearTime = _pickAndPlaceGearTime;
    }
}
