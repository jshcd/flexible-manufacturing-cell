/* Slave 1 code */

package Automaton.Slaves;

import Element.Robot.Robot1;
import Auxiliar.State;
import Element.Conveyor.ConveyorBelt;
import Element.Other.Sensor;
import Scada.DataBase.DBConnection;
import Element.Station.AssemblyStation;

public class Slave1 implements Slave {
	private SlaveMailBox _mailBox;
	private ConveyorBelt _gearBelt;
	private ConveyorBelt _axisBelt;
	private Robot1 _robot;
	private State _state;
	private DBConnection _dbconnection;
	private AssemblyStation _assemblyStation;
        
        private Sensor _sensor1;
        private Sensor _sensor2;
        private Sensor _sensor3;
        
        public Slave1(){
            
            // TODO: Aqui hay que cargar los parametros primero y cambiarlos por los ceros
            _gearBelt = new ConveyorBelt(0,0,0);
            _axisBelt = new ConveyorBelt(0,0,0);
            
            _robot = new Robot1();
            
            _state = Auxiliar.State.q0;
            
            _dbconnection = new DBConnection();
            
            _assemblyStation = new AssemblyStation(0,0,0);
            
            _sensor1 = new Sensor();
            _sensor1.setSensorId(1);
            _sensor1.setAssociatedContainer(_gearBelt);
            _sensor2.setProcess(this);
            // TODO: cargar la posicion en la cinta
            _sensor1.setPositionInBelt(positionInBelt);
            
            _sensor2 = new Sensor();
            _sensor2.setSensorId(2);
            _sensor2.setAssociatedContainer(_axisBelt);
            _sensor2.setProcess(this);
            // TODO: cargar la posicion en la cinta
            _sensor2.setPositionInBelt(positionInBelt);
            
            _sensor3 = new Sensor();
            _sensor3.setSensorId(3);
            _sensor3.setAssociatedContainer(_assemblyStation);
            _sensor3.setProcess(this);
            // TODO: cargar la posicion en la cinta
            _sensor3.setPositionInBelt(positionInBelt);
            
        }

	public void start() {
		throw new UnsupportedOperationException();
	}

	public void stop() {
		throw new UnsupportedOperationException();
	}

	public void runCommand(int command) {
		throw new UnsupportedOperationException();
	}

	public void startGearBelt() {
		throw new UnsupportedOperationException();
	}

	public void stopGearBelt() {
		throw new UnsupportedOperationException();
	}

	public void startAxisBelt() {
		throw new UnsupportedOperationException();
	}

	public void stopAxisBelt() {
		throw new UnsupportedOperationException();
	}

	public void orderToRobot() {
		throw new UnsupportedOperationException();
	}

	public void loadParameters() {
		throw new UnsupportedOperationException();
	}

	public void reportToMaster() {
		throw new UnsupportedOperationException();
	}
}