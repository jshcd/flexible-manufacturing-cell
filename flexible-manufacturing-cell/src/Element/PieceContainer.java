/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Element;

import Automaton.Slaves.Slave;
import Element.Other.Sensor;
import Element.Piece.Piece;
import java.util.List;

/**
 *
 * @author Portatil
 */
public interface PieceContainer extends Runnable {

    void addPiece(Piece p);

    void addSensor(Sensor s);

    int getId();

    List<Piece> getPieces();

    Slave getProcess();

    boolean isMoving();

    void removeLastPiece();

    void run();

    void setId(int id);

    void setPieces(List<Piece> pieces);

    void setProcess(Slave _process);

    void startContainer();

    void stopContainer();
    
}
