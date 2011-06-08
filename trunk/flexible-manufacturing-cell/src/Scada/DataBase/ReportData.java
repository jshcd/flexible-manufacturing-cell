package Scada.DataBase;

/**
 * Defines the structure of a Report
 * @author 
 */
public class ReportData {

    /**
     * Number of right pieces in current execution
     */
    public int _rightPiecesCurrentExec;
    
    /**
     * Number of wrong pieces in current execution
     */
    public int _wrongPiecesCurrentExec;
    
    /**
     * Number of total execution right pieces
     */
    public int _rightPiecesAllExec;
    
    /**
     * Number of total execution wrong pieces
     */
    public int _wrongPiecesAllExec;
    
    /**
     * Number of execution restarts
     */
    public int _nRestarts;
    
    /**
     * Number of emergency stops
     */
    public int _nEmergencyStops;
    
    /**
     * Number of normal stops
     */
    public int _nNormalStops;

    /**
     * Shows if it is the first start
     */
    public boolean _firstStart;

    /**
     * Constructor
     */
    public ReportData() {
        /* Does anything */
    }
}
