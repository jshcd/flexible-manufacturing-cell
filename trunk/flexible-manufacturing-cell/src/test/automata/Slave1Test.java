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
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Slave1Test extends Slave1 {

    TestAutomata test;
    Robot1Test _robot;

    public Slave1Test() {
        super();
    }

    public void reportToMaster(int i) {
    }

    public void orderToRobot(int i) {
        test.sendToRobot1(i);
        test.sendToRobot2(i);
        test.sendToSlave1(i);
        test.sendToSlave2(i);
        test.sendToSlave3(i);
//        System.out.println("S1 " + i);
    }

    public void startRobot() {
        _robot = new Robot1Test();
        _robot.setTest(test);
        try {
            _robot.setTrasportTime1(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR1).getInt("time"));
            _robot.setTransportTime2(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR2).getInt("time"));
            _robot.setTransportTime3(_dbconnection.executeSelect(Constants.DBQUERY_SELECT_ROBOT1_CONFIGURATION_TR3).getInt("time"));
            (new Thread(_robot)).start();

        } catch (SQLException ex) {
            Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // In this loop just one gear and axis are placed
    protected void mainLoop() {

        Piece p = new Piece();
        p.setPosition(0);
        p.setType(PieceType.gear);
        _gearBelt.addPiece(p);

        Logger.getLogger(Slave1.class.getName()).log(Level.FINE, "Added gear");

        p = new Piece();
        p.setPosition(0);
        p.setType(PieceType.axis);
        _axisBelt.addPiece(p);
        Logger.getLogger(Slave1.class.getName()).log(Level.FINE, "Added axis");
    }

    public void setTest(TestAutomata test) {
        this.test = test;
    }

    public Robot1Test getRobot() {
        return _robot;
    }
    
    
}