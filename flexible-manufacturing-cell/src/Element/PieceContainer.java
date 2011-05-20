/*
 * This interface represents every object that can contain Pieces in it. 
 */

package Element;

import Element.Piece.Piece;
import java.util.ArrayList;

public interface PieceContainer {

	public ArrayList<Piece> getCapacity();

	public void setCapacity(ArrayList<Element.Piece.Piece> capacity);

	public void setEventListeners();
}