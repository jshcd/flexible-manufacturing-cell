/*
 * This class is in charge of connecting to a database and retrieving and setting paremeter sets.
 * It also provides RMI access to send parameters.
 */
package Scada.DataBase;

public class DBManager implements DBInterface {

    public void connect() {
        throw new UnsupportedOperationException();
    }

    public ParameterSet getParameters() {
        throw new UnsupportedOperationException();
    }

    public void setParameters(ParameterSet s) {
        throw new UnsupportedOperationException();
    }

    public int retrieveParameter(String parameterName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}