
package Scada.DataBase;

/**
 * Class that defines the configuration parameters gathered from the Database
 * that configure a Conveyor Belt
 * @author Echoplex
 */
public class BeltConfigurationData {
    
    /**
     * Lenth of the belt
     */
    private int _length;
    
    /**
     * Speed of the belt
     */
    private int _speed;
    
    /**
     * Piece capacity of the belt
     */
    private int _capacity;

    /**
     * Main constructor
     */
    public  BeltConfigurationData(){ }
    
    /**
     * Constructor of the class
     * @param length Belt length
     * @param speed Belt speed
     * @param capacity Belt capacity
     */
    public  BeltConfigurationData(int length, int speed, int capacity){
        _length = length;
        _speed = speed;
        _capacity = capacity;
    }
    
    /**
     * Sets the belt length to a new value
     * @param length New value for the belt length
     */
    public void setLength(int length){
        _length = length;
    }
    
    /**
     * Sets the belt speed to a new value
     * @param speed New value for the belt speed
     */
    public void setSpeed(int speed){
        _speed = speed;
    }
    
    /**
     * Sets the belt capacity to a new value
     * @param capacity New value for the belt capacity
     */
    public void setCapacity(int capacity){
        _capacity = capacity;
    }
    
    /**
     * Returns the belt length value
     * @return Belt length value
     */
    public int getLength(){
        return _length;
    }
    
    /**
     * Returns the belt speed value
     * @return Belt speed value
     */
    public int getSpeed(){
        return _speed;
    }
    
    /**
     * Returns the belt capacity value
     * @return Belt capacity value
     */
    public int getCapacity(){
        return _capacity;
    }
}
