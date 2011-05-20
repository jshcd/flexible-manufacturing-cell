/* Slave 1 code */

package Automaton.Slaves;

import Element.Conveyor.OneSensorBelt;
import Element.Robot.Robot1;
import Auxiliar.State;
import Scada.DataBase.DBConnection;
import Element.Station.AssemblyStation;

public class Slave1 implements Slave {
	private SlaveMailBox _mailBox;
	private OneSensorBelt _gearBelt;
	private OneSensorBelt _axisBelt;
	private Robot1 _robot;
	private State _state;
	private DBConnection _dbconnection;
	private AssemblyStation _assemblyStation;

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