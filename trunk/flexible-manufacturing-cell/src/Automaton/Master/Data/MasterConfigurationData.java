/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Automaton.Master.Data;

import Auxiliar.MailboxData;
import Scada.DataBase.Robot1ConfigurationData;
import Scada.DataBase.Slave1ConfigurationData;
import Scada.DataBase.Slave2ConfigurationData;
import Scada.DataBase.Slave3ConfigurationData;

/**
 *
 * @author Javier
 */
public class MasterConfigurationData extends MailboxData {
    private Slave1ConfigurationData _slave1ConfigurationData;
    private Robot1ConfigurationData _robot1ConfigurationData;
    private double _sensorRange;
    private double _pieceSize;
    private Slave2ConfigurationData _slave2ConfigurationData;
    private Slave3ConfigurationData _slave3ConfigurationData;

    public double getPieceSize() {
        return _pieceSize;
    }

    public Robot1ConfigurationData getRobot1ConfigurationData() {
        return _robot1ConfigurationData;
    }

    public double getSensorRange() {
        return _sensorRange;
    }

    public Slave1ConfigurationData getSlave1ConfigurationData() {
        return _slave1ConfigurationData;
    }

    public Slave2ConfigurationData getSlave2ConfigurationData() {
        return _slave2ConfigurationData;
    }

    public Slave3ConfigurationData getSlave3ConfigurationData() {
        return _slave3ConfigurationData;
    }

    public void setPieceSize(double _pieceSize) {
        this._pieceSize = _pieceSize;
    }

    public void setRobot1ConfigurationData(Robot1ConfigurationData _robot1ConfigurationData) {
        this._robot1ConfigurationData = _robot1ConfigurationData;
    }

    public void setSensorRange(double _sensorRange) {
        this._sensorRange = _sensorRange;
    }

    public void setSlave1ConfigurationData(Slave1ConfigurationData _slave1ConfigurationData) {
        this._slave1ConfigurationData = _slave1ConfigurationData;
    }

    public void setSlave2ConfigurationData(Slave2ConfigurationData _slave2ConfigurationData) {
        this._slave2ConfigurationData = _slave2ConfigurationData;
    }

    public void setSlave3ConfigurationData(Slave3ConfigurationData _slave3ConfigurationData) {
        this._slave3ConfigurationData = _slave3ConfigurationData;
    }
    
}
