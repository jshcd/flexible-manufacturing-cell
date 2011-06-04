/*
 * It represents a piece
 */
package Element.Piece;

import java.awt.Point;

public class Piece {

    private Element.Piece.Piece.PieceType _type;
    private double _position;
    private Point _pos;

    public Element.Piece.Piece.PieceType getType() {
        return _type;
    }

    public synchronized void setType(Element.Piece.Piece.PieceType type) {
        _type = type;
    }

    public double getPosition() {
        return _position;
    }

    public synchronized void setPosition(double position) {
        _position = position;
    }

    public enum PieceType {
        axis,
        gear,
        assembly,
        weldedAssembly,
        weldedAssemblyOk,
        weldedAssemblyNotOk;
    }
    
    public void setGuiPosition(Point p){
        _pos = p;
    }
    
    public Point getPos(){
        return _pos;
    }
}