/*
 * It represents a 
 */

package Element.Piece;

import Element.PieceContainer;

public class Piece {
	private Integer _id;
	private Element.Piece.Piece.PieceType _type;
	private Integer _position;

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

	public Integer getPosition() {
		throw new UnsupportedOperationException();
	}

	public void setPosition(Integer position) {
		throw new UnsupportedOperationException();
	}
        
	public enum PieceType {
		axis,
		gear,
		assembly,
		weldedAssembly;
	}
}