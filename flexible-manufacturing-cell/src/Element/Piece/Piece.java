/*
 * It represents a piece
 */
package Element.Piece;

public class Piece {

    private int _id;
    private Element.Piece.Piece.PieceType _type;
    private float _position;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public Element.Piece.Piece.PieceType getType() {
        return _type;
    }

    public void setType(Element.Piece.Piece.PieceType type) {
        _type = type;
    }

    public float getPosition() {
        return _position;
    }

    public void setPosition(float position) {
        _position = position;
    }

    public enum PieceType {
        axis,
        gear,
        assembly,
        weldedAssembly;
    }
}