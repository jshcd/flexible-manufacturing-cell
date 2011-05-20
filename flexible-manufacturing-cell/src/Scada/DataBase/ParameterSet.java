/*
 * This class represents a set of parameters
 */


package Scada.DataBase;

import java.util.ArrayList;

public class ParameterSet {
	private ArrayList<Parameter> _parameters;

	public ArrayList<Parameter> getParameters() {
		throw new UnsupportedOperationException();
	}

	public void setParameters(ArrayList<Parameter> parameters) {
		throw new UnsupportedOperationException();
	}
        
        public class Parameter{
            public String field = "";
            public int value = 0;
        }
}