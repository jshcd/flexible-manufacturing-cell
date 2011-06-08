
package Element;

import Automaton.Slaves.Slave;
import Element.Other.Sensor;
import Element.Piece.Piece;
import java.util.List;

/**
 * Defines the interface of a Piece Container
 * @author Echoplex
 */
public interface PieceContainer extends Runnable {

    /**
     * Adds a piece in the container
     * @param p Piece to add
     */
    void addPiece(Piece p);

    /**
     * Adds a sensor in the container
     * @param s Sensor to add
     */
    void addSensor(Sensor s);

    /**
     * @return Container ID
     */
    int getId();

    /**
     * @return List of pieces contained in the Container
     */
    List<Piece> getPieces();

    /**
     * @return Slave that is related to this container
     */
    Slave getProcess();

    /**
     * @return TRUE if the container is activated, FALSE in other case
     */
    boolean isMoving();

    /**
     * Removes the last piece added
     */
    void removeLastPiece();
    
    /**
     * Executes the thread asociated to the container
     */
    void run();

    /**
     * Sets the container id
     * @param id New Id value
     */
    void setId(int id);

    /**
     * Sets the list of pieces contained
     * @param pieces List of pieces
     */
    void setPieces(List<Piece> pieces);

    /**
     * Sets the process associated to the container
     * @param _process New process
     */
    void setProcess(Slave _process);

    /**
     * Starts the container
     */
    void startContainer();

    /**
     * Stops the container
     */
    void stopContainer();
}
