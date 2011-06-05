/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Scada.DataBase;

/**
 *
 * @author Ringare
 */
public class MasterConfigurationData {
    public Slave1ConfigurationData _slave1ConfigurationData;
    public Slave2ConfigurationData _slave2ConfigurationData;
    public Slave3ConfigurationData _slave3ConfigurationData;
    public Robot1ConfigurationData _robot1ConfigurationData;
    public Robot2ConfigurationData _robot2ConfigurationData;
    public int _clockCycleTime;
    public int _sensorRange;
    public int _pieceSize;
    
    public  MasterConfigurationData(){
    }

    public void setClockCycleTime(int _clockCycleTime) {
        this._clockCycleTime = _clockCycleTime;
    }
    
    public void setSensorRange(int _sensorRange){
        this._sensorRange = _sensorRange;
    }
    
    public void setPieceSize(int _pieceSize){
        this._pieceSize = _pieceSize;
    }

    public void setRobot1ConfigurationData(Robot1ConfigurationData _robot1ConfigurationData) {
        this._robot1ConfigurationData = _robot1ConfigurationData;
    }
    
    public void setRobot2ConfigurationData(Robot2ConfigurationData _robot2ConfigurationData){
        this._robot2ConfigurationData = _robot2ConfigurationData;
    }

    public void setSlave1ConfigurationData(Slave1ConfigurationData _slave1ConfigurationData) {
        this._slave1ConfigurationData = _slave1ConfigurationData;
    }

    public void setSlave2ConfigurationData(Slave2ConfigurationData _slave2ConfigurationData) {
        this._slave2ConfigurationData = _slave2ConfigurationData;
    }

    public void setSlave3ConfigurationData(Slave3ConfigurationData _slave3ConfigurationData) {
        this._slave3ConfigurationData = _slave3ConfigurationData;
    }


    
}
