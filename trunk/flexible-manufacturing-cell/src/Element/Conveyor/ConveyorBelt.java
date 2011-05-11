/*
 * Abstract class that defines a belt wich contains sensors. This class is extended by OneSensorBelt
 * and TwoSensorBelt depending on what type it is.
 */


package Element.Conveyor;

import Automaton.Slaves.Slave;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.PieceContainer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConveyorBelt extends Thread implements PieceContainer {

    private int _id;
    private List<Piece> _pieces;
    private int _length;
    private int _speed;
    private boolean _moving;
    private ArrayList<Sensor> _sensors;
    
    // Process for which it works
    protected Slave _process;
    
    public ConveyorBelt(int id, int speed, int length){
        _id = id;
        _pieces = Collections.synchronizedList(new ArrayList<Piece>());
        _length = length;
        _speed = speed;
        _moving = false;
        _sensors = new ArrayList();
    }
    
    @Override
    public void start(){
        
        // TODO: Check this works right
        while(_moving){
            try {
                Thread.sleep(10);
                for(Piece p:_pieces){
                    p.setPosition(p.getPosition() + _speed/100);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ConveyorBelt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println("Conveyor Belt with id " + _id + " has finished");
    }

    public void startBelt() {
        _moving = true;
        System.out.println("Conveyor Belt with id " + _id + " has started");
    }

    public void stopBelt() {
        _moving = false;
        System.out.println("Conveyor Belt with id " + _id + " has stoped");
    }

    public int getSpeed() {
        return _speed;
    }

    public void setSpeed(int speed) {
        _speed = speed;
    }

    public int getLength() {
        return _length;
    }

    public void setLength(int length) {
        _length = length;
    }

    public List<Element.Piece.Piece> getPieces() {
        return _pieces;
    }

    public void setPieces(List<Element.Piece.Piece> pieces) {
        _pieces = pieces;
    }

    public int getConveyorId() {
        return _id;
    }

    public void setConveyorId(int id) {
        _id = id;
    }

    public Slave getProcess() {
        return _process;
    }

    public void setProcess(Slave _process) {
        this._process = _process;
    }
    
    public void addSensor(Sensor s){
        _sensors.add(s);
    }
    
    public void addPiece(Piece p){
        _pieces.add(p);
    }
}