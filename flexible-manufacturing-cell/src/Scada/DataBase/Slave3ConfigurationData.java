package Scada.DataBase;

import java.io.Serializable;

/**
 * Defines the configuration data for the Slave 3
 * @author Echoplex
 */
public class Slave3ConfigurationData implements Serializable {

    /**
     * Accepted Belt instance
     */
    public BeltConfigurationData _acceptedBelt;
    /**
     * Not Accepted - Rejected Belt instance
     */
    public BeltConfigurationData _notAcceptedBelt;

    /**
     * Constructor
     */
    public Slave3ConfigurationData() {
    }

    /**
     * Constructor of the class
     * @param _acceptedBelt Accepted Belt Instance
     * @param _notAcceptedBelt Rejected Belt Instance
     */
    public Slave3ConfigurationData(BeltConfigurationData _acceptedBelt, BeltConfigurationData _notAcceptedBelt) {
        this._acceptedBelt = _acceptedBelt;
        this._notAcceptedBelt = _notAcceptedBelt;
    }

    /**
     * @return The Rejected Belt Instance
     */
    public BeltConfigurationData getNotAcceptedBelt() {
        return _notAcceptedBelt;
    }

    /**
     * Sets a new instance for the rejected belt
     * @param _notAcceptedBelt New instance
     */
    public void setNotAcceptedBelt(BeltConfigurationData _notAcceptedBelt) {
        this._notAcceptedBelt = _notAcceptedBelt;
    }

    /**
     * @return The Accepted Belt insatnce
     */
    public BeltConfigurationData getAcceptedBelt() {
        return _acceptedBelt;
    }

    /**
     * Sets a new instance for the Accepted Belt
     * @param _acceptedBelt New instance
     */
    public void setAcceptedBelt(BeltConfigurationData _acceptedBelt) {
        this._acceptedBelt = _acceptedBelt;
    }
}
