/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.automata;

/**
 *
 * @author Portatil
 */
public class TestAutomata {

    Slave1Test s1;
    Slave2Test s2;
    Slave3Test s3;
    Robot2Test r2;

    public static void main(String args[]) {
        final TestAutomata ta = new TestAutomata();

    }

    public TestAutomata() {
        s1 = new Slave1Test();
        s2 = new Slave2Test();
//        s3 = new Slave3Test();

        s1.initialize();
        s2.initialize();
//        s3.initialize();

        s1.startRobot();

        Thread t = new Thread(new Runnable() {

            public void run() {
                s1.start();
            }
        });
        t.start();

//        r2 = new Robot2Test();
//        
//        Thread y = new Thread(r2);
//        y.start();

    }
}
