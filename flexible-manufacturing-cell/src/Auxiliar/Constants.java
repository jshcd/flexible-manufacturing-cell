/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auxiliar;

/**
 *
 * @author Francesco
 */
public class Constants {
    
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
    
    public final static int SENSOR_GEAR_UNLOAD_ACTIVATED = 10;
    public final static int SENSOR_GEAR_UNLOAD_DISACTIVATED = 11;
    
    public final static int SENSOR_AXIS_UNLOAD_ACTIVATED = 20;
    public final static int SENSOR_AXIS_UNLOAD_DISACTIVATED = 21;
        
    public final static int SENSOR_ASSEMBLY_ACTIVATED = 30;
    public final static int SENSOR_ASSEMBLY_DISACTIVATED = 31;
    
    public final static int SENSOR_WELDING_LOAD_ACTIVATED = 40;
    public final static int SENSOR_WELDING_LOAD_DISACTIVATED = 41;
    public final static int SENSOR_WELDING_UNLOAD_ACTIVATED = 50;
    public final static int SENSOR_WELDING_UNLOAD_DISACTIVATED = 51;
    
    public final static int SENSOR_WELDING_TABLE_ACTIVATED = 60;
    public final static int SENSOR_WELDING_TABLE_DISACTIVATED = 61;
        
    public final static int SENSOR_QUALITY_ACTIVATED = 70;
    public final static int SENSOR_QUALITY_DISACTIVATED = 71;
        
    public final static int SENSOR_OK_LOAD_ACTIVATED = 80;
    public final static int SENSOR_OK_LOAD_DISACTIVATED = 81;
    public final static int SENSOR_OK_UNLOAD_ACTIVATED = 100;
    public final static int SENSOR_OK_UNLOAD_DISACTIVATED = 101;
    
    public final static int SENSOR_NOT_OK_LOAD_ACTIVATED = 90;
    public final static int SENSOR_NOT_OK_LOAD_DISACTIVATED = 91;
    public final static int SENSOR_NOT_OK_UNLOAD_ACTIVATED = 110;
    public final static int SENSOR_NOT_OK_UNLOAD_DISACTIVATED = 111;
    
    /*
     * Constants used as parameters, stored in databases
     */
    public final static String PARAM_CONVEYOR_LENGTH = "c_length";
    public final static String PARAM_CONVEYOR_SPEED = "c_speed";
    public final static String PARAM_SENSOR_INITIAL_POSITION = "s_i_pos";
    public final static String PARAM_SENSOR_FINAL_POSITION = "s_f_pos";
    public final static String PARAM_SENSOR_RANGE = "s_range";
    public final static String PARAM_PIECE_SIZE = "p_size";
    
    
}
