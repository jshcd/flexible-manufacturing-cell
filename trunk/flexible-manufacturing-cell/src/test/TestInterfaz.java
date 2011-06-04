/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import Automaton.Master.Master;
import Scada.Gui.MonitorWindow;

/**
 *
 * @author test
 */
public class TestInterfaz {
    
     public static void main(String[] args) {
          Master m = new Master();
          MonitorWindow interfaz = new MonitorWindow(m);
         interfaz.run();
    }


}
