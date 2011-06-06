/* Slave 1 code */
package test.automata;

import Automaton.Slaves.Slave1;
import Element.Robot.Robot1;
import Auxiliar.*;
import Element.Conveyor.ConveyorBelt;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.Piece.Piece.PieceType;
import Scada.DataBase.DBConnection;
import Element.Station.AssemblyStation;
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

public class NoCommSlave extends Slave1 {

    public NoCommSlave() {
        super();
    }

    public void reportToMaster(int i) {
    }

    public void sendCommand(int i) {
    }
    
    public Robot1 getRobot(){
        return _robot;
    }
}