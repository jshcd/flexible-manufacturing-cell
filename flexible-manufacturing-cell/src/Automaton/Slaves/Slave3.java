/* Slave 3 code */

package Automaton.Slaves;

import Element.Station.QualityControlStation;
import Auxiliar.AutomatonState;
import Element.Conveyor.ConveyorBelt;
import Scada.DataBase.DBConnection;

public class Slave3 implements Slave {
	private SlaveMailBox _mailBox;
	private QualityControlStation _qualityControlStation;
	private AutomatonState _state;
	private DBConnection _dbconnection;
	private ConveyorBelt _acceptedBelt;
	private ConveyorBelt _rejectedBelt;

	public void start() {
		throw new UnsupportedOperationException();
	}

	public void stop() {
		throw new UnsupportedOperationException();
	}

	public void runCommand(int command) {
		throw new UnsupportedOperationException();
	}

	public void qualityControl() {
		throw new UnsupportedOperationException();
	}

	public void orderToRobot() {
		throw new UnsupportedOperationException();
	}

	public void reportToMaster() {
		throw new UnsupportedOperationException();
	}

	public void loadParameters() {
		throw new UnsupportedOperationException();
	}
}