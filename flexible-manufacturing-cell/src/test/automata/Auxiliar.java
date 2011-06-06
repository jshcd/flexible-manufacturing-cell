/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.automata;

import Auxiliar.Constants;
import Element.Conveyor.ConveyorBelt;
import Element.Other.Sensor;

/**
 *
 * @author Portatil
 */
public class Auxiliar {

    Slave1Test s1;
    Slave2Test s2;
    Slave3Test s3;
    Robot2Test r2;

    public static void main(String args[]) {
        final Auxiliar ta = new Auxiliar();

    }

    public Auxiliar() {

        s3 = new Slave3Test();
        s3.initialize();
        s3.start();
        s3.runCommand(Constants.ROBOT2_SLAVE3_PLACES_WELDED_OK);


    }

    public void sendToSlave1(int command) {
        s1.runCommand(command);
    }

    public void sendToSlave2(int command) {
        s2.runCommand(command);
    }

    public void sendToSlave3(int command) {
        s3.runCommand(command);
    }

    //public void sendToRobot1(int command) {
    //    s1.getRobot().runCommand(command);
    //}

    public void sendToRobot2(int command) {
        r2.runCommand(command);
    }
}
