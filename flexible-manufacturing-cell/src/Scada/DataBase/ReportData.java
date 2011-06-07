package Scada.DataBase;

/**
 *
 * @author 
 */
public class ReportData {

    public int _rightPiecesCurrentExec;
    public int _wrongPiecesCurrentExec;
    public int _rightPiecesAllExec;
    public int _wrongPiecesAllExec;
    public int _nRestarts;
    public int _nEmergencyStops;
    public int _nNormalStops;

    public boolean _firstStart;

    public ReportData() {
    }

    public ReportData(int rightPackagesCurrentExec, int wrongPackagesCurrentExec, int rightPackagesAllExec, int wrongPackagesAllExec, int nRestarts, int nEmergencyStops, int nNormalStops) {
        this._rightPiecesCurrentExec = rightPackagesCurrentExec;
        this._wrongPiecesCurrentExec = wrongPackagesCurrentExec;
        this._rightPiecesAllExec = rightPackagesAllExec;
        this._wrongPiecesAllExec = wrongPackagesAllExec;
        this._nRestarts = nRestarts;
        this._nEmergencyStops = nEmergencyStops;
        this._nNormalStops = nNormalStops;
    }



  


}
