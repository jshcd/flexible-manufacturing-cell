/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Scada.DataBase;

/**
 *
 * @author Portatil
 */
public interface DBInterface {
    public void connect();

    public ParameterSet getParameters();

    public void setParameters(ParameterSet s);
    
    public int retrieveParameter(String parameterName);
}
