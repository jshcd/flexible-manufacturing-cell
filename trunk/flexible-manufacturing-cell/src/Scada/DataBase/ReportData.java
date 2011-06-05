/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Scada.DataBase;

/**
 *
 * @author 
 */
public class ReportData {

    protected int rightPiecesCurrentExec;
    protected int wrongPiecesCurrentExec;
    protected int rightPiecesAllExec;
    protected int wrongPiecesAllExec;
    protected int nRestarts;
    protected int nEmergencyStops;
    protected int nNormalStops;

    protected boolean firstStart;

    public ReportData() {
    }

    public ReportData(int rightPackagesCurrentExec, int wrongPackagesCurrentExec, int rightPackagesAllExec, int wrongPackagesAllExec, int nRestarts, int nEmergencyStops, int nNormalStops) {
        this.rightPiecesCurrentExec = rightPackagesCurrentExec;
        this.wrongPiecesCurrentExec = wrongPackagesCurrentExec;
        this.rightPiecesAllExec = rightPackagesAllExec;
        this.wrongPiecesAllExec = wrongPackagesAllExec;
        this.nRestarts = nRestarts;
        this.nEmergencyStops = nEmergencyStops;
        this.nNormalStops = nNormalStops;
    }



    public void setnEmergencyStops(int nEmergencyStops) {
        this.nEmergencyStops = nEmergencyStops;
    }

    public void setnNormalStops(int nNormalStops) {
        this.nNormalStops = nNormalStops;
    }

    public void setnRestarts(int nRestarts) {
        this.nRestarts = nRestarts;
    }

    public void setRightPiecesAllExec(int rightPackagesAllExec) {
        this.rightPiecesAllExec = rightPackagesAllExec;
    }

    public void setRightPiecesCurrentExec(int rightPackagesCurrentExec) {
        this.rightPiecesCurrentExec = rightPackagesCurrentExec;
    }

    public void setWrongPiecesAllExec(int wrongPackagesAllExec) {
        this.wrongPiecesAllExec = wrongPackagesAllExec;
    }

    public void setWrongPiecesCurrentExec(int wrongPackagesCurrentExec) {
        this.wrongPiecesCurrentExec = wrongPackagesCurrentExec;
    }

    public void setFirstStart(boolean firstStart) {
        this.firstStart = firstStart;
    }

    public boolean isFirstStart() {
        return firstStart;
    }
    

    public int getnEmergencyStops() {
        return nEmergencyStops;
    }

    public int getnNormalStops() {
        return nNormalStops;
    }

    public int getnRestarts() {
        return nRestarts;
    }

    public int getRightPackagesAllExec() {
        return rightPiecesAllExec;
    }

    public int getRightPackagesCurrentExec() {
        return rightPiecesCurrentExec;
    }

    public int getWrongPackagesAllExec() {
        return wrongPiecesAllExec;
    }

    public int getWrongPackagesCurrentExec() {
        return wrongPiecesCurrentExec;
    }


}
