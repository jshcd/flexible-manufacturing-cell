/*
 * It represents a piece
 */
package Element.Piece;

public class Piece {

    private Element.Piece.Piece.PieceType _type;
    private double _position;

    public Element.Piece.Piece.PieceType getType() {
        return _type;
    }

    public void setType(Element.Piece.Piece.PieceType type) {
        _type = type;
    }

    public double getPosition() {
        return _position;
    }

    public void setPosition(double position) {
        _position = position;
    }

    public enum PieceType {
        axis,
        gear,
        assembly,
        weldedAssembly;
    }
}