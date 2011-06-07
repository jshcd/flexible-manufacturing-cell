package Scada.DataBase;

/* Imports */
import Auxiliar.Constants;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Defines the different functionalities that the system will have to gather 
 * and save the configuration parameters to the database
 * @author Echoplex
 */
public class DBManager {

    /**
     * Reads the configuration parameters from the database and 
     * stores them in a new instance of MasterConfigurationData
     * @return MasterConfigurationData instance with the gathered data
     */
    public MasterConfigurationData readParameters() {
        MasterConfigurationData md = new MasterConfigurationData();

        int clockTime = 0;
        int sensorRange = 0;
        int pieceSize = 0;
        Slave1ConfigurationData s1 = null;
        Slave2ConfigurationData s2 = null;
        Slave3ConfigurationData s3 = null;
        Robot1ConfigurationData r1 = null;
        Robot2ConfigurationData r2 = null;

        DBConnection db = new DBConnection();
        ResultSet rs = null;
        db.connect();

        /* Slave 1 Configuration */
        try {
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_SLAVE1_BELT1_CONFIGURATION);
            BeltConfigurationData gearBelt = new BeltConfigurationData(rs.getInt("length"), rs.getInt("speed"), rs.getInt("capacity"));
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_SLAVE1_BELT2_CONFIGURATION);
            BeltConfigurationData axisBelt = new BeltConfigurationData(rs.getInt("length"), rs.getInt("speed"), rs.getInt("capacity"));
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_SLAVE1_BELT3_CONFIGURATION);
            BeltConfigurationData weldingBelt = new BeltConfigurationData(rs.getInt("length"), rs.getInt("speed"), rs.getInt("capacity"));
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_ASSEMBLY_STATION_TIME);
            int assemblyActivationTime = rs.getInt("time");
            s1 = new Slave1ConfigurationData(gearBelt, axisBelt, weldingBelt, assemblyActivationTime);
        } catch (SQLException e) {
            /* In case of exception set the values as default */
            s1 = new Slave1ConfigurationData(new BeltConfigurationData(10, 20, 50),
                    new BeltConfigurationData(10, 20, 50),
                    new BeltConfigurationData(10, 20, 50),
                    300);
        }

        /* Slave 2 Configuration */
        try {
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_WELDING_STATION_TIME);
            int weldingActivationTime = rs.getInt("time");
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_QUALITY_STATION_TIME);
            int qualityStationActivationTime = rs.getInt("time");
            s2 = new Slave2ConfigurationData(weldingActivationTime, qualityStationActivationTime);
        } catch (SQLException e) {
            /* In case of exception set the values as default */
            s2 = new Slave2ConfigurationData(3000, 500);
        }

        /* Slave 3 Configuration */
        try {
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_SLAVE3_BELT1_CONFIGURATION);
            BeltConfigurationData okBelt = new BeltConfigurationData(rs.getInt("length"), rs.getInt("speed"), rs.getInt("capacity"));
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_SLAVE3_BELT2_CONFIGURATION);
            BeltConfigurationData notOkBelt = new BeltConfigurationData(rs.getInt("length"), rs.getInt("speed"), rs.getInt("capacity"));
            s3 = new Slave3ConfigurationData(okBelt, notOkBelt);
        } catch (SQLException e) {
            /* In case of exception set the values as default */
            s3 = new Slave3ConfigurationData(new BeltConfigurationData(10, 10, -1), new BeltConfigurationData(10, -1, -1));
        }

        /* Robot 1 Configuration */
        try {
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR1);
            int tr1 = rs.getInt("time");
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR2);
            int tr2 = rs.getInt("time");
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR3);
            int tr3 = rs.getInt("time");
            r1 = new Robot1ConfigurationData(tr1, tr2, tr3);
        } catch (SQLException e) {
            /* In case of exception set the values as default */
            r1 = new Robot1ConfigurationData(300, 500, 500);
        }

        /* Robot 2 Configuration */
        try {
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_ROBOT2_CONFIGURATION_TR4);
            int tr4 = rs.getInt("time");
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_ROBOT2_CONFIGURATION_TR5);
            int tr5 = rs.getInt("time");
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_ROBOT2_CONFIGURATION_TR6);
            int tr6 = rs.getInt("time");
            r2 = new Robot2ConfigurationData(tr4, tr5, tr6);
        } catch (SQLException e) {
            /* In case of exception set the values as default */
            r2 = new Robot2ConfigurationData(200, 200, 300);
        }

        /* General Configuration for Master */
        try {
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_GLOBAL_TIMING_CONFIGURATION);
            clockTime = rs.getInt("value");
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_SENSOR_RANGE);
            sensorRange = rs.getInt("value");
            rs = db.executeSelect(Auxiliar.Constants.DBQUERY_SELECT_PIECE_SIZE);
            pieceSize = rs.getInt("value");

        } catch (SQLException e) {
            /* In case of exception set the value as default */
            /* md._clockCycleTime = 200;*/
            clockTime = 200;

        }

        md.setClockCycleTime(clockTime);
        md.setSensorRange(sensorRange);
        md.setPieceSize(pieceSize);
        md.setSlave1ConfigurationData(s1);
        md.setSlave2ConfigurationData(s2);
        md.setSlave3ConfigurationData(s3);
        md.setRobot1ConfigurationData(r1);
        md.setRobot2ConfigurationData(r2);

        db.disconnect();

        /* Return the MasterDataConfiguration Object */
        return md;
    }

    /**
     * Updates the configuration parameters stored in the system and probably updated 
     * to the Database.
     * @param md MasterConfigurationData instance containing the system current parameters
     */
    public void updateParameters(MasterConfigurationData md) {
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
        query = Auxiliar.Constants.DBQUERY_UPDATE_SLAVE1_BELT3_CONFIGURATION;
        query = query.replaceAll("\\[LENGTH\\]",
                String.valueOf(md._slave1ConfigurationData._weldingBeltConfiguration.getLength()));
        query = query.replaceAll("\\[SPEED\\]",
                String.valueOf(md._slave1ConfigurationData._weldingBeltConfiguration.getSpeed()));
        query = query.replaceAll("\\[CAPACITY\\]",
                /* Uncomment if included in GUI 
                 *  String.valueOf(md._slave1ConfigurationData._weldingBeltConfiguration.getCapacity()));
                 */
                "capacity"); // Comment if included in GUI

        db.executeQuery(query);
        query = Auxiliar.Constants.DBQUERY_UPDATE_SLAVE3_BELT1_CONFIGURATION;
        query = query.replaceAll("\\[LENGTH\\]",
                String.valueOf(md._slave3ConfigurationData._acceptedBelt.getLength()));
        query = query.replaceAll("\\[SPEED\\]",
                String.valueOf(md._slave3ConfigurationData._acceptedBelt.getSpeed()));
        query = query.replaceAll("\\[CAPACITY\\]",
                /* Uncomment if included in GUI 
                 *   String.valueOf(md._slave3ConfigurationData._acceptedBelt.getCapacity()));
                 */
                "capacity"); // Comment if included in GUI
        db.executeQuery(query);
        query = Auxiliar.Constants.DBQUERY_UPDATE_SLAVE3_BELT2_CONFIGURATION;
        query = query.replaceAll("\\[LENGTH\\]",
                String.valueOf(md._slave3ConfigurationData._notAcceptedBelt.getLength()));
        query = query.replaceAll("\\[SPEED\\]",
                /* Uncomment if included in GUI 
                 *  String.valueOf(md._slave3ConfigurationData._notAcceptedBelt.getSpeed()));
                 */
                "speed"); // Comment if included in GUI
        query = query.replaceAll("\\[CAPACITY\\]",
                /* Uncomment if included in GUI 
                 *  String.valueOf(md._slave3ConfigurationData._notAcceptedBelt.getCapacity()));
                 */
                "capacity"); // Comment if included in GUI
        db.executeQuery(query);

        query = Auxiliar.Constants.DBQUERY_UPDATE_ASSEMBLY_STATION_TIME;
        query = query.replaceAll("\\[TIME\\]", String.valueOf(md._slave1ConfigurationData._assemblyActivationTime));
        db.executeQuery(query);
        query = Auxiliar.Constants.DBQUERY_UPDATE_WELDING_STATION_TIME;
        query = query.replaceAll("\\[TIME\\]", String.valueOf(md._slave2ConfigurationData._weldingActivationTime));
        db.executeQuery(query);
        query = Auxiliar.Constants.DBQUERY_UPDATE_QUALITY_STATION_TIME;
        query = query.replaceAll("\\[TIME\\]", String.valueOf(md._slave2ConfigurationData._qualityControlActivationTime));
        db.executeQuery(query);

        query = Auxiliar.Constants.DBQUERY_UPDATE_ROBOT1_CONFIGURATION_TR1;
        query = query.replaceAll("\\[TIME\\]", String.valueOf(md._robot1ConfigurationData.getPickAndPlaceGearTime()));
        db.executeQuery(query);
        query = Auxiliar.Constants.DBQUERY_UPDATE_ROBOT1_CONFIGURATION_TR2;
        query = query.replaceAll("\\[TIME\\]", String.valueOf(md._robot1ConfigurationData.getPickAndPlaceAxisTime()));
        db.executeQuery(query);
        query = Auxiliar.Constants.DBQUERY_UPDATE_ROBOT1_CONFIGURATION_TR3;
        query = query.replaceAll("\\[TIME\\]", String.valueOf(md._robot1ConfigurationData.getPickAndPlaceAssemblyTime()));
        db.executeQuery(query);
        query = Auxiliar.Constants.DBQUERY_UPDATE_ROBOT2_CONFIGURATION_TR4;
        query = query.replaceAll("\\[TIME\\]", String.valueOf(md._robot2ConfigurationData.getPickAndTransportAssemblyTime()));
        db.executeQuery(query);
        query = Auxiliar.Constants.DBQUERY_UPDATE_ROBOT2_CONFIGURATION_TR5;
        query = query.replaceAll("\\[TIME\\]", String.valueOf(md._robot2ConfigurationData.getPickAndTransportWeldedAssemblyTime()));
        db.executeQuery(query);
        query = Auxiliar.Constants.DBQUERY_UPDATE_ROBOT2_CONFIGURATION_TR6;
        query = query.replaceAll("\\[TIME\\]", String.valueOf(md._robot2ConfigurationData.getPickAndTransportCheckedAssemblyTime()));
        db.executeQuery(query);

        query = Auxiliar.Constants.DBQUERY_UPDATE_GLOBAL_TIMING_CONFIGURATION;
        query = query.replaceAll("\\[VALUE\\]", String.valueOf(md._clockCycleTime));
        db.executeQuery(query);
        /* 
         * Uncomment this if these parameters are included in the GUI
         * 
        query = Auxiliar.Constants.DBQUERY_UPDATE_SENSOR_RANGE;
        query = query.replaceAll("\\[VALUE\\]", String.valueOf(md._sensorRange));
        db.executeQuery(query);
        query = Auxiliar.Constants.DBQUERY_UPDATE_PIECE_SIZE;
        query = query.replaceAll("\\[VALUE\\]", String.valueOf(md._pieceSize));
        db.executeQuery(query);
         */
    }

    /**
     * Reads all the report data stored in the system database.
     * @return Returns all the report data stored in the system database.
     */
    public ReportData readReportData() {
        ReportData data = new ReportData();
        
            try {
                // Reads the property file
                Properties properties = new Properties();
                properties.load(new FileInputStream(Constants.REPORTS_PATH));

                data._rightPiecesCurrentExec = Integer.parseInt(properties.get(
                        "rightPiecesCurrentExec").toString());
                data._wrongPiecesCurrentExec = Integer.parseInt(properties.get(
                        "wrongPiecesCurrentExec").toString());
                data._rightPiecesAllExec = Integer.parseInt(properties.get(
                        "rightPiecesAllExec").toString());
                data._wrongPiecesAllExec = Integer.parseInt(properties.get(
                        "wrongPiecesAllExec").toString());

                data._nRestarts = Integer.parseInt(properties.get("nRestarts").toString());
                data._nEmergencyStops = Integer.parseInt(properties.get(
                        "nEmergencyStops").toString());
                data._nNormalStops = Integer.parseInt(properties.get("nNormalStops").toString());
            } catch (Exception e) {
                // If the property file does not exist, default values are loaded
                data._wrongPiecesCurrentExec = 0;
                data._wrongPiecesAllExec = 0;
                data._rightPiecesAllExec = 0;
                data._rightPiecesCurrentExec = 0;
                data._nRestarts = 0;
                data._nEmergencyStops = 0;
                data._nNormalStops = 0;
                data._firstStart = true;
            }
       

        return data;
    }

    /**
     * Writes report data in the system database.
     *
     * @param data
     *            The report data to write.
     */
    public void writeReportData(ReportData data) {
        try {
            Properties properties = new Properties();

            properties.put("rightPiecesCurrentExec", String.valueOf(data._rightPiecesCurrentExec));
            properties.put("wrongPiecesCurrentExec", String.valueOf(data._wrongPiecesCurrentExec));
            properties.put("rightPiecesAllExec", String.valueOf(data._rightPiecesAllExec));
            properties.put("wrongPiecesAllExec", String.valueOf(data._wrongPiecesAllExec));

            properties.put("nNormalStops", String.valueOf(data._nNormalStops));
            properties.put("nEmergencyStops", String.valueOf(data._nEmergencyStops));
            properties.put("nRestarts", String.valueOf(data._nRestarts));

            properties.store(new FileOutputStream(Constants.REPORTS_PATH),
                    "Flexible-manufacturing-cell Auto-Generated");
        } catch (Exception e) {
        }
    }
}
