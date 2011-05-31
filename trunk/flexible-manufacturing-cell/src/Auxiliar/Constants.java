/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auxiliar;

import java.awt.Point;

/**
 *
 * @author Francesco
 */
public class Constants {

    /*
     * Constant used for MS Access database location
     */
    public final static String DATABASE_LOCATION = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=FlexibleManufacturincCellDB.mdb";
    /*
     *  Constants used by mailboxes
     */
    public final static int START_ORDER = 0;
    public final static int EMERGENCY_STOP_ORDER = 1;
    public final static int NORMAL_STOP_ORDER = 2;
    public final static int SLAVE_ONE_STARTING = 3;
    public final static int SLAVE_ONE_STOPPING = 4;
    public final static int SLAVE_TWO_STARTING = 5;
    public final static int SLAVE_TWO_STOPPING = 6;
    public final static int SLAVE_THREE_STARTING = 7;
    public final static int SLAVE_THREE_STOPPING = 8;
    public final static int SENSOR_GEAR_UNLOAD_ACTIVATED = 11;
    public final static int SENSOR_GEAR_UNLOAD_DISACTIVATED = 10;
    public final static int SENSOR_AXIS_UNLOAD_ACTIVATED = 21;
    public final static int SENSOR_AXIS_UNLOAD_DISACTIVATED = 20;
    public final static int SENSOR_ASSEMBLY_ACTIVATED = 31;
    public final static int SENSOR_ASSEMBLY_DISACTIVATED = 30;
    public final static int SENSOR_WELDING_LOAD_ACTIVATED = 41;
    public final static int SENSOR_WELDING_LOAD_DISACTIVATED = 40;
    public final static int SENSOR_WELDING_UNLOAD_ACTIVATED = 51;
    public final static int SENSOR_WELDING_UNLOAD_DISACTIVATED = 50;
    public final static int SENSOR_WELDING_TABLE_ACTIVATED = 61;
    public final static int SENSOR_WELDING_TABLE_DISACTIVATED = 60;
    public final static int SENSOR_QUALITY_ACTIVATED = 71;
    public final static int SENSOR_QUALITY_DISACTIVATED = 70;
    public final static int SENSOR_OK_LOAD_ACTIVATED = 81;
    public final static int SENSOR_OK_LOAD_DISACTIVATED = 80;
    public final static int SENSOR_OK_UNLOAD_ACTIVATED = 101;
    public final static int SENSOR_OK_UNLOAD_DISACTIVATED = 100;
    public final static int SENSOR_NOT_OK_LOAD_ACTIVATED = 91;
    public final static int SENSOR_NOT_OK_LOAD_DISACTIVATED = 90;
    public final static int SENSOR_NOT_OK_UNLOAD_ACTIVATED = 111;
    public final static int SENSOR_NOT_OK_UNLOAD_DISACTIVATED = 110;
    public final static int SLAVE1_ROBOT1_PICKS_AXIS = 200;
    public final static int SLAVE1_ROBOT1_PICKS_GEAR = 201;
    public final static int SLAVE1_ROBOT1_PLACES_GEAR = 202;
    public final static int SLAVE1_ROBOT1_PLACES_AXIS = 203;
    public final static int SLAVE1_ROBOT1_PICKS_ASSEMBLY = 204;
    public final static int SLAVE1_ROBOT1_PLACES_ASSEMBLY = 205;
    public final static int SLAVE1_ROBOT1_REQUEST_ASSEMBLY = 206;
    public final static int SLAVE1_ASSEMBLY_COMPLETED = 207;
    public final static int SLAVE2_WELDED_ASSEMBLY_COMPLETED = 300;
    public final static int SLAVE2_ROBOT2_REQUEST_WELDING = 301;
    public final static int SLAVE1_ROBOT2_PICKS_ASSEMBLY = 302;
    public final static int SLAVE2_ROBOT2_PLACES_ASSEMBLY = 303;
    public final static int SLAVE2_ROBOT2_REQUEST_QUALITY = 304;
    public final static int SLAVE2_ROBOT2_PICKS_WELDED_ASSEMBLY = 305;
    public final static int SLAVE3_ROBOT2_PLACES_WELDED_ASSEMBLY = 306;
    public final static int SLAVE3_ROBOT2_PICKS_CHECKED_WELDED_ASSEMBLY = 307;
    public final static int SLAVE3_ROBOT2_PLACES_WELDED_OK = 308;
    public final static int SLAVE3_ROBOT2_PLACES_WELDED_NOT_OK = 309;
    public final static int SLAVE3_QUALITY_CONTROL_COMPLETED_OK = 310;
    public final static int SLAVE3_QUALITY_CONTROL_COMPLETED_NOT_OK = 311;
    /* 
     * Constants used in DB Queries 
     */
    public final static String DBQUERY_SELECT_SLAVE1_BELT1_CONFIGURATION = "SELECT * FROM belts "
            + "WHERE belts.id = 1";
    public final static String DBQUERY_SELECT_SLAVE1_BELT2_CONFIGURATION = "SELECT * FROM belts "
            + "WHERE belts.id = 2";
    public final static String DBQUERY_SELECT_SLAVE1_ACTIVATION_TIME_CONFIGURATION = "SELECT * "
            + "FROM activation_times WHERE activation_times.time_name='t1'";
    public final static String DBQUERY_SELECT_SLAVE2_BELT_CONFIGURATION = "SELECT * FROM belts "
            + "WHERE belts.id = 3";
    public final static String DBQUERY_SELECT_SLAVE2_ACTIVATION_TIME_CONFIGURATION = "SELECT * "
            + "FROM activation_times WHERE activation_times.time_name='t2'";
    public final static String DBQUERY_SELECT_SLAVE3_BELT1_CONFIGURATION = "SELECT * FROM belts "
            + "WHERE belts.id = 4";
    public final static String DBQUERY_SELECT_SLAVE3_BELT2_CONFIGURATION = "SELECT * FROM belts "
            + "WHERE belts.id = 5";
    public final static String DBQUERY_SELECT_SLAVE3_ACTIVATION_TIME_CONFIGURATION = "SELECT * "
            + "FROM activation_times WHERE activation_times.time_name='t3'";
    public final static String DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR1 = "SELECT * FROM robot1 WHERE robot1.id = 1";
    public final static String DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR2 = "SELECT * FROM robot1 WHERE robot1.id = 2";
    public final static String DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR3 = "SELECT * FROM robot1 WHERE robot1.id = 3";
    
    public final static String DBQUERY_SELECT_ASSEMBLY_STATION_TIME = "SELECT * FROM stations WHERE station_name = 'assembly'";
    public final static String DBQUERY_SELECT_WELDING_STATION_TIME = "SELECT * FROM stations WHERE station_name = 'welding'";
    public final static String DBQUERY_SELECT_QUALITY_STATION_TIME = "SELECT * FROM stations WHERE station_name = 'quality'";
    
    public final static String DBQUERY_SELECT_ROBOT2_CONFIGURATION = "SELECT * FROM robots WHERE "
            + "robots.id = 2";
    public final static String DBQUERY_SELECT_GLOBAL_TIMING_CONFIGURATION = "SELECT "
            + "configuration.value FROM configuration WHERE configuration.parameter = 'clock_cycle'";
    public final static String DBQUERY_SELECT_SENSOR_RANGE = "SELECT "
            + "configuration.value FROM configuration WHERE configuration.parameter = 'sensor_range'";
    public final static String DBQUERY_SELECT_PIECE_SIZE = "SELECT "
            + "configuration.value FROM configuration WHERE configuration.parameter = 'piece_size'";
    /* Constants used in DB Updates */
    public final static String DBQUERY_UPDATE_SLAVE1_BELT1_CONFIGURATION = "UPDATE belts "
            + "SET length = '[LENGTH]', speed = '[SPEED]', capacity = '[CAPACITY]' WHERE id = 1";
    public final static String DBQUERY_UPDATE_SLAVE1_BELT2_CONFIGURATION = "UPDATE belts "
            + "SET length = '[LENGTH]', speed = '[SPEED]', capacity = '[CAPACITY]' WHERE id = 2";
    public final static String DBQUERY_UPDATE_SLAVE1_ACTIVATION_TIME_CONFIGURATION = "UPDATE "
            + "activation_times SET time = '[TIME]' WHERE time_name='t1'";
    public final static String DBQUERY_UPDATE_SLAVE2_BELT_CONFIGURATION = "UPDATE belts "
            + "SET length = '[LENGTH]', speed = '[SPEED]' WHERE id = 3";
    public final static String DBQUERY_UPDATE_SLAVE2_ACTIVATION_TIME_CONFIGURATION = "UPDATE "
            + "activation_times SET time = '[TIME]' WHERE time_name='t2'";
    public final static String DBQUERY_UPDATE_SLAVE3_BELT1_CONFIGURATION = "UPDATE belts "
            + "SET length = '[LENGTH]', speed = '[SPEED]' WHERE id = 4";
    public final static String DBQUERY_UPDATE_SLAVE3_BELT2_CONFIGURATION = "UPDATE belts "
            + "SET length = '[LENGTH]' WHERE id = 5";
    public final static String DBQUERY_UPDATE_SLAVE3_ACTIVATION_TIME_CONFIGURATION = "UPDATE "
            + "activation_times SET time = '[TIME]' WHERE time_name='t3'";
    public final static String DBQUERY_UPDATE_ROBOT1_CONFIGURATION_PICKANDPLACEGEAR =
            "UPDATE robot1 SET time = '[TIME]' WHERE id=1";
    public final static String DBQUERY_UPDATE_ROBOT1_CONFIGURATION_PICKANDPLACEAXIS =
            "UPDATE robot1 SET time = '[TIME]' WHERE id=2";
    public final static String DBQUERY_UPDATE_ROBOT1_CONFIGURATION_PICKANDPLACEASSEMBLY =
            "UPDATE robot1 SET time = '[TIME]' WHERE id=3";
    public final static String DBQUERY_UPDATE_ROBOT1_CONFIGURATION_ASSEMBLYTIME =
            "UPDATE robot1 SET time = '[TIME]' WHERE id=4";
    public final static String DBQUERY_UPDATE_ROBOT1_CONFIGURATION_WELDINGTIME =
            "UPDATE robot1 SET time = '[TIME]' WHERE id=5";
    public final static String DBQUERY_UPDATE_ROBOT1_CONFIGURATION_QUALITYCONTROLTIME =
            "UPDATE robot1 SET time = '[TIME]' WHERE id=6";
//    public final static String DBQUERY_UPDATE_ROBOT2_CONFIGURATION = "UPDATE robots SET "
//            + "picking_time = '[PICKING_TIME]', transport_element1 = '[TRANSPORT_TIMEA]', "
//            + "transport_element2 = '[TRANSPORT_TIMEB]' WHERE id = '2'";
    public final static String DBQUERY_UPDATE_GLOBAL_TIMING_CONFIGURATION = "UPDATE configuration "
            + "SET value = '[VALUE]' WHERE parameter = 'clock_cycle'";
    /**
     * constants used for GUI
     */
    public static final int CANVAS_WIDTH = 960;
    public static final int CANVAS_HEIGHT = 494;
    public static final Point QUALITY_POSITION = new Point(505, 160);  //probando
    public static final String TITLE = "Flexible Manufacturing Cell";
    public static final String CONTROL_PANEL_TITLE = "Control Panel";
    public static final String START_TOOL_TIP = "Start";
    public static final String STOP_TOOL_TIP = "Normal stop";
    public static final String EMERGENCY_STOP_TOOL_TIP = "Emergency stop";
    public static final String CONFIGURATION_TOOL_TIP = "Parameters configuration";
    public static final String REPORT_TOOL_TIP = "Report generation";

    /* CONNECTION ESTABLISHMENT RESPONSES */
    public static final String CONNECTION_ACCEPTED = "ConnectionAccepted";
    public static final String CONNECTION_REJECTED_DUPLICATED_ID = "ConnectionRejectedDuplicatedID";
    public static final String CONNECTION_REJECTED_UNEXPECTED_ID = "ConnectionRejectedUnexpectedID";
    public static final String DEFAULT_MASTER_IP = "localhost";
    public static final int DEFAULT_MASTER_PORT = 42680;
    public static final int UPDATE_FREQUENCY = 30;
}
