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
    public final static int START_SLAVE1 = 1001;
    public final static int START_SLAVE2 = 1002;
    public final static int START_SLAVE3 = 1003;
    public final static int START_ROBOT1 = 1004;
    public final static int START_ROBOT2 = 1005;
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
    public final static int SENSOR_OK_LOAD_ACTIVATED = 91;
    public final static int SENSOR_OK_LOAD_DISACTIVATED = 90;
    public final static int SENSOR_OK_UNLOAD_ACTIVATED = 111;
    public final static int SENSOR_OK_UNLOAD_DISACTIVATED = 110;
    public final static int SENSOR_NOT_OK_LOAD_ACTIVATED = 81;
    public final static int SENSOR_NOT_OK_LOAD_DISACTIVATED = 80;
    public final static int SENSOR_NOT_OK_UNLOAD_ACTIVATED = 101;
    public final static int SENSOR_NOT_OK_UNLOAD_DISACTIVATED = 100;
    public final static int ROBOT1_SLAVE1_PICKS_AXIS = 200;
    public final static int ROBOT1_SLAVE1_PICKS_GEAR = 201;
    public final static int ROBOT1_SLAVE1_PLACES_GEAR = 202;
    public final static int ROBOT1_SLAVE1_PLACES_AXIS = 203;
    public final static int ROBOT1_SLAVE1_PICKS_ASSEMBLY = 204;
    public final static int ROBOT1_SLAVE1_PLACES_ASSEMBLY = 205;
    public final static int ROBOT1_SLAVE1_REQUEST_ASSEMBLY = 206;
    public final static int SLAVE1_ROBOT1_ASSEMBLY_COMPLETED = 207;
    public final static int ROBOT2_SLAVE1_PICKS_ASSEMBLY = 208;
    public final static int SLAVE1_ROBOT2_ASSEMBLY_PICKED = 209;
    public final static int ROBOT2_SLAVE2_REQUEST_WELDING = 301;
    public final static int SLAVE2_ROBOT2_WELDED_ASSEMBLY_COMPLETED = 300;
    public final static int ROBOT2_SLAVE2_PLACES_ASSEMBLY = 303;
    public final static int SLAVE2_ROBOT2_ASSEMBLY_PLACED = 307;
    public final static int ROBOT2_SLAVE2_PICKS_WELDED_ASSEMBLY = 305;
    public final static int SLAVE2_ROBOT2_WELDED_ASSEMBLY_PICKED = 308;
    public final static int ROBOT2_SLAVE2_PLACES_WELDED_ASSEMBLY = 306;
    public final static int SLAVE2_ROBOT2_WELDED_ASSEMBLY_PLACED = 309;
    public final static int ROBOT2_SLAVE2_REQUEST_QUALITY = 304;
    public final static int ROBOT2_SLAVE2_PICKS_CHECKED_WELDED_ASSEMBLY = 310;
    public final static int SLAVE2_ROBOT2_CHECKED_WELDED_ASSEMBLY_PICKED = 313;
    public final static int SLAVE2_ROBOT2_QUALITY_CONTROL_COMPLETED_OK = 311;
    public final static int SLAVE2_ROBOT2_QUALITY_CONTROL_COMPLETED_NOT_OK = 312;
    public final static int ROBOT2_SLAVE3_PLACES_WELDED_OK = 401;
    public final static int ROBOT2_SLAVE3_PLACES_WELDED_NOT_OK = 402;
    public final static int SLAVE3_ROBOT2_WELDED_ASSEMBLY_PLACED = 403;
    public final static int SLAVE1_ID = 1;
    public final static int SLAVE2_ID = 2;
    public final static int SLAVE3_ID = 3;
    public final static int COMMAND_SLAVE1_CONNECTED = 1;
    public final static int COMMAND_SLAVE2_CONNECTED = 2;
    public final static int COMMAND_SLAVE3_CONNECTED = 3;
    /* Constants used in DB Queries */
    public final static String DBQUERY_SELECT_SLAVE1_BELT1_CONFIGURATION = "SELECT * FROM belts "
            + "WHERE belts.id = 1";
    public final static String DBQUERY_SELECT_SLAVE1_BELT2_CONFIGURATION = "SELECT * FROM belts "
            + "WHERE belts.id = 2";
    public final static String DBQUERY_SELECT_SLAVE1_BELT3_CONFIGURATION = "SELECT * FROM belts "
            + "WHERE belts.id = 3";
    public final static String DBQUERY_SELECT_SLAVE3_BELT1_CONFIGURATION = "SELECT * FROM belts "
            + "WHERE belts.id = 4";
    public final static String DBQUERY_SELECT_SLAVE3_BELT2_CONFIGURATION = "SELECT * FROM belts "
            + "WHERE belts.id = 5";
    public final static String DBQUERY_SELECT_ASSEMBLY_STATION_TIME = "SELECT * FROM stations WHERE station_name = 'assembly'";
    public final static String DBQUERY_SELECT_WELDING_STATION_TIME = "SELECT * FROM stations WHERE station_name = 'welding'";
    public final static String DBQUERY_SELECT_QUALITY_STATION_TIME = "SELECT * FROM stations WHERE station_name = 'quality'";
    public final static String DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR1 = "SELECT * FROM robot1 WHERE robot1.id = 1";
    public final static String DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR2 = "SELECT * FROM robot1 WHERE robot1.id = 2";
    public final static String DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR3 = "SELECT * FROM robot1 WHERE robot1.id = 3";
    public final static String DBQUERY_SELECT_ROBOT2_CONFIGURATION_TR4 = "SELECT * FROM robot2 WHERE robot2.id = 1";
    public final static String DBQUERY_SELECT_ROBOT2_CONFIGURATION_TR5 = "SELECT * FROM robot2 WHERE robot2.id = 2";
    public final static String DBQUERY_SELECT_ROBOT2_CONFIGURATION_TR6 = "SELECT * FROM robot2 WHERE robot2.id = 3";
    public final static String DBQUERY_SELECT_GLOBAL_TIMING_CONFIGURATION = "SELECT "
            + "configuration.value FROM configuration WHERE configuration.parameter = 'clock_cycle'";
    public final static String DBQUERY_SELECT_SENSOR_RANGE = "SELECT "
            + "configuration.value FROM configuration WHERE configuration.parameter = 'sensor_range'";
    public final static String DBQUERY_SELECT_PIECE_SIZE = "SELECT "
            + "configuration.value FROM configuration WHERE configuration.parameter = 'piece_size'";
    public final static String DBQUERY_SELECT_SUCCESS_RATE = "SELECT "
            + "configuration.value FROM configuration WHERE configuration.parameter = 'success_rate'";
    /* Constants used in DB Updates */
    public final static String DBQUERY_UPDATE_SLAVE1_BELT1_CONFIGURATION = "UPDATE belts "
            + "SET length = [LENGTH], speed = [SPEED], capacity = [CAPACITY] WHERE id = 1";
    public final static String DBQUERY_UPDATE_SLAVE1_BELT2_CONFIGURATION = "UPDATE belts "
            + "SET length = [LENGTH], speed = [SPEED], capacity = [CAPACITY] WHERE id = 2";
    public final static String DBQUERY_UPDATE_SLAVE1_BELT3_CONFIGURATION = "UPDATE belts "
            + "SET length = [LENGTH], speed = [SPEED], capacity = [CAPACITY] WHERE id = 3";
    public final static String DBQUERY_UPDATE_SLAVE3_BELT1_CONFIGURATION = "UPDATE belts "
            + "SET length = [LENGTH], speed = [SPEED], capacity = [CAPACITY] WHERE id = 4";
    public final static String DBQUERY_UPDATE_SLAVE3_BELT2_CONFIGURATION = "UPDATE belts "
            + "SET length = [LENGTH], speed = [SPEED], capacity = [CAPACITY] WHERE id = 5";
    public final static String DBQUERY_UPDATE_ASSEMBLY_STATION_TIME = "UPDATE stations "
            + "SET stations.time = [TIME] WHERE station_name = 'assembly'";
    public final static String DBQUERY_UPDATE_WELDING_STATION_TIME = "UPDATE stations "
            + "SET stations.time = [TIME] WHERE station_name = 'welding'";
    public final static String DBQUERY_UPDATE_QUALITY_STATION_TIME = "UPDATE stations "
            + "SET stations.time = [TIME] WHERE station_name = 'quality'";
    public final static String DBQUERY_UPDATE_ROBOT1_CONFIGURATION_TR1 = "UPDATE robot1 SET "
            + "robot1.time = [TIME] WHERE robot1.id = 1";
    public final static String DBQUERY_UPDATE_ROBOT1_CONFIGURATION_TR2 = "UPDATE robot1 SET "
            + "robot1.time = [TIME] WHERE robot1.id = 2";
    public final static String DBQUERY_UPDATE_ROBOT1_CONFIGURATION_TR3 = "UPDATE robot1 SET "
            + "robot1.time = [TIME] WHERE robot1.id = 3";
    public final static String DBQUERY_UPDATE_ROBOT2_CONFIGURATION_TR4 = "UPDATE robot2 SET "
            + "robot2.time = [TIME] WHERE robot2.id = 1";
    public final static String DBQUERY_UPDATE_ROBOT2_CONFIGURATION_TR5 = "UPDATE robot2 SET "
            + "robot2.time = [TIME] WHERE robot2.id = 2";
    public final static String DBQUERY_UPDATE_ROBOT2_CONFIGURATION_TR6 = "UPDATE robot2 SET "
            + "robot2.time = [TIME] WHERE robot2.id = 3";
    public final static String DBQUERY_UPDATE_GLOBAL_TIMING_CONFIGURATION = "UPDATE "
            + "configuration SET configuration.value = [VALUE] WHERE configuration.parameter = 'clock_cycle'";
    public final static String DBQUERY_UPDATE_SENSOR_RANGE = "UPDATE "
            + "configuration SET configuration.value = [VALUE] WHERE configuration.parameter = 'sensor_range'";
    public final static String DBQUERY_UPDATE_PIECE_SIZE = "UPDATE "
            + "configuration SET configuration.value = [VALUE] WHERE configuration.parameter = 'piece_size'";
    public final static String DBQUERY_UPDATE_SUCCESS_RATE = "UPDATE "
            + "configuration SET configuration.value = [VALUE] WHERE configuration.parameter = 'success_rate'";
    /**
     * CONSTANTS used for GUI
     */
    public static final int GUI_WIDTH = 985;
    public static final int GUI_HEIGHT = 735;
    public static final int CANVAS_WIDTH = 962;
    public static final int CANVAS_HEIGHT = 519;
    public static final String TITLE = "Flexible Manufacturing Cell";
    public static final String CONTROL_PANEL_TITLE = "Control Panel";
    public static final String START_TOOL_TIP = "Start";
    public static final String STOP_TOOL_TIP = "Normal stop";
    public static final String EMERGENCY_STOP_TOOL_TIP = "Emergency stop";
    public static final String CONFIGURATION_TOOL_TIP = "Configuration parameters";
    public static final String REPORT_TOOL_TIP = "Report generation";
    /* CONSTANTS used in GUI for ELEMENT POSITIONING */
    /* SENSOR LIGHT POSITIONS */
    public static final Point GEAR_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION = new Point(267, 54);
    public static final Point AXIS_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION = new Point(267, 174);
    public static final Point WELDING_CONVEYOR_SENSOR_LIGHT_LEFT_POSITION = new Point(580, 29);
    public static final Point WELDING_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION = new Point(778, 29);
    public static final Point OK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION = new Point(410, 294);
    public static final Point OK_CONVEYOR_SENSOR_RIGHT_LIGHT_POSITION = new Point(610, 294);
    public static final Point NOTOK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION = new Point(410, 404);
    public static final Point NOTOK_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION = new Point(610, 404);
    public static final Point ASSEMBLY_STATION_SENSOR_LIGHT_POSITION = new Point(544, 192);
    public static final Point WELDING_STATION_SENSOR_LIGHT_POSITION = new Point(900, 146);
    public static final Point QUALITY_CONTROL_STATION_LIGHT_SENSOR_POSITION = new Point(821, 276);
    /* WELDING STAMPER */
    public static final Point WELDING_STAMPER_POSITION_ENABLED = new Point(391, 130);
    public static final Point WELDING_STAMPER_POSITION_DISABLED = new Point(391, 80);
    public static final Point WELDING_AXIS_POSITION = new Point(461, 186);
    public static final Point WELDING_GEAR_POSITION = new Point(439, 193);
    public static final Point WELDING_WELDED_PIECE_POSITION = new Point(444, 185);
    /* ASSEMBLY STATION */
    public static final Point ASSEMBLY_STATION_GEAR_POSITION = new Point(453, 313);
    public static final Point ASSEMBLY_STATION_AXIS_POSITION = new Point(500, 280);
    public static final Point WELDING_STATION_CENTER_POSITION = new Point(824, 183);
    public static final Point QUALITY_STATION_CENTER_POSITION = new Point(742, 314);
    /* TORCH */
    public static final Point TORCH_POSITION_ENABLED = new Point(833, 114);
    public static final Point TORCH_POSITION_DISABLED = new Point(833, 74);
    /* GLASS */
    public static final Point GLASS_POSITION_ENABLED = new Point(730, 316);
    /* STARTING POSITIONS FOR CONVEYOR BELTS */
    public static final Point GEAR_CONVEYOR_START_POSITION = new Point(26, 87);
    public static final Point AXIS_CONVEYOR_START_POSITION = new Point(13, 200);
    public static final Point WELDING_CONVEYOR_START_POSITION = new Point(522, 50);
    public static final Point OK_CONVEYOR_START_POSITION = new Point(599, 315);
    public static final Point NOTOK_CONVEYOR_START_POSITION = new Point(594, 425);
    /* SENSOR ACTIVATION POSITION IN BELTS */
    public static final Point GEAR_CONVEYOR_SENSOR_START_POSITION = new Point(46, 88);
    public static final Point GEAR_CONVEYOR_SENSOR_END_POSITION = new Point(246, 88);
    public static final Point AXIS_CONVEYOR_SENSOR_START_POSITION = new Point(31, 200);
    public static final Point AXIS_CONVEYOR_SENSOR_END_POSITION = new Point(233, 200);
    public static final Point WELDING_CONVEYOR_SENSOR_START_POSITION = new Point(546, 50);
    public static final Point WELDING_CONVEYOR_SENSOR_END_POSITION = new Point(746, 50);
    public static final Point OK_CONVEYOR_SENSOR_START_POSITION = new Point(576, 315);
    public static final Point OK_CONVEYOR_SENSOR_END_POSITION = new Point(376, 315);
    public static final Point NOTOK_CONVEYOR_SENSOR_START_POSITION = new Point(577, 425);
    public static final Point NOTOK_CONVEYOR_SENSOR_END_POSITION = new Point(376, 425);
    //conections Panel
    public static final String TITLE_CONNECTIONS = "Connections Panel";
    public static final String ASSEMBLY_AUTOMATON_LABEL = "Assembly Automaton";
    public static final String WELDING_AUTOMATON_LABEL = "Welding Automaton";
    public static final String QUALITY_AUTOMATON_LABEL = "Quality Automaton";
    public static final String MASTER_SCADA_AUTOMATON_LABEL = "Master and SCADA Automaton";
    //Log Panel
    public static final String TITLE_LOG = "Log Panel";

    /* CONNECTION ESTABLISHMENT RESPONSES */
    public static final String CONNECTION_ACCEPTED = "ConnectionAccepted";
    public static final String CONNECTION_REJECTED_DUPLICATED_ID = "ConnectionRejectedDuplicatedID";
    public static final String CONNECTION_REJECTED_UNEXPECTED_ID = "ConnectionRejectedUnexpectedID";
    public static final String DEFAULT_MASTER_IP = "localhost";
    public static final int DEFAULT_MASTER_PORT = 42680;
    public static final int UPDATE_FREQUENCY = 30;
    /**
     * String that defines the report configuration file
     */
    public static final String REPORTS_PATH = "report.properties";
}
