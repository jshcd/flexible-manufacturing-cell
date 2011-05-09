/*
 * It represents a piece
 */

package Element.Piece;


public class Piece {
	private int _id;
	private Element.Piece.Piece.PieceType _type;
	private float _position;

	public int getId() {
		throw new UnsupportedOperationException();
	}

	public void setId(int id) {
		throw new UnsupportedOperationException();
	}

	public Element.Piece.Piece.PieceType getType() {
		throw new UnsupportedOperationException();
	}

	public void setType(Element.Piece.Piece.PieceType type) {
		throw new UnsupportedOperationException();
	}

	public int getPosition() {
		throw new UnsupportedOperationException();
	}

	public void setPosition(int position) {
		throw new UnsupportedOperationException();
	}
        
	public enum PieceType {
		axis,
		gear,
		assembly,
		weldedAssembly;
	}
}