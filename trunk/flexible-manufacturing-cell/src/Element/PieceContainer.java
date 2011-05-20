/*
 * This interface represents every object that can contain Pieces in it. 
 */

package Element;

import Element.Piece.Piece;
import java.util.List;

public interface PieceContainer {

	public List<Piece> getPieces();

	public void setPieces(List<Element.Piece.Piece> pieces);
        
}