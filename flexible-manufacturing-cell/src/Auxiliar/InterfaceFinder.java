package Auxiliar;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterfaceFinder {

    public static void main(String args[]) {
        try {
            Enumeration e = java.net.NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements()){
                System.out.println((NetworkInterface)e.nextElement());
            }
        } catch (SocketException ex) {
            Logger.getLogger(InterfaceFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
