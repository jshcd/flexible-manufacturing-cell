package Element.Robot;

import Auxiliar.AutomatonState;
import Element.Piece.Piece;
import Element.Other.Sensor;
import Automaton.Slaves.Slave1;
import Automaton.Slaves.Slave2;
import Element.Conveyor.ConveyorBelt;
import Element.Station.AssemblyStation;
import Scada.DataBase.DBManager;

public class Robot1 extends Thread implements Robot {

    private RobotMailBox _mailBox;
    private AutomatonState _state;
    private Piece _loadedPiece;
    
    private int pickingTime;
    private int trasportTime1; // When the robot has moved the gear to the assembly table, the robot 1 waits until the sensor 2 is active.
    private int transportTime2; // When the robot has moved the ax to the assembly table, the robot 1 waits until the sensor 1 is active.
    
    public Robot1(){
        _state = AutomatonState.q0;
        _mailBox = new RobotMailBox(1);
    }
    
    @Override
    public void start(){
        while(true){
            switch(_state){
                // TODO: rellenar transiciones
            }
        }
    }
    
    public void runCommand(int command) {
        throw new UnsupportedOperationException();
    }
    
    public void pickAxis() {
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.axis);
    }
    
    public void pickGear() {
        _loadedPiece = new Piece();
        _loadedPiece.setType(Piece.PieceType.gear);
    }

    public void transportAssembly() {
        throw new UnsupportedOperationException();
    }

    public void transportAxisGear() {
        throw new UnsupportedOperationException();
    }

    public Piece getLoadedPiece() {
        return this._loadedPiece;
    }

    public void setLoadedPiece(Piece loadedPiece) {
        _loadedPiece = loadedPiece;
    }

    public int getPickingTime() {
        return pickingTime;
    }

    public void setPickingTime(int pickingTime) {
        this.pickingTime = pickingTime;
    }

    public int getTrasportTime1() {
        return trasportTime1;
    }

    public void setTrasportTime1(int trasportTime1) {
        this.trasportTime1 = trasportTime1;
    }

    public int getTrasportTime2() {
        return transportTime2;
    }

    public void setTrasportTime2(int trasportTime2) {
        this.transportTime2 = trasportTime2;
    }

    
    
}