
/*
 * This class is in charge of connecting and accepting connections from different terminals and passing
 * and receiving parameter sets.
 */

package Scada.DataBase;

public class DBConnection {
	private DBConnection _other;
	private DBManager _manager;

	public void connect() {
		throw new UnsupportedOperationException();
	}

	public void sendParameters(ParameterSet parameters) {
		throw new UnsupportedOperationException();
	}

	public ParameterSet receiveParameters() {
		throw new UnsupportedOperationException();
	}
}