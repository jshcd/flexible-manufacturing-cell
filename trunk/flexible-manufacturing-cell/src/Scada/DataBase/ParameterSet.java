/*
 * This class represents a set of parameters
 */
package Scada.DataBase;

import java.util.ArrayList;

public class ParameterSet {

    private static ArrayList<Parameter> _parameters;

    public ArrayList<Parameter> getParameters() {
        return _parameters;
    }

    public void setParameters(ArrayList<Parameter> parameters) {
        _parameters = parameters;
    }
    
    public class Parameter {
        public String field = "";
        public int value = 0;
    }
}