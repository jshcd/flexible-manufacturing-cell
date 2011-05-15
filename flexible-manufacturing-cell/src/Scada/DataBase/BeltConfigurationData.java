/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Scada.DataBase;

/**
 *
 * @author Ringare
 */
public class BeltConfigurationData {
    private int _length;
    private int _speed;
    private int _capacity;
    
    public void BeltConfiguration(){}
    
    public void BeltConfiguration(int length, int speed, int capacity){
        _length = length;
        _speed = speed;
        _capacity = capacity;
    }
    
    public void setLength(int length){
        _length = length;
    }
    
    public void setSpeed(int speed){
        _speed = speed;
    }
    
    public void setCapacity(int capacity){
        _capacity = capacity;
    }
    
    public int getLength(){
        return _length;
    }
    
    public int getSpeed(){
        return _speed;
    }
    
    public int getCapacity(){
        return _capacity;
    }
}
