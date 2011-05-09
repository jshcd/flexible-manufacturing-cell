package Automaton.Master;

import Scada.DataBase.DBManager;
import Scada.Gui.Canvas;
import Element.Robot.Robot2;

public class Master {

    private MasterMailBox _mailBox;
    private DBManager _dbmanager;
    private Canvas _canvas;
    private Robot2 _robot2;

    public static void main(String[] args) {
        Master m = new Master();
    }

    public Master() {
        _mailBox = new MasterMailBox();
        _dbmanager = new DBManager();
        _canvas = new Canvas();
        _robot2 = new Robot2();
    }

    public void initialize() {
        throw new UnsupportedOperationException();
    }

    public void startSystem() {
        throw new UnsupportedOperationException();
    }
    
    public void stopSystem() {
        throw new UnsupportedOperationException();
    }

    public void emergencyStop() {
        throw new UnsupportedOperationException();
    }

    public void failureStop() {
        throw new UnsupportedOperationException();
    }

    public void runCommand(int command) {
        throw new UnsupportedOperationException();
    }

    public void loadParameters() {
        throw new UnsupportedOperationException();
    }

    public Canvas getCanvas() {
        return _canvas;
    }

    public void setCanvas(Canvas _canvas) {
        this._canvas = _canvas;
    }

    public DBManager getDbmanager() {
        return _dbmanager;
    }

    public void setDbmanager(DBManager _dbmanager) {
        this._dbmanager = _dbmanager;
    }

    public MasterMailBox getMailBox() {
        return _mailBox;
    }

    public void setMailBox(MasterMailBox _mailBox) {
        this._mailBox = _mailBox;
    }

    public Robot2 getRobot2() {
        return _robot2;
    }

    public void setRobot2(Robot2 _robot2) {
        this._robot2 = _robot2;
    }
}