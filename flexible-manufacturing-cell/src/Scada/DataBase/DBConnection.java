
/*
 * This class is in charge of connecting and accepting connections from different terminals 
 * (other object of this class) and passing and receiving parameter sets.
 */
package Scada.DataBase;

import Scada.DataBase.ParameterSet.Parameter;

public class DBConnection implements DBInterface{

    private DBConnection _other;
    private DBManager _manager;
    private ParameterSet _parameters;
    
    private String _serverIP;

    public DBConnection(String serverIP) {
        _serverIP = serverIP;
    }
    
    
    public void connect() {
        throw new UnsupportedOperationException();
    }

    public void setParameters(ParameterSet parameters) {
        throw new UnsupportedOperationException();
    }

    public ParameterSet getParameters() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public int retrieveParameter(String parameterName) {
        for (Parameter p : _parameters.getParameters()) {
            if (p.field.equals(parameterName)) {
                return p.value;
            }
        }
        return -1;
    }

}