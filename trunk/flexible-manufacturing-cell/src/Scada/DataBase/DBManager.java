/*
 * This class is in charge of connecting to a database and retrieving and setting paremeter sets.
 * It also provides RMI access to send parameters.
 */
package Scada.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
    
   public MasterConfigurationData readParameters(){
       MasterConfigurationData md = new MasterConfigurationData();
       DBConnection db = new DBConnection();
       ResultSet rs = null;
       db.connect();
       
       /* Slave 1 Configuration */
       try{
           rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_SLAVE1_BELT1_CONFIGURATION);
           md._slave1ConfigurationData._gearBeltConfiguration.setLength(rs.getInt("length"));
           md._slave1ConfigurationData._gearBeltConfiguration.setSpeed(rs.getInt("speed"));
           md._slave1ConfigurationData._gearBeltConfiguration.setCapacity(rs.getInt("capacity"));
           rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_SLAVE1_BELT2_CONFIGURATION);
           md._slave1ConfigurationData._axisBeltConfiguration.setLength(rs.getInt("length"));
           md._slave1ConfigurationData._axisBeltConfiguration.setSpeed(rs.getInt("speed"));
           md._slave1ConfigurationData._axisBeltConfiguration.setCapacity(rs.getInt("capacity"));
           rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_SLAVE1_ACTIVATION_TIME_CONFIGURATION);
           md._slave1ConfigurationData._activationTime = rs.getInt("time");
       }catch(SQLException e){
           /* In case of exception set the values as default */           
           md._slave1ConfigurationData._gearBeltConfiguration.setLength(10);
           md._slave1ConfigurationData._gearBeltConfiguration.setSpeed(20);
           md._slave1ConfigurationData._gearBeltConfiguration.setCapacity(50);
           md._slave1ConfigurationData._axisBeltConfiguration.setLength(10);
           md._slave1ConfigurationData._axisBeltConfiguration.setSpeed(20);
           md._slave1ConfigurationData._axisBeltConfiguration.setCapacity(50);
           md._slave1ConfigurationData._activationTime = 3;
       }
       
       /* Slave 2 Configuration */
       try{
           rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_SLAVE2_BELT_CONFIGURATION);
           md._slave2ConfigurationData._weldingBeltConfiguration.setLength(rs.getInt("length"));
           md._slave2ConfigurationData._weldingBeltConfiguration.setSpeed(rs.getInt("speed"));
           rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_SLAVE2_ACTIVATION_TIME_CONFIGURATION);
           md._slave2ConfigurationData._activationTime = rs.getInt("time");
       }catch(SQLException e){
           /* In case of exception set the values as default */           
           md._slave2ConfigurationData._weldingBeltConfiguration.setLength(10);
           md._slave2ConfigurationData._weldingBeltConfiguration.setSpeed(20);
           md._slave2ConfigurationData._activationTime = 30;
       }
       
       /* Slave 3 Configuration */
       try{
           rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_SLAVE3_BELT1_CONFIGURATION);
           md._slave3ConfigurationData._qualityControlBelt.setLength(rs.getInt("length"));
           md._slave3ConfigurationData._qualityControlBelt.setSpeed(rs.getInt("speed"));
           rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_SLAVE3_BELT2_CONFIGURATION);
           md._slave3ConfigurationData._notAcceptedBelt.setLength(rs.getInt("length"));
           rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_SLAVE3_ACTIVATION_TIME_CONFIGURATION);
           md._slave3ConfigurationData._activationTime = rs.getInt("time");
       }catch(SQLException e){
           /* In case of exception set the values as default */
           md._slave3ConfigurationData._qualityControlBelt.setLength(10);
           md._slave3ConfigurationData._qualityControlBelt.setSpeed(10);
           md._slave3ConfigurationData._notAcceptedBelt.setLength(10);
           md._slave3ConfigurationData._activationTime = 5;
       }
       
       /* Robot 1 Configuration */
       try{
           rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION);
           md._robot1ConfigurationData.setPickingTime(rs.getInt("picking_time"));
           md._robot1ConfigurationData.setTransportTimeA(rs.getInt("transport_element1"));
           md._robot1ConfigurationData.setTransportTimeB(rs.getInt("transport_element2"));
       }catch(SQLException e){
           /* In case of exception set the values as default */
           md._robot1ConfigurationData.setPickingTime(5);
           md._robot1ConfigurationData.setTransportTimeA(5);
           md._robot1ConfigurationData.setTransportTimeB(5);
       }
       
       /* Robot 2 Configuration */
       try{
           rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_ROBOT2_CONFIGURATION);
           md._robot2ConfigurationData.setPickingTime(rs.getInt("picking_time"));
           md._robot2ConfigurationData.setTransportTimeA(rs.getInt("transport_element1"));
           md._robot2ConfigurationData.setTransportTimeB(rs.getInt("transport_element2"));
       }catch(SQLException e){
           /* In case of exception set the values as default */
           md._robot2ConfigurationData.setPickingTime(5);
           md._robot2ConfigurationData.setTransportTimeA(5);
           md._robot2ConfigurationData.setTransportTimeB(5);
       }
       
       /* General Configuration for Master */
       try{
           rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_GLOBAL_TIMING_CONFIGURATION);
           md._clockCycleTime = rs.getInt("value");
       }catch(SQLException e){
           /* In case of exception set the value as default */
           md._clockCycleTime = 200;
       }
       
       db.disconnect();
       
       /* Return the MasterDataConfiguration Object */
       return md;       
   }
   
   public void updateParameters(MasterConfigurationData md){
       DBConnection db = new DBConnection();
       db.connect();
       String query;
       
       /* Slave 1 Configuration */
       query = Auxiliar.Constants.DBQUERY_UPDATE_SLAVE1_BELT1_CONFIGURATION;
       query = query.replaceAll("\\[LENGTH\\]",
               String.valueOf(md._slave1ConfigurationData._gearBeltConfiguration.getLength()));
       query = query.replaceAll("\\[SPEED\\]",
               String.valueOf(md._slave1ConfigurationData._gearBeltConfiguration.getSpeed()));
       query = query.replaceAll("\\[CAPACITY\\]",
               String.valueOf(md._slave1ConfigurationData._gearBeltConfiguration.getCapacity()));
       db.executeQuery(query);
       query = Auxiliar.Constants.DBQUERY_UPDATE_SLAVE1_BELT2_CONFIGURATION;
       query = query.replaceAll("\\[LENGTH\\]",
               String.valueOf(md._slave1ConfigurationData._axisBeltConfiguration.getLength()));
       query = query.replaceAll("\\[SPEED\\]",
               String.valueOf(md._slave1ConfigurationData._axisBeltConfiguration.getSpeed()));
       query = query.replaceAll("\\[CAPACITY\\]",
               String.valueOf(md._slave1ConfigurationData._axisBeltConfiguration.getCapacity()));
       db.executeQuery(query);
       query = Auxiliar.Constants.DBQUERY_UPDATE_SLAVE1_ACTIVATION_TIME_CONFIGURATION;
       query = query.replaceAll("\\[TIME\\]", 
               String.valueOf(md._slave1ConfigurationData._activationTime));
       db.executeQuery(query);
       
       /* Slave 2 Configuration */       
       query = Auxiliar.Constants.DBQUERY_UPDATE_SLAVE2_BELT_CONFIGURATION;
       query = query.replaceAll("\\[LENGTH\\]",
               String.valueOf(md._slave2ConfigurationData._weldingBeltConfiguration.getLength()));
       query = query.replaceAll("\\[SPEED\\]",
               String.valueOf(md._slave2ConfigurationData._weldingBeltConfiguration.getSpeed()));
       db.executeQuery(query);
       query = Auxiliar.Constants.DBQUERY_UPDATE_SLAVE2_ACTIVATION_TIME_CONFIGURATION;
       query = query.replaceAll("\\[TIME\\]", 
               String.valueOf(md._slave2ConfigurationData._activationTime));
       db.executeQuery(query);
       
       /* Slave 3 Configuration */
       query = Auxiliar.Constants.DBQUERY_UPDATE_SLAVE3_BELT1_CONFIGURATION;
       query = query.replaceAll("\\[LENGTH\\]",
               String.valueOf(md._slave3ConfigurationData._qualityControlBelt.getLength()));
       query = query.replaceAll("\\[SPEED\\]",
               String.valueOf(md._slave3ConfigurationData._qualityControlBelt.getSpeed()));
       db.executeQuery(query);
       query = Auxiliar.Constants.DBQUERY_UPDATE_SLAVE3_BELT2_CONFIGURATION;
       query = query.replaceAll("\\[LENGTH\\]",
               String.valueOf(md._slave3ConfigurationData._notAcceptedBelt.getLength()));
       db.executeQuery(query);       
       query = Auxiliar.Constants.DBQUERY_UPDATE_SLAVE3_ACTIVATION_TIME_CONFIGURATION;
       query = query.replaceAll("\\[TIME\\]", 
               String.valueOf(md._slave3ConfigurationData._activationTime));
       db.executeQuery(query);
       
       /* Robot 1 Configuration */
       query = Auxiliar.Constants.DBQUERY_UPDATE_ROBOT1_CONFIGURATION;
       query = query.replaceAll("\\[PICKING_TIME\\]", 
               String.valueOf(md._robot1ConfigurationData.getPickingTime()));
       query = query.replaceAll("\\[TRANSPORT_TIMEA\\]", 
               String.valueOf(md._robot1ConfigurationData.getTransportTimeA()));
       query = query.replaceAll("\\[TRANSPORT_TIMEB\\]", 
               String.valueOf(md._robot1ConfigurationData.getTransportTimeB()));
       db.executeQuery(query);
       
       /* Robot 2 Configuration */
       query = Auxiliar.Constants.DBQUERY_UPDATE_ROBOT2_CONFIGURATION;
       query = query.replaceAll("\\[PICKING_TIME\\]", 
               String.valueOf(md._robot1ConfigurationData.getPickingTime()));
       query = query.replaceAll("\\[TRANSPORT_TIMEA\\]", 
               String.valueOf(md._robot1ConfigurationData.getTransportTimeA()));
       query = query.replaceAll("\\[TRANSPORT_TIMEB\\]", 
               String.valueOf(md._robot1ConfigurationData.getTransportTimeB()));
       db.executeQuery(query);
       
       /* General Timing Configuraiton */
       query = Auxiliar.Constants.DBQUERY_UPDATE_GLOBAL_TIMING_CONFIGURATION;
       query = query.replaceAll("\\[VALUE\\]", String.valueOf(md._clockCycleTime));
       db.executeQuery(query);
   }
}