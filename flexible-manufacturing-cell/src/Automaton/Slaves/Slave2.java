/* Slave 2 code */

package Automaton.Slaves;

import Element.Conveyor.ConveyorBelt;
import Element.Station.WeldingStation;
import Auxiliar.State;
import Scada.DataBase.DBConnection;

public class Slave2 implements Slave {
	private SlaveMailBox _mailBox;
	private ConveyorBelt _assemblyBelt;
	private WeldingStation _weldingStation;
	private State _state;
	private DBConnection _dbconnection;

	public void start() {
		throw new UnsupportedOperationException();
	}

	public void stop() {
		throw new UnsupportedOperationException();
	}

	public void runCommand(int command) {
		throw new UnsupportedOperationException();
	}

	public void startAssemblyBelt() {
		throw new UnsupportedOperationException();
	}

	public void stopAssemblyBelt() {
		throw new UnsupportedOperationException();
	}

	public void unloadAssembly() {
		throw new UnsupportedOperationException();
	}

	public void weld() {
		throw new UnsupportedOperationException();
	}

	public void weldingAvailable() {
		throw new UnsupportedOperationException();
	}

	public void reportToMaster() {
		throw new UnsupportedOperationException();
	}

	public void loadParameters() {
		throw new UnsupportedOperationException();
	}
}