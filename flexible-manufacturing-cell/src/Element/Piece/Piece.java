
package Element.Piece;

import java.awt.Point;
import java.io.Serializable;

/**
 * Represents a piece
 * @author Echoplex
 */
public class Piece implements Serializable{

    /**
     * Enumeration for the type of piece
     */
    private Element.Piece.Piece.PieceType _type;
    
    /**
     * The position inside the different automatas
     */
    private double _position;
    
    /**
     * The GUI position
     */
    private Point _pos;

    /**
     * @return The Piece type
     */
    public Element.Piece.Piece.PieceType getType() {
        return _type;
    }

    /**
     * Sets a new type for the piece
     * @param type New type
     */
    public synchronized void setType(Element.Piece.Piece.PieceType type) {
        _type = type;
    }

    /**
     * @return Returns the coordinates where the Piece is rendered in the GUI
     */
    public double getPosition() {
        return _position;
    }

    /**
     * Sets the position of the Piece within the different automatons
     * @param position New position value
     */
    public synchronized void setPosition(double position) {
        _position = position;
    }

    /**
     * Piece type enumeration
     */
    public enum PieceType {
        axis,
        gear,
        assembly,
        weldedAssembly,
        weldedAssemblyOk,
        weldedAssemblyNotOk;
    }
    
    /**
     * Sets the GUI position to a new value
     * @param p New value of the coordinates in the GUI
     */
    public void setGuiPosition(Point p){
        _pos = p;
    }
    
    /**
     * @return The GUI position
     */
    public Point getPos(){
        return _pos;
    }
}