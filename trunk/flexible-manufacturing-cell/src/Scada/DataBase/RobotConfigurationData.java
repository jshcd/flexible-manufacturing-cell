/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Scada.DataBase;

/**
 *
 * @author Ringare
 */
public class RobotConfigurationData {
    private int _pickingTime;
    private int _transportTimeA;
    private int _transportTimeB;
    
    public RobotConfigurationData(int pickingTime, int transportTimeA, int transportTimeB){
        _pickingTime = pickingTime;
        _transportTimeA = transportTimeA;
        _transportTimeB = transportTimeB;
    }
    
    public void setPickingTime(int pickingTime){
        _pickingTime = pickingTime;
    }
    
    public void setTransportTimeA(int transportTimeA){
        _transportTimeA = transportTimeA;
    }
    
    public void setTransportTimeB(int transportTimeB){
        _transportTimeB = transportTimeB;
    }
    
    public int getPickingTime(){
        return _pickingTime;
    }
    
    public int getTransportTimeA(){
        return _transportTimeA;
    }
    
    public int getTransportTimeB(){
        return _transportTimeB;
    }
}
