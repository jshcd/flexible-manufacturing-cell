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
    public RobotConfigurationData _robot1ConfigurationData;
    public RobotConfigurationData _robot2ConfigurationData;
    public int _clockCycleTime;
    
    public void MasterConfigurationData(){}   
    
}
