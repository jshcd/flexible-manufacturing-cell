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
    /* Falta por incluir la configuracion del Robot2 */
    /*public RobotConfigurationData _robot2ConfigurationData;*/
    public int _clockCycleTime;
    
    public void MasterConfigurationData(){
    }
    
}
