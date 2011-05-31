/* Slave 2 code */
package test.automata;

import Automaton.Slaves.Slave2;
import Auxiliar.Constants;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.Piece.Piece.PieceType;
import Element.Station.QualityControlStation;
import Element.Station.WeldingStation;
import Scada.DataBase.DBConnection;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Slave2Test extends Slave2 {

    public Slave2Test() {
        super();
    }

    public void reportToMaster(int i) {
        throw new UnsupportedOperationException();
    }

    public void orderToRobot(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}