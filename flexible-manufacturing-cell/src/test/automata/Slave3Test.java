/* Slave 3 code */
package test.automata;

import Automaton.Slaves.Slave3;
import Auxiliar.Constants;
import Element.Station.QualityControlStation;
import Element.Conveyor.ConveyorBelt;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.Piece.Piece.PieceType;
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

public class Slave3Test extends Slave3 {

    private DBConnection _dbconnection;
    private ConveyorBelt _acceptedBelt;
    private ConveyorBelt _rejectedBelt;
    private Sensor _sensor8;
    private Sensor _sensor9;
    private Sensor _sensor10;
    private Sensor _sensor11;

    public Slave3Test() {
        super();
    }

    public void reportToMaster(int i) {
        throw new UnsupportedOperationException();
    }

    public void orderToRobot(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}