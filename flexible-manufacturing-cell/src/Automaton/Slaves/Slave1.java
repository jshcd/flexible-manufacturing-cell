/* Slave 1 code */
package Automaton.Slaves;

import Element.Robot.Robot1;
import Auxiliar.*;
import Element.Conveyor.ConveyorBelt;
import Element.Other.Sensor;
import Element.Piece.Piece;
import Element.Piece.Piece.PieceType;
import Scada.DataBase.DBConnection;
import Element.Station.AssemblyStation;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Slave1 implements Slave {

    private SlaveMailBox _mailBox;
    private ConveyorBelt _gearBelt;
    private ConveyorBelt _axisBelt;
    private Robot1 _robot;
    private DBConnection _dbconnection;
    private AssemblyStation _assemblyStation;
    private Sensor _sensor1;
    private Sensor _sensor2;
    private Sensor _sensor3;
    private boolean _finishing;

    /*
     * Arg[0] must be the IP Address of the server
     */
    public static void main(String[] args) {
        Slave1 s = new Slave1(args[0]);
    }

    public Slave1(String serverIP) {
        initialize(serverIP);
        mainLoop();
    }

    public void initialize(String serverIP) {
        _dbconnection = new DBConnection(serverIP);
        _dbconnection.connect();
        int conveyorSpeed = _dbconnection.retrieveParameter(Auxiliar.Constants.PARAM_CONVEYOR_SPEED);
        int conveyorLength = _dbconnection.retrieveParameter(Auxiliar.Constants.PARAM_CONVEYOR_LENGTH);
        int finalPositionInBelt = _dbconnection.retrieveParameter(Auxiliar.Constants.PARAM_SENSOR_FINAL_POSITION);
        int sensor_range = _dbconnection.retrieveParameter(Auxiliar.Constants.PARAM_SENSOR_RANGE);

        _gearBelt = new ConveyorBelt(1, conveyorSpeed, conveyorLength);
        _axisBelt = new ConveyorBelt(2, conveyorSpeed, conveyorLength);

        _robot = new Robot1();

        //TODO: establecer velocidad y longitud?
        _assemblyStation = new AssemblyStation(3, 0, 0);

        _sensor1 = new Sensor();
        _sensor1.setSensorId(1);
        _sensor1.setAssociatedContainer(_gearBelt);
        _sensor1.setProcess(this);
        _sensor1.setRange(sensor_range);
        _sensor1.setPositionInBelt(finalPositionInBelt);

        _sensor2 = new Sensor();
        _sensor2.setSensorId(2);
        _sensor2.setAssociatedContainer(_axisBelt);
        _sensor2.setProcess(this);
        _sensor2.setRange(sensor_range);
        _sensor2.setPositionInBelt(finalPositionInBelt);

        _sensor3 = new Sensor();
        _sensor3.setSensorId(3);
        _sensor3.setAssociatedContainer(_assemblyStation);
        _sensor3.setProcess(this);
        _sensor3.setRange(sensor_range);
        _sensor3.setPositionInBelt(0);

        // We start the belts
        _gearBelt.start();
        _gearBelt.addSensor(_sensor1);
        _axisBelt.start();
        _axisBelt.addSensor(_sensor2);
        _assemblyStation.start();
        _assemblyStation.addSensor(_sensor3);

        //We start the sensors
        _sensor1.start();
        _sensor2.start();
        _sensor3.start();

        _finishing = false;
    }

    /*
     * Starts the system
     */
    public void start() {
        _finishing = false;
        _gearBelt.startBelt();
        _axisBelt.startBelt();
        orderToRobot(Constants.START_ORDER);
        mainLoop();
    }
    /*
     * Emergency stop
     */

    public void stop() {
        _finishing = true;
        _gearBelt.stopBelt();
        _axisBelt.stopBelt();
        orderToRobot(Constants.EMERGENCY_STOP_ORDER);
    }

    public void runCommand(int command) {
        switch (command) {
            case Constants.START_ORDER:
                start();
                break;
            case Constants.EMERGENCY_STOP_ORDER:
                stop();
                break;
            case Constants.NORMAL_STOP_ORDER:
                _finishing = true;
                break;
        }
    }

    public void orderToRobot(int orderNumber) {
        throw new UnsupportedOperationException();
    }

    public void reportToMaster() {
        throw new UnsupportedOperationException();
    }

    private void mainLoop() {

        int pieceSize = _dbconnection.retrieveParameter(Constants.PARAM_PIECE_SIZE);

        // if we didn't receive the order to finish, we keep adding pieces
        while (!_finishing) {

            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Slave1.class.getName()).log(Level.SEVERE, null, ex);
            }

            boolean roomInGearBelt = true;
            boolean roomInAxisBelt = true;

            //we check if there is room in the gears belt for adding a new piece
            for (Piece p : _gearBelt.getPieces()) {
                if (p.getPosition() < pieceSize * 1.1) {
                    roomInGearBelt = false;
                    break;
                }
            }

            // if so we add the piece
            if (roomInGearBelt) {
                Piece p = new Piece();
                p.setPosition(0);
                p.setType(PieceType.gear);
                _gearBelt.addPiece(p);
            }

            //we check if there is room in the axis belt for adding a new piece
            for (Piece p : _axisBelt.getPieces()) {
                if (p.getPosition() < pieceSize * 1.1) {
                    roomInAxisBelt = false;
                    break;
                }
            }

            // if so we add the piece
            if (roomInAxisBelt) {
                Piece p = new Piece();
                p.setPosition(0);
                p.setType(PieceType.axis);
                _axisBelt.addPiece(p);
            }
        }


    }
}