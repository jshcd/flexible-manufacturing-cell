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