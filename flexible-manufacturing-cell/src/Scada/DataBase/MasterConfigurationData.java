
package Scada.DataBase;

/**
 * Defines the instance that stores all the configuration parameters of the application
 * @author Echoplex
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
    
    /** Constructor */
    public  MasterConfigurationData(){}

    /**
     * Sets the clock cycle parameters
     * @param _clockCycleTime New clock cycle value
     */
    public void setClockCycleTime(int _clockCycleTime) {
        this._clockCycleTime = _clockCycleTime;
    }
    
    /**
     * Sets the sensor range parameter
     * @param _sensorRange New sensor range value
     */
    public void setSensorRange(int _sensorRange){
        this._sensorRange = _sensorRange;
    }
    
    /**
     * Sets the piece size parameter
     * @param _pieceSize New piece size value
     */
    public void setPieceSize(int _pieceSize){
        this._pieceSize = _pieceSize;
    }

    /**
     * Sets a new instance for the Robot1 configuration parameters
     * @param _robot1ConfigurationData New instance of Robot1 Parameter Set
     */
    public void setRobot1ConfigurationData(Robot1ConfigurationData _robot1ConfigurationData) {
        this._robot1ConfigurationData = _robot1ConfigurationData;
    }
    
    /**
     * Sets a new instance for the Robot2 configuration prameters
     * @param _robot2ConfigurationData New instance of Robot2 Parameter Set
     */
    public void setRobot2ConfigurationData(Robot2ConfigurationData _robot2ConfigurationData){
        this._robot2ConfigurationData = _robot2ConfigurationData;
    }

    /**
     * Sets a new instance for the Slave1 configuration parameters
     * @param _slave1ConfigurationData New instance of Slave1 parameter set
     */
    public void setSlave1ConfigurationData(Slave1ConfigurationData _slave1ConfigurationData) {
        this._slave1ConfigurationData = _slave1ConfigurationData;
    }

    /**
     * Sets a new instance of the Slave2 configuration parameters
     * @param _slave2ConfigurationData New instance of Slave2 parameter set
     */
    public void setSlave2ConfigurationData(Slave2ConfigurationData _slave2ConfigurationData) {
        this._slave2ConfigurationData = _slave2ConfigurationData;
    }

    /**
     * Sets a new instance of the Slave3 configuration parameters
     * @param _slave3ConfigurationData New instance of Slave3 parameter set
     */
    public void setSlave3ConfigurationData(Slave3ConfigurationData _slave3ConfigurationData) {
        this._slave3ConfigurationData = _slave3ConfigurationData;
    }
}
