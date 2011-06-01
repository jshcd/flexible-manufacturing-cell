/*
 * This class checks the quality of pieces.
 */
package Element.Station;

import Auxiliar.Constants;
import Element.Conveyor.ConveyorBelt;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QualityControlStation extends ConveyorBelt {

    private int _qualityTime;
    private int _sucessRate;

    public QualityControlStation(int id, int speed, int length) {
        super(id, speed, length);
    }

    @Override
    public synchronized void run() {
        while (true) {
            if (_moving) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(QualityControlStation.class.getName()).log(Level.SEVERE, null, ex);
                }
                checkQuality();
            }
        }
    }

    public boolean checkQuality() {
        if (_pieces.size() == 1) {

            try {
                Thread.sleep(_qualityTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(AssemblyStation.class.getName()).log(Level.SEVERE, null, ex);
            }

            int random = (int) Math.random() * 100;
            if (random > _sucessRate) {
                this._process.orderToRobot(Constants.SLAVE3_ROBOT2_QUALITY_CONTROL_COMPLETED_OK);
            } else {
                this._process.orderToRobot(Constants.SLAVE3_ROBOT2_QUALITY_CONTROL_COMPLETED_NOT_OK);
            }
            return true;
        } else {
            return false;
        }
    }

    public int getQualityTime() {
        return _qualityTime;
    }

    public void setQualityTime(int qualityTime) {
        this._qualityTime = qualityTime;
    }

    public int getSucessRate() {
        return _sucessRate;
    }

    public void setSucessRate(int sucessRate) {
        this._sucessRate = sucessRate;
    }
}