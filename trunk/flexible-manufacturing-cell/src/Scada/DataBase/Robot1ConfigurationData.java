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
    private int _assemblyTime;
    private int _weldingTime;
    private int _qualityControlTime;
    
    public Robot1ConfigurationData(int pickAndPlaceGearTime, int pickAndPlaceAxisTime, 
            int pickAndPlaceAssemblyTime, int assemblyTime, int weldingTime, int qualityControlTime){
        _pickAndPlaceGearTime = pickAndPlaceGearTime;
        _pickAndPlaceAxisTime = pickAndPlaceAxisTime;
        _pickAndPlaceAssemblyTime = pickAndPlaceAssemblyTime;
        _assemblyTime = assemblyTime;
        _weldingTime = weldingTime;
        _qualityControlTime = qualityControlTime;
    }
    
    public int getPickAndPlaceGearTime(){
        return _pickAndPlaceGearTime;
    }
    
    public int getPickAndPlaceAxisTime(){
        return _pickAndPlaceAxisTime;
    }
    
    public int getPickAndPlaceAssemblyTime(){
        return _pickAndPlaceAssemblyTime;
    }
    
    public int getAssemblyTime(){
        return _assemblyTime;
    }
    
    public int getWeldingTime(){
        return _weldingTime;
    }
    
    public int getQualityControlTime(){
        return _qualityControlTime;
    }
    
    public void setPickAndPlaceGearTime(int pickAndPlaceGearTime){
        _pickAndPlaceGearTime = pickAndPlaceGearTime;
    }
    
    public void setPickAndPlaceAxisTime(int pickAndPlaceAxisTime){
        _pickAndPlaceAxisTime = pickAndPlaceAxisTime;
    }
    
    public void setPickAndPlaceAssemblyTime(int pickAndPlaceAssemblyTime){
        _pickAndPlaceAssemblyTime = pickAndPlaceAssemblyTime;
    }
    
    public void setAssemblyTime(int assemblyTime){
        _assemblyTime = assemblyTime;
    }
    
    public void setWeldingTime(int weldingTime){
        _weldingTime = weldingTime;
    }
    
    public void setQualityControlTime(int qualityControlTime){
        _qualityControlTime = qualityControlTime;
    }
}
